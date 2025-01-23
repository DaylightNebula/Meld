package io.github.daylightnebula.meld.ksp

import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toClassName
import io.github.daylightnebula.meld.ksp.data.BuildJavaPackets
import io.github.daylightnebula.meld.ksp.data.RegisterCodec
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.KType

class MeldProcessor(
    val codeGenerator: CodeGenerator,
    val logger: KSPLogger
): SymbolProcessor {

    val json = Json {
        ignoreUnknownKeys = true
    }

    // todo download protocol.json

    // todo implement codecs for all native types
    // todo remove old AbstractReader and ByteWriter implementations

    // todo add types and params to packets

    data class CodecEntry(val type: ClassName, val codec: ClassName)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        // register native types
        val nativeCodecs = resolver.getSymbolsWithAnnotation(RegisterCodec::class.qualifiedName!!).map {
            // get annotation
            val clazz = it as KSClassDeclaration
            val annotation = clazz.annotations
                .filter { it.shortName == resolver.getKSNameFromString("RegisterCodec") }
                .first()
            val arguments = annotation.arguments.associate { (it.name?.asString() ?: "") to it }
            val target = arguments["target"]!!.value as String
            val type = (arguments["type"]!!.value as KSType).toClassName()
            val codec = it.toClassName()

            target to CodecEntry(type, codec)
        }.toMap()

        // build packet classes
        resolver.getSymbolsWithAnnotation(BuildJavaPackets::class.qualifiedName!!)
            .forEach { if (it is KSClassDeclaration) buildPacketsClasses(it, nativeCodecs) }

        return emptyList<KSAnnotated>()
    }

    private fun buildPacketsClasses(file: KSClassDeclaration, codecs: Map<String, CodecEntry>) {
        // get types
        val protocol: ProtocolFile = json.decodeFromString(downloadProtocol())
        val types = genPacketContainer(protocol.handshaking, codecs, "Handshake") +
            genPacketContainer(protocol.status, codecs, "Status") +
            genPacketContainer(protocol.configuration, codecs, "Config") +
            genPacketContainer(protocol.login, codecs, "Login") +
            genPacketContainer(protocol.play, codecs, "Play")

        // build final output
        val collection = FileSpec.builder(file.packageName.asString(), "JavaData")
            .addTypes(types)
            .build()

        // save final output
        val numDrops = file.packageName.asString().count { it == '.' } + 2 // +2 to deal with types
        var outFile = File(file.containingFile!!.filePath)
        (0 until numDrops).forEach { outFile = outFile.parentFile }
        if (!outFile.exists()) {
            outFile.mkdirs()
        }
        collection.writeTo(outFile)
    }

    private fun genPacketContainer(
        container: SCPacketContainer,
        codecs: Map<String, CodecEntry>,
        name: String
    ): List<TypeSpec> =
        genPacketTypes(container.toServer, codecs, "Server${name}", name) +
        genPacketTypes(container.toClient, codecs, "Client${name}", name)

    private fun genPacketTypes(
        types: PacketTypes,
        codecs: Map<String, CodecEntry>,
        name: String,
        state: String
    ): List<TypeSpec> = types.types.mapNotNull { (key, value) ->
        // get class name
        val className =
            if (key.startsWith("packet_")) "${name}${snakeToCamelCase(key.substring(7 until key.length))}"
            else return@mapNotNull null
        val myClass = ClassName("io.github.daylightnebula.meld.server", "Java$className")

        // load named types
//        val params = mutableListOf<PropertySpec>()
        val namedTypes = (value as? ProtocolType.Container)?.contained ?: emptyList()
        val nameTypeClasses = namedTypes.mapNotNull { type ->
            (type.name ?: return@mapNotNull null) to (when(type.type) {
                is ProtocolType.Simple -> codecs[type.type.text] ?: throw java.lang.IllegalStateException("No codec for ${type.type.text}")
                else -> TODO("ProtocolType CodecEntry finder not implemented for ${type.type}")
            })
        }.toMap()
        val params = nameTypeClasses.mapNotNull { (name, entry) ->
            PropertySpec
                .builder(name, entry.type)
                .initializer(name)
                .build()
        }

        // add ID
        val init = (
            (types.types["packet"]!! as ProtocolType.Container)
                .contained.first { it.name == "name" }
                .type as ProtocolType.Mapping
            ).mappings
            .firstNotNullOf { if (it.value == key.substring(7 until key.length)) it.key else null }
        val idProp = PropertySpec.builder("ID", Int::class)
            .initializer(init)
            .addModifiers(KModifier.OVERRIDE)
            .build()

        // add state
        val stateProp = PropertySpec.builder("STATE", ClassName("io.github.daylightnebula.meld.server.networking.java", "JavaConnectionState"))
            .initializer(when (state) {
                "Play" -> "JavaConnectionState.IN_GAME"
                "Config" -> "JavaConnectionState.CONFIG"
                "Login" -> "JavaConnectionState.LOGIN"
                "Status" -> "JavaConnectionState.STATUS"
                "Handshake" -> "JavaConnectionState.HANDSHAKE"
                else -> throw IllegalStateException()
            })
            .addModifiers(KModifier.OVERRIDE)
            .build()

        // add constructor
        val construct = FunSpec.constructorBuilder()
            .addParameters(params.map {
                ParameterSpec.builder(it.name, it.type).build()
            })
            .build()

        // add create function
        val decodeReturnPre = nameTypeClasses.map { (name, entry) ->
            "\t$name = ${entry.codec.simpleName}.decode(reader)"
        }.joinToString(",\n")
        val decodeReturn = if (nameTypeClasses.isNotEmpty()) "\n$decodeReturnPre\n" else ""
        val decodeFun = FunSpec.builder("decode")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("reader", ClassName("io.github.daylightnebula.meld.ksp.data", "IReader"))
            .addCode("return ${myClass.simpleName}($decodeReturn)")
            .returns(myClass)
            .build()

        // add encode function
        val encodeReturnPre = nameTypeClasses.map { (name, entry) ->
            entry.codec.simpleName + ".encode($name)"
        }.joinToString(" +\n ")
        val encodeReturn = if (nameTypeClasses.isNotEmpty()) encodeReturnPre else "byteArrayOf()"
        val encodeFun = FunSpec.builder("encode")
            .addModifiers(KModifier.OVERRIDE)
            .addCode("return $encodeReturn")
            .returns(ByteArray::class)
            .build()

        // create companion
        val companion = TypeSpec.companionObjectBuilder()
            .addSuperinterface(
                ClassName("io.github.daylightnebula.meld.server.networking.java.JavaPacket", "Creator")
                    .parameterizedBy(myClass)
            )
            .addProperty(idProp)
            .addProperty(stateProp)
            .addFunction(decodeFun)
            .build()

        // open up file and add new types
        TypeSpec.classBuilder("Java$className")
            .addSuperinterface(ClassName("io.github.daylightnebula.meld.server.networking.java", "JavaPacket"))
            .addType(companion)
            .addProperty(idProp)
            .addProperty(stateProp)
            .addProperties(params)
            .primaryConstructor(construct)
            .addFunction(encodeFun)
            .build()
    }

    fun snakeToCamelCase(input: String) = input
        .split("_")
        .joinToString("") { it.capitalize() }

    fun downloadProtocol() = protocolJson

    class Provider: SymbolProcessorProvider {
        override fun create(environment: SymbolProcessorEnvironment) =
            MeldProcessor(environment.codeGenerator, environment.logger)
    }
}
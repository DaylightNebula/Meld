package io.github.daylightnebula.meld.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toClassName
import io.github.daylightnebula.meld.ksp.data.BuildJavaPackets
import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.ksp.data.RegisterCodec
import io.github.daylightnebula.meld.ksp.prismarine.PacketTypes
import io.github.daylightnebula.meld.ksp.prismarine.PrismarineType
import io.github.daylightnebula.meld.ksp.prismarine.ProtocolFile
import io.github.daylightnebula.meld.ksp.prismarine.SCPacketContainer
import io.ktor.client.HttpClient
import io.ktor.client.request.prepareGet
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.uuid.ExperimentalUuidApi

object MeldProcessor: SymbolProcessor {
    const val TARGET_VERSION = "1.21.4"

    lateinit var codeGenerator: CodeGenerator
    lateinit var logger: KSPLogger
    val codecs = mutableMapOf<String, CodecEntry>()

    val client = HttpClient {}
    val json = Json {
        ignoreUnknownKeys = true
    }

    // todo download protocol.json

    data class CodecEntry(val type: TypeName, val codec: ClassName, val manuallyCreated: Boolean)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        // register native types
        codecs.putAll(resolver.getSymbolsWithAnnotation(RegisterCodec::class.qualifiedName!!).map {
            // get annotation
            val clazz = it as KSClassDeclaration
            val annotation = clazz.annotations
                .filter { it.shortName == resolver.getKSNameFromString("RegisterCodec") }
                .first()
            val arguments = annotation.arguments.associate { (it.name?.asString() ?: "") to it }
            val target = arguments["target"]!!.value as String
            val type = (arguments["type"]!!.value as KSType).toClassName()
            val codec = it.toClassName()

            target to CodecEntry(type, codec, true)
        }.toMap().toMutableMap())

        // build packet classes
        resolver.getSymbolsWithAnnotation(BuildJavaPackets::class.qualifiedName!!)
            .forEach { if (it is KSClassDeclaration) buildPacketsClasses(it) }

        return emptyList<KSAnnotated>()
    }

    private fun buildPacketsClasses(file: KSClassDeclaration) {
        // download from https://github.com/PrismarineJS/minecraft-data/tree/master/data/pc
        val response = runBlocking {
            client.prepareGet { url("https://raw.githubusercontent.com/PrismarineJS/minecraft-data/refs/heads/master/data/pc/$TARGET_VERSION/protocol.json") }
                .execute()
                .bodyAsText()
        }

        // get types
        val protocol: ProtocolFile = json.decodeFromString(response)
        val types =
            genTypeContainers(protocol.types.types) +
            genPacketContainer(protocol.handshaking, "Handshake") +
            genPacketContainer(protocol.status, "Status") +
            genPacketContainer(protocol.configuration, "Config") +
            genPacketContainer(protocol.login, "Login") +
            genPacketContainer(protocol.play, "Play")

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

    private fun genTypeContainers(types: Map<String, PrismarineType>): List<TypeSpec> = types.mapNotNull { (key, type) ->
        if (codecs.contains(key)) return@mapNotNull null

        return@mapNotNull when(type) {
            is PrismarineType.Container -> {
                // add id prop
                val idProp = PropertySpec.builder("ID", Int::class)
                    .initializer("0x00")
                    .addModifiers(KModifier.OVERRIDE)
                    .build()

                // add state
                val stateProp = PropertySpec.builder("STATE", ClassName("io.github.daylightnebula.meld.server.networking.java", "JavaConnectionState"))
                    .initializer("JavaConnectionState.HANDSHAKE")
                    .addModifiers(KModifier.OVERRIDE)
                    .build()

                // generate container
                val preName = snakeToCamelCase(key)
                val className = if (preName.startsWith("packet", ignoreCase = true)) preName else "Type$preName"
                val myClass = ClassName("io.github.daylightnebula.meld.server", className)
                codecs[key] = CodecEntry(myClass, myClass, false)
                genContainer(
                    value = type,
                    myClass = myClass,
                    idProp = idProp,
                    stateProp = stateProp
                )
            }

            is PrismarineType.Mapping -> {
                if (type.type != "varint") TODO("Support mappings other than varint")

                // create enum class
                val className = ClassName("io.github.daylightnebula.meld.server", snakeToCamelCase(key))
                val enum = TypeSpec.enumBuilder(className)
                type.mappings.forEach { (_, value) ->
                    enum.addEnumConstant(snakeToCamelCase(value))
                }

                // add codec
                val companion = TypeSpec.companionObjectBuilder()
                    .addSuperinterface(ClassName("io.github.daylightnebula.meld.ksp.data", "Codec").parameterizedBy(className))
                    .addFunction(
                        FunSpec.builder("decode")
                            .addModifiers(KModifier.OVERRIDE)
                            .addParameter(ParameterSpec.builder("reader", IReader::class).build())
                            .addCode("return entries[VarIntCodec.decode(reader)]")
                            .returns(className)
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("encode")
                            .addModifiers(KModifier.OVERRIDE)
                            .addParameter(ParameterSpec.builder("data", className).build())
                            .addCode("return VarIntCodec.encode(`data`.ordinal)")
                            .returns(ByteArray::class)
                            .build()
                    )
                    .build()
                enum.addType(companion)

                // save codec and return
                codecs[key] = CodecEntry(className, className, true)
                listOf(enum.build())
            }

            is PrismarineType.Array -> {
                val types = mutableListOf<TypeSpec>()
                val typeName = snakeToCamelCase(key)
                val codecName = ClassName("io.github.daylightnebula.meld.server", "${typeName}Codec")

                val typeBuilder = TypeSpec.objectBuilder(codecName)

                // add id prop
                val idProp = PropertySpec.builder("ID", Int::class)
                    .initializer("0x00")
                    .addModifiers(KModifier.OVERRIDE)
                    .build()

                // add state
                val stateProp = PropertySpec.builder("STATE", ClassName("io.github.daylightnebula.meld.server.networking.java", "JavaConnectionState"))
                    .initializer("JavaConnectionState.HANDSHAKE")
                    .addModifiers(KModifier.OVERRIDE)
                    .build()

                // get child types
                val typeCollection = TypeCollection.ListTypeCollection(types)
                val child = getKtType(typeCollection, type.type, idProp, stateProp, codecName, "type_$typeName")
                    ?: throw java.lang.IllegalStateException("getKtType did not return a property for type array generator $key!")
                val arrayType = ClassName("kotlin.collections", "List").parameterizedBy(child.type)
                typeBuilder.addSuperinterface(
                    ClassName("io.github.daylightnebula.meld.ksp.data", "Codec")
                        .parameterizedBy(arrayType)
                )

                // add functions
                typeBuilder.addFunction(
                    FunSpec.builder("decode")
                        .addModifiers(KModifier.OVERRIDE)
                        .addParameter(ParameterSpec.builder("reader", IReader::class).build())
                        .addCode("return (0 until VarIntCodec.decode(reader)).map { ${child.type}.decode(reader) }")
                        .returns(arrayType)
                        .build()
                )
                typeBuilder.addFunction(
                    FunSpec.builder("encode")
                        .addModifiers(KModifier.OVERRIDE)
                        .addParameter(ParameterSpec.builder("data", arrayType).build())
                        .addCode("return VarIntCodec.encode(`data`.size) + `data`.map { `data` -> `data`.encode() }.fold(byteArrayOf()) { acc, bytes -> acc + bytes }")
                        .returns(ByteArray::class)
                        .build()
                )

                // save and return
                types.add(typeBuilder.build())
                codecs[key] = CodecEntry(arrayType, codecName, true)
                types
            }

            else -> null
        }
    }.flatten()

    private fun genPacketContainer(
        container: SCPacketContainer,
        name: String
    ): List<TypeSpec> = (
            genPacketTypes(container.toServer, "Server${name}", name) +
            genPacketTypes(container.toClient, "Client${name}", name)
        ).flatten()

    private fun getPrimaryCodec(
        inType: PrismarineType,
    ): CodecEntry? = when(inType) {
        is PrismarineType.Simple -> codecs[inType.text] //?: throw java.lang.IllegalStateException("Could not find simple codec for ${inType.text}")
        is PrismarineType.Array -> getPrimaryCodec(inType.type)
        is PrismarineType.Option -> getPrimaryCodec(inType.type)

//        is ProtocolType.Mapping -> TODO("Primary Codec Mapping")
//        is ProtocolType.BitFlags -> TODO("Primary Codec Bit Flags")
//        is ProtocolType.BitFields -> TODO("Primary Codec Bit Fields")
//        is ProtocolType.CompareTo -> TODO("Primary Codec Compare To")
//        is ProtocolType.Complex -> TODO("Primary Codec Complex")
//        is ProtocolType.TopBitSetTerminatedArray -> TODO("Primary Codec Top Bit Set Terminated Array")
        else -> null //throw IllegalStateException("Could not find primary codec for protocol type $inType")
    }

    private fun getKtType(
        typeBuilder: TypeCollection,
        inType: PrismarineType,
        idProp: PropertySpec,
        stateProp: PropertySpec,
        packetClass: ClassName,
        name: String
    ): PropertySpec? = when(inType) {
        is PrismarineType.Simple -> getPrimaryCodec(inType)
            .let { codec -> PropertySpec.builder(name, codec?.type ?: return null).initializer(name).build() }

        is PrismarineType.Array -> {
            val internalType = getKtType(
                typeBuilder = typeBuilder,
                inType = inType.type,
                idProp = idProp,
                stateProp = stateProp,
                packetClass = packetClass,
                name = name
            )?.type ?: return null

            PropertySpec.builder(
                name = name,
                type = ClassName("kotlin.collections", "List").parameterizedBy(internalType)
            ).initializer(name).build()
        }

        is PrismarineType.Buffer -> PropertySpec.builder(name, ByteArray::class)
            .initializer(name)
            .build()

        is PrismarineType.RegistryEntryHolderSet -> PropertySpec.builder(
            name = name,
            type = ClassName("io.github.daylightnebula.meld.server", "IDSet")
        ).initializer(name).build()

        is PrismarineType.Container -> {
            val containerClass = ClassName(
                when(typeBuilder) {
                    is TypeCollection.ListTypeCollection -> "io.github.daylightnebula.meld.server"
                    else -> ""
                }, snakeToCamelCase(name))

            // add container type
            typeBuilder.addAll(genContainer(
                value = inType,
                myClass = containerClass,
                idProp = idProp,
                stateProp = stateProp
            ))

            // add property
            PropertySpec.builder(name, containerClass)
                .initializer(name)
                .build()
        }

        is PrismarineType.Option -> getKtType(
            typeBuilder = typeBuilder,
            inType = inType.type,
            idProp = idProp,
            stateProp = stateProp,
            packetClass = packetClass,
            name = name
        )?.let { child ->
            PropertySpec.builder(name, child.type.copy(nullable = true))
                .initializer(name)
                .build()
        }

//        is ProtocolType.Mapping -> TODO("KT Type Mapping")
//        is ProtocolType.BitFlags -> TODO("KT Type Bit Flags")
//        is ProtocolType.BitFields -> TODO("KT Type Bit Fields")
//        is ProtocolType.CompareTo -> TODO("KT Type Compare To")
//        is ProtocolType.Complex -> TODO("KT Type Complex")
//        is ProtocolType.TopBitSetTerminatedArray -> TODO("KT Type Top Bit Set Terminated Array")
        else -> null //throw IllegalStateException("Could not find kt type for protocol type $inType")
    }

    fun encodeString(
        type: PrismarineType,
        name: String
    ): String? = when(type) {
        is PrismarineType.Simple -> {
            val codec = getPrimaryCodec(type) ?: return null
            if (codec.manuallyCreated) "${codec.codec.simpleName}.encode($name)"
            else "$name.encode()"
        }

        is PrismarineType.Array -> {
            val child = encodeString(type.type, name) ?: return null
            "VarIntCodec.encode($name.size) + $name.map { $name -> $child }.fold(byteArrayOf()) { acc, bytes -> acc + bytes }"
        }

        is PrismarineType.Option -> {
            val child = encodeString(type.type, name) ?: return null
            "if ($name != null) { byteArrayOf(0x01) + ($child) } else { byteArrayOf(0x00) }"
        }

        is PrismarineType.Container -> "$name.encode()"
        is PrismarineType.Buffer -> name
        is PrismarineType.RegistryEntryHolderSet -> "$name.encode()"

        //        is ProtocolType.Mapping -> TODO("KT Type Mapping")
        //        is ProtocolType.BitFlags -> TODO("KT Type Bit Flags")
        //        is ProtocolType.BitFields -> TODO("KT Type Bit Fields")
        //        is ProtocolType.CompareTo -> TODO("KT Type Compare To")
        //        is ProtocolType.Complex -> TODO("KT Type Complex")
        //        is ProtocolType.TopBitSetTerminatedArray -> TODO("KT Type Top Bit Set Terminated Array")
        else -> null
    }

    fun decodeString(
        type: PrismarineType,
        name: String,
    ): String? = when(type) {
        is PrismarineType.Simple -> {
            val codec = getPrimaryCodec(type)?.codec ?: return null
            "${codec.simpleName}.decode(reader)"
        }

        is PrismarineType.Array ->
            "(0 until VarIntCodec.decode(reader)).map { ${decodeString(type.type, name)} }"

        is PrismarineType.Buffer ->
            "reader.readMany(VarIntCodec.decode(reader))"

        is PrismarineType.Container -> "${snakeToCamelCase(name)}.decode(reader)"

        is PrismarineType.Option -> {
            val child = decodeString(type.type, name)
            "if (reader.read() > 0) $child else null"
        }

        is PrismarineType.RegistryEntryHolderSet -> "IDSet.decode(reader)"

        //        is ProtocolType.Mapping -> TODO("KT Type Mapping")
        //        is ProtocolType.BitFlags -> TODO("KT Type Bit Flags")
        //        is ProtocolType.BitFields -> TODO("KT Type Bit Fields")
        //        is ProtocolType.CompareTo -> TODO("KT Type Compare To")
        //        is ProtocolType.Complex -> TODO("KT Type Complex")
        //        is ProtocolType.TopBitSetTerminatedArray -> TODO("KT Type Top Bit Set Terminated Array")
        else -> null
    }

    private fun genContainer(
        value: PrismarineType.Container,
        myClass: ClassName,
        idProp: PropertySpec,
        stateProp: PropertySpec
    ): List<TypeSpec> {
        val types = mutableListOf<TypeSpec>()

        // create builder
        val builder = TypeSpec.classBuilder(myClass.simpleName)
            .addSuperinterface(ClassName("io.github.daylightnebula.meld.server.networking.java", "JavaPacket"))
            .addProperty(idProp)
            .addProperty(stateProp)
            .addAnnotation(
                AnnotationSpec.builder(ClassName("kotlin", "OptIn"))
                    .addMember("%T::class", ExperimentalUuidApi::class)
                    .build()
            )

        // load named types
        val namedTypes = value.contained
        val params = namedTypes.mapNotNull { type ->
            lowerCamelCase(type.name ?: "error") to (
                getKtType(
                    typeBuilder = TypeCollection.InternalTypeCollection(builder),
                    inType = type.type,
                    idProp = idProp,
                    stateProp = stateProp,
                    packetClass = myClass,
                    name = lowerCamelCase(type.name ?: "error")
                ) ?: return@mapNotNull null
            ) }.toMap()
        builder.addProperties(params.values)

        // add constructor
        val construct = FunSpec.constructorBuilder()
            .addParameters(params.values.map { prop ->
                ParameterSpec.builder(prop.name, prop.type).build()
            })
            .build()
        builder.primaryConstructor(construct)

        // add create function
        val decodeReturnPre = namedTypes.mapNotNull { pair ->
            val pairName = lowerCamelCase(pair.name ?: return@mapNotNull null)
            decodeString(
                type = pair.type,
                name = pairName
            )?.let { "\t${pairName} = $it" }
        }.joinToString(",\n")
        val decodeReturn = if (decodeReturnPre.length > 2) "\n$decodeReturnPre\n" else ""
        val decodeFun = FunSpec.builder("decode")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("reader", ClassName("io.github.daylightnebula.meld.ksp.data", "IReader"))
            .addCode("return ${myClass.simpleName}($decodeReturn)")
            .returns(myClass)
            .build()

        // add encode function
        val encodeReturnPre = namedTypes.mapNotNull { pair ->
            encodeString(
                type = pair.type,
                name = lowerCamelCase((pair.name ?: return@mapNotNull null).toString())
            )
        }.joinToString(" +\n ")
        val encodeReturn = if (encodeReturnPre.length > 2) encodeReturnPre else "byteArrayOf()"
        val encodeFun = FunSpec.builder("encode")
            .addModifiers(KModifier.OVERRIDE)
            .addCode("return $encodeReturn")
            .returns(ByteArray::class)
            .build()
        builder.addFunction(encodeFun)

        // create companion
        builder.addType(
            TypeSpec.companionObjectBuilder()
                .addSuperinterface(
                    ClassName("io.github.daylightnebula.meld.server.networking.java.JavaPacket", "Creator")
                        .parameterizedBy(myClass)
                )
                .addProperty(idProp)
                .addProperty(stateProp)
                .addFunction(decodeFun)
                .build()
        )

        // save type
        types.add(builder.build())
        return types
    }

    private fun genPacketTypes(
        types: PacketTypes,
        name: String,
        state: String
    ) = types.types.types.mapNotNull { (key, value) ->
        val container = value as? PrismarineType.Container ?: return@mapNotNull null

        // generate class name descriptor or skip
        val className =
            if (key.startsWith("packet_")) "${name}${snakeToCamelCase(key.substring(7 until key.length))}"
            else return@mapNotNull null
        val myClass = ClassName("io.github.daylightnebula.meld.server", "Java$className")

        // add ID
        val init = (
            (types.types.types["packet"]!! as PrismarineType.Container)
                .contained.first { it.name == "name" }
                .type as PrismarineType.Mapping
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

        // create container
        genContainer(container, myClass, idProp, stateProp)
    }

    @Suppress("unused")
    class Provider: SymbolProcessorProvider {
        override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
            codeGenerator = environment.codeGenerator
            logger = environment.logger
            return MeldProcessor
        }
    }
}
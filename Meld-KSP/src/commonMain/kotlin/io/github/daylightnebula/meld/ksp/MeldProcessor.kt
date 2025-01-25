package io.github.daylightnebula.meld.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toClassName
import com.sun.tools.javac.tree.TreeInfo.types
import io.github.daylightnebula.meld.ksp.data.BuildJavaPackets
import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.ksp.data.RegisterCodec
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.uuid.ExperimentalUuidApi

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
    // todo replace vec2f and vec3f with Float2 and Float3

    data class CodecEntry(val type: TypeName, val codec: ClassName, val manuallyCreated: Boolean)

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

            target to CodecEntry(type, codec, true)
        }.toMap().toMutableMap()

        // build packet classes
        resolver.getSymbolsWithAnnotation(BuildJavaPackets::class.qualifiedName!!)
            .forEach { if (it is KSClassDeclaration) buildPacketsClasses(it, nativeCodecs) }

        return emptyList<KSAnnotated>()
    }

    private fun buildPacketsClasses(file: KSClassDeclaration, codecs: MutableMap<String, CodecEntry>) {
        // get types
        val protocol: ProtocolFile = json.decodeFromString(downloadProtocol())
        val types =
            genTypeContainers(protocol.types, codecs) +
            genPacketContainer(protocol.handshaking, codecs, "Handshake") +
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

    private fun genTypeContainers(
        types: Map<String, ProtocolType>,
        codecs: MutableMap<String, CodecEntry>
    ): List<TypeSpec> = types.mapNotNull { (key, type) ->
        return@mapNotNull when(type) {
            is ProtocolType.Container -> {
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
                    codecs = codecs,
                    value = type,
                    myClass = myClass,
                    idProp = idProp,
                    stateProp = stateProp
                )
            }

            is ProtocolType.Mapping -> {
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

            is ProtocolType.Array -> {
                val types = mutableListOf<TypeSpec>()
                val typeName = snakeToCamelCase(key)
                val codecName = ClassName("io.github.daylightnebula.meld.server", "${typeName}Codec")

                val typeBuilder = TypeSpec.objectBuilder(codecName)
//                    .addSuperinterface(ClassName("io.github.daylightnebula.meld.ksp.data", "Codec").parameterizedBy(className))

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
                val child = getKtType(codecs, typeCollection, type.type, idProp, stateProp, codecName, "type_" + typeName)
                    ?: throw java.lang.IllegalStateException("getKtType did not return a property for type array generator $key!")
                val arrayType = ClassName("kotlin", "Array").parameterizedBy(child.type)
                typeBuilder.addSuperinterface(
                    ClassName("io.github.daylightnebula.meld.ksp.data", "Codec")
                        .parameterizedBy(arrayType)
                )

                // add functions
                typeBuilder.addFunction(
                    FunSpec.builder("decode")
                        .addModifiers(KModifier.OVERRIDE)
                        .addParameter(ParameterSpec.builder("reader", IReader::class).build())
                        .addCode("return (0 until VarIntCodec.decode(reader)).map { ${child.type}.decode(reader) }.toTypedArray()")
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
        codecs: Map<String, CodecEntry>,
        name: String
    ): List<TypeSpec> = (
            genPacketTypes(container.toServer, codecs, "Server${name}", name) +
            genPacketTypes(container.toClient, codecs, "Client${name}", name)
        ).flatten()

    private fun getPrimaryCodec(
        codecs: Map<String, CodecEntry>,
        inType: ProtocolType,
    ): CodecEntry? = when(inType) {
        is ProtocolType.Simple -> codecs[inType.text] //?: throw java.lang.IllegalStateException("Could not find simple codec for ${inType.text}")
        is ProtocolType.Array -> getPrimaryCodec(codecs, inType.type)
        is ProtocolType.Option -> getPrimaryCodec(codecs, inType.type)

//        is ProtocolType.Mapping -> TODO("Primary Codec Mapping")
//        is ProtocolType.BitFlags -> TODO("Primary Codec Bit Flags")
//        is ProtocolType.BitFields -> TODO("Primary Codec Bit Fields")
//        is ProtocolType.CompareTo -> TODO("Primary Codec Compare To")
//        is ProtocolType.Complex -> TODO("Primary Codec Complex")
//        is ProtocolType.TopBitSetTerminatedArray -> TODO("Primary Codec Top Bit Set Terminated Array")
        else -> null //throw IllegalStateException("Could not find primary codec for protocol type $inType")
    }

    private fun getKtType(
        codecs: Map<String, CodecEntry>,
        typeBuilder: TypeCollection,
        inType: ProtocolType,
        idProp: PropertySpec,
        stateProp: PropertySpec,
        packetClass: ClassName,
        name: String
    ): PropertySpec? = when(inType) {
        is ProtocolType.Simple -> getPrimaryCodec(codecs, inType)
            .let { codec -> PropertySpec.builder(name, codec?.type ?: return null).initializer(name).build() }

        is ProtocolType.Array -> {
            val internalType = getKtType(
                codecs = codecs,
                typeBuilder = typeBuilder,
                inType = inType.type,
                idProp = idProp,
                stateProp = stateProp,
                packetClass = packetClass,
                name = name
            )?.type ?: return null

            PropertySpec.builder(
                name = name,
                type = ClassName("kotlin", "Array").parameterizedBy(internalType)
            ).initializer(name).build()
        }

        is ProtocolType.Buffer -> PropertySpec.builder(name, ByteArray::class)
            .initializer(name)
            .build()

        is ProtocolType.RegistryEntryHolderSet -> PropertySpec.builder(
            name = name,
            type = ClassName("io.github.daylightnebula.meld.server", "IDSet")
        ).initializer(name).build()

        is ProtocolType.Container -> {
            val containerClass = ClassName(
                when(typeBuilder) {
                    is TypeCollection.ListTypeCollection -> "io.github.daylightnebula.meld.server"
                    else -> ""
                }, snakeToCamelCase(name))

            // add container type
            typeBuilder.addAll(genContainer(
                codecs = codecs,
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

        is ProtocolType.Option -> getKtType(
            codecs = codecs,
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
        codecs: Map<String, CodecEntry>,
        type: ProtocolType,
        name: String
    ): String? = when(type) {
        is ProtocolType.Simple -> {
            val codec = getPrimaryCodec(codecs, type) ?: return null
            if (codec.manuallyCreated) "${codec.codec.simpleName}.encode($name)"
            else "$name.encode()"
        }

        is ProtocolType.Array -> {
            val child = encodeString(codecs, type.type, name) ?: return null
            "$name.map { $name -> $child }.fold(byteArrayOf()) { acc, bytes -> acc + bytes }"
        }

        is ProtocolType.Option -> {
            val child = encodeString(codecs, type.type, name) ?: return null
            "if ($name != null) { byteArrayOf(0x01) + ($child) } else { byteArrayOf(0x00) }"
        }

        is ProtocolType.Container -> "$name.encode()"
        is ProtocolType.Buffer -> name
        is ProtocolType.RegistryEntryHolderSet -> "$name.encode()"

        //        is ProtocolType.Mapping -> TODO("KT Type Mapping")
        //        is ProtocolType.BitFlags -> TODO("KT Type Bit Flags")
        //        is ProtocolType.BitFields -> TODO("KT Type Bit Fields")
        //        is ProtocolType.CompareTo -> TODO("KT Type Compare To")
        //        is ProtocolType.Complex -> TODO("KT Type Complex")
        //        is ProtocolType.TopBitSetTerminatedArray -> TODO("KT Type Top Bit Set Terminated Array")
        else -> null
    }

    fun decodeString(
        codecs: Map<String, CodecEntry>,
        type: ProtocolType,
        name: String,
    ): String? = when(type) {
        is ProtocolType.Simple -> {
            val codec = getPrimaryCodec(codecs, type)?.codec ?: return null
            "${codec.simpleName}.decode(reader)"
        }

        is ProtocolType.Array ->
            "(0 until VarIntCodec.decode(reader)).map { ${decodeString(codecs, type.type, name)} }.toTypedArray()"

        is ProtocolType.Buffer ->
            "reader.readMany(VarIntCodec.decode(reader))"

        is ProtocolType.Container -> "${snakeToCamelCase(name)}.decode(reader)"

        is ProtocolType.Option -> {
            val child = decodeString(codecs, type.type, name)
            "if (reader.read() > 0) $child else null"
        }

        is ProtocolType.RegistryEntryHolderSet -> "IDSet.decode(reader)"

        //        is ProtocolType.Mapping -> TODO("KT Type Mapping")
        //        is ProtocolType.BitFlags -> TODO("KT Type Bit Flags")
        //        is ProtocolType.BitFields -> TODO("KT Type Bit Fields")
        //        is ProtocolType.CompareTo -> TODO("KT Type Compare To")
        //        is ProtocolType.Complex -> TODO("KT Type Complex")
        //        is ProtocolType.TopBitSetTerminatedArray -> TODO("KT Type Top Bit Set Terminated Array")
        else -> null
    }

    private fun genContainer(
        codecs: Map<String, CodecEntry>,
        value: ProtocolType.Container,
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
                    codecs = codecs,
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
                codecs = codecs,
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
                codecs = codecs,
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
        codecs: Map<String, CodecEntry>,
        name: String,
        state: String
    ) = types.types.mapNotNull { (key, value) ->
        val container = value as? ProtocolType.Container ?: return@mapNotNull null

        // generate class name descriptor or skip
        val className =
            if (key.startsWith("packet_")) "${name}${snakeToCamelCase(key.substring(7 until key.length))}"
            else return@mapNotNull null
        val myClass = ClassName("io.github.daylightnebula.meld.server", "Java$className")

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

        // create container
        genContainer(codecs, container, myClass, idProp, stateProp)
    }

    fun snakeToCamelCase(input: String) = input
        .split("_")
        .joinToString("") { it.capitalize() }

    fun lowerCamelCase(input: String) = snakeToCamelCase(input).let { it[0].lowercase() + it.substring(1 until it.length) }

    fun downloadProtocol() = protocolJson

    class Provider: SymbolProcessorProvider {
        override fun create(environment: SymbolProcessorEnvironment) =
            MeldProcessor(environment.codeGenerator, environment.logger)
    }
}
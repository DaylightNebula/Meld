package io.github.daylightnebula.meld.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFile
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import io.github.daylightnebula.meld.ksp.data.BuildJavaPackets
import kotlinx.serialization.json.Json
import java.io.File

class MeldProcessor(
    val codeGenerator: CodeGenerator,
    val logger: KSPLogger
): SymbolProcessor {

    val json = Json {
        ignoreUnknownKeys = true
    }

    // todo download protocol.json
    // todo generate packets by name
    // todo generate types
    // todo add types and params to packets

    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(BuildJavaPackets::class.qualifiedName!!)
            .forEach { if (it is KSFile) buildPacketsFile(it) }

        return emptyList<KSAnnotated>()
    }

    private fun buildPacketsFile(file: KSFile) {
        // get types
        val protocol: ProtocolFile = json.decodeFromString(downloadProtocol())
        val types = genPacketContainer(protocol.handshaking, "Handshake") +
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
        var outFile = File(file.filePath)
        (0 until numDrops).forEach { outFile = outFile.parentFile }
        if (!outFile.exists()) {
            outFile.mkdirs()
        }
        collection.writeTo(outFile)
    }

    private fun genPacketContainer(
        container: SCPacketContainer,
        name: String
    ): List<TypeSpec> =
        genPacketTypes(container.toServer, "Server${name}") +
        genPacketTypes(container.toClient, "Client${name}")

    private fun genPacketTypes(
        types: PacketTypes,
        name: String
    ): List<TypeSpec> = types.types.map { (key, arr) ->
        val className =
            if (key == "packet") name
            else "${name}${snakeToCamelCase(key.substring(7 until key.length))}"

        logger.warn("Array $arr")

        // open up file and add new types
        TypeSpec.classBuilder(className)
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
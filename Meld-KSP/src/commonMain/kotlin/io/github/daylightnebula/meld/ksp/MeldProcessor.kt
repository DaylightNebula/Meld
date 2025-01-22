package io.github.daylightnebula.meld.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo
import kotlinx.serialization.json.Json

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
        val protocol: ProtocolFile = json.decodeFromString(downloadProtocol() ?: return emptyList())
        genPacketContainer(resolver, protocol.handshaking, "Handshake")
        genPacketContainer(resolver, protocol.status, "Status")
        genPacketContainer(resolver, protocol.configuration, "Config")
        genPacketContainer(resolver, protocol.login, "Login")
        genPacketContainer(resolver, protocol.play, "Play")

        return emptyList<KSAnnotated>()
    }

    fun genPacketContainer(resolver: Resolver, container: SCPacketContainer, name: String) {
        genPacketTypes(resolver, container.toServer, "Server${name}")
        genPacketTypes(resolver, container.toClient, "Client${name}")
    }

    fun genPacketTypes(resolver: Resolver, types: PacketTypes, name: String) =
        types.types.forEach { (key, _) ->
            val className =
                if (key == "packet") name
                else "${name}${snakeToCamelCase(key.substring(7 until key.length))}"

            if (resolver.getClassDeclarationByName(resolver.getKSNameFromString(className)) != null)
                return@forEach

            try {
                val fileSpec = FileSpec.builder("io.github.daylightnebula.meld.protocol", className)
                    .addType(TypeSpec.classBuilder(className).build())
                    .build()
                fileSpec.writeTo(codeGenerator, aggregating = true)
            } catch (e: FileAlreadyExistsException) {}
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
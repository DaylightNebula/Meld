package io.github.daylightnebula.meld.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toClassName
import com.sun.tools.javac.tree.TreeInfo.symbol
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
    const val PRISMARINE_ROOT_URL = "https://raw.githubusercontent.com/PrismarineJS/minecraft-data/refs/heads/master/data/pc"
    const val TARGET_VERSION = "1.21.4"

    lateinit var codeGenerator: CodeGenerator
    lateinit var logger: KSPLogger
    val codecs = mutableMapOf<String, CodecEntry>()

    val client = HttpClient {}
    val json = Json {
        ignoreUnknownKeys = true
    }

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
        runBasicAnnotation<BuildJavaPackets>(resolver) { file, _ -> MeldPackets.buildPacketsClasses(file) }
//        resolver.getSymbolsWithAnnotation(BuildJavaPackets::class.qualifiedName!!)
//            .forEach { if (it is KSClassDeclaration) MeldPackets.buildPacketsClasses(it) }
//        resolver

        return emptyList<KSAnnotated>()
    }

    private inline fun <reified A: Annotation> runBasicAnnotation(resolver: Resolver, callback: (KSClassDeclaration, KSAnnotation) -> Unit) {
        resolver.getSymbolsWithAnnotation(A::class.qualifiedName!!).forEach { symbol ->
            if (symbol !is KSClassDeclaration) return@forEach
            val annotation = symbol.annotations
                .filter { it.shortName == resolver.getKSNameFromString(A::class.simpleName!!) }
                .first()
            callback(symbol, annotation)
        }
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
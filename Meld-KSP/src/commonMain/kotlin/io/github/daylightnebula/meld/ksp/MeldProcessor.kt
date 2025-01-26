package io.github.daylightnebula.meld.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.toClassName
import io.github.daylightnebula.meld.ksp.data.BuildPrismarineData
import io.github.daylightnebula.meld.ksp.data.RegisterCodec
import io.ktor.client.HttpClient
import io.ktor.client.request.prepareGet
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object MeldProcessor: SymbolProcessor {
    const val DATA_PATH_URL = "https://raw.githubusercontent.com/PrismarineJS/minecraft-data/refs/heads/master/data/dataPaths.json"
    const val PRISMARINE_ROOT_URL = "https://raw.githubusercontent.com/PrismarineJS/minecraft-data/refs/heads/master/data/"
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

        // build prismarine data
        runBasicAnnotation<BuildPrismarineData>(resolver) { file, _ ->
            // run all data paths
            val dataPaths = json.decodeFromString<JsonObject>(runBlocking {
                client.prepareGet { url(DATA_PATH_URL) }
                    .execute()
                    .bodyAsText()
            })

            // load all
            dataPaths["pc"]!!.jsonObject[TARGET_VERSION]!!.jsonObject.forEach { (name, element) ->
                val urlExt = element.jsonPrimitive.content
                val url = "${PRISMARINE_ROOT_URL}/$urlExt/$name.json"
                when (name) {
                    "protocol" -> MeldPackets.buildPacketsClasses(file, url)
                    "biomes" -> MeldBiomes.build(file, url)
                    else -> logger.warn("No method to decode \"$name\"")
                }
            }
        }

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
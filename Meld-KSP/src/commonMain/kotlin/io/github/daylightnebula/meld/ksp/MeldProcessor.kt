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
import io.github.daylightnebula.meld.ksp.data.RegisterPacket
import io.github.daylightnebula.meld.ksp.generators.MeldBiomes
import io.github.daylightnebula.meld.ksp.generators.MeldEntities
import io.github.daylightnebula.meld.ksp.generators.MeldPackets
import io.ktor.client.HttpClient
import io.ktor.client.request.prepareGet
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File
import kotlin.uuid.ExperimentalUuidApi

object MeldProcessor: SymbolProcessor {
    const val DATA_PATH_URL = "https://raw.githubusercontent.com/PrismarineJS/minecraft-data/refs/heads/master/data/dataPaths.json"
    const val PRISMARINE_ROOT_URL = "https://raw.githubusercontent.com/PrismarineJS/minecraft-data/refs/heads/master/data/"
    const val TARGET_VERSION = "1.21.4"

    lateinit var codeGenerator: CodeGenerator
    lateinit var logger: KSPLogger
    val codecs = mutableMapOf<String, CodecEntry>()
    val manuallyCreatedPackets = mutableListOf<String>()

    val client = HttpClient {}
    val json = Json {
        ignoreUnknownKeys = true
    }

    data class CodecEntry(val type: TypeName, val codec: ClassName, val manuallyCreated: Boolean)

    @OptIn(ExperimentalUuidApi::class)
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

        // register manually created packest
        manuallyCreatedPackets.addAll(resolver.getSymbolsWithAnnotation(RegisterPacket::class.qualifiedName!!).map {
            // get annotation
            val clazz = it as KSClassDeclaration
            val annotation = clazz.annotations
                .filter { it.shortName == resolver.getKSNameFromString("RegisterPacket") }
                .first()
            val arguments = annotation.arguments.associate { (it.name?.asString() ?: "") to it }
            arguments["overridingName"]!!.value as String
        })

        // attempt to create temp file to get build dir path
        val findFile = resolver.getAllFiles().firstOrNull { it.fileName == "TempFileToInferBuildDir.kt" }
        var buildDir = if (findFile != null) {
            val tokens = findFile.filePath.split("/")
            File(tokens.subList(0, tokens.size - 3).joinToString("/"))
        } else {
            codeGenerator.createNewFile(
                Dependencies(false),  // No dependencies; it’s a standalone file
                packageName = "filefinder",
                fileName = "TempFileToInferBuildDir"
            )
            codeGenerator.generatedFile.first()
        }

        // get build dir
        if (buildDir.path.contains("build"))
            while (buildDir.name != "build")
                buildDir = buildDir.parentFile

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
                // get file path and URL
                val urlExt = element.jsonPrimitive.content
                val filePath = "$urlExt/$name.json"
                val url = "${PRISMARINE_ROOT_URL}/$filePath"

                // attempt to get cache file
                val cacheFile = File(buildDir, "mcCache/$filePath")

                // download file if necessary
                if (!cacheFile.exists()) {
                    cacheFile.parentFile.mkdirs()
                    cacheFile.writeText(runBlocking {
                        client.prepareGet { url(url) }
                            .execute()
                            .bodyAsText()
                    })
                }

                // load file text
                val fileText = cacheFile.readText()

                // run builder
                when (name) {
                    "protocol" -> MeldPackets.buildPacketsClasses(file, fileText)
                    "biomes" -> MeldBiomes.build(file, fileText)
                    "entities" -> MeldEntities.buildEntitiesFile(file, fileText)
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
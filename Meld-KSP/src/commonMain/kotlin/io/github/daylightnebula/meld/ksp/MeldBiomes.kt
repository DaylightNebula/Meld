package io.github.daylightnebula.meld.ksp

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.sun.tools.javac.tree.TreeInfo.types
import io.github.daylightnebula.meld.ksp.MeldProcessor.client
import io.github.daylightnebula.meld.ksp.MeldProcessor.json
import io.github.daylightnebula.meld.ksp.prismarine.PrismarineBiome
import io.ktor.client.request.prepareGet
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.io.File

object MeldBiomes {
    fun build(file: KSClassDeclaration) {
        // get all biomes
        val biomeContent = runBlocking {
            client.prepareGet { url("${MeldProcessor.PRISMARINE_ROOT_URL}/${MeldProcessor.TARGET_VERSION}/biomes.json") }
                .execute()
                .bodyAsText()
        }
        val biomes = json.decodeFromString<List<PrismarineBiome>>(biomeContent)

        // build biome properties
        val properties = mapOf(
            "id" to PropertySpec.builder("id", Int::class).initializer("id").build(),
            "name" to PropertySpec.builder("name", String::class).initializer("name").build(),
            "category" to PropertySpec.builder("category", String::class).initializer("category").build(),
            "temperature" to PropertySpec.builder("temperature", Float::class).initializer("temperature").build(),
            "hasPrecipitation" to PropertySpec.builder("hasPrecipitation", Boolean::class).initializer("hasPrecipitation").build(),
            "dimension" to PropertySpec.builder("dimension", String::class).initializer("dimension").build(),
            "displayName" to PropertySpec.builder("displayName", String::class).initializer("displayName").build(),
            "color" to PropertySpec.builder("color", Int::class).initializer("color").build(),
        )

        // build constructor
        val construct = FunSpec.constructorBuilder()
            .addParameters(properties.map { (key, prop) ->
                ParameterSpec.builder(key, prop.type).build()
            })
            .build()

        // compile all biomes
        val allBiomes = biomes.joinToString(",\n") { biome ->
            """
                Biome(
                    id = ${biome.id},
                    name = "${biome.name}",
                    category = "${biome.category}",
                    temperature = ${biome.temperature}f,
                    hasPrecipitation = ${biome.hasPrecipitation},
                    dimension = "${biome.dimension}",
                    displayName = "${biome.displayName}",
                    color = ${biome.color}
                )
            """.trimIndent()
        }

        // build companion object
        val companion = TypeSpec.companionObjectBuilder()
            .addProperty(
                PropertySpec.builder(
                    name = "biomes",
                    type = ClassName("kotlin.collections", "List")
                        .parameterizedBy(ClassName("io.github.daylightnebula.meld.server.generated", "Biome"))
                )
                    .initializer("listOf(\n$allBiomes)")
                    .build()
            )
            .build()

        // build biome class
        val biomeType = TypeSpec.classBuilder("Biome")
            .addType(companion)
            .addModifiers(KModifier.DATA)
            .addProperties(properties.values)
            .primaryConstructor(construct)
            .build()

        // build final output
        val collection = FileSpec.builder(file.packageName.asString() + ".generated", "Biomes")
            .addType(biomeType)
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
}
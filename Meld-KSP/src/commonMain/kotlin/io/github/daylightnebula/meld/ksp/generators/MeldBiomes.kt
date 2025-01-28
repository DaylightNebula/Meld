package io.github.daylightnebula.meld.ksp.generators

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.github.daylightnebula.meld.ksp.MeldProcessor.json
import io.github.daylightnebula.meld.ksp.prismarine.PrismarineBiome
import java.io.File

object MeldBiomes {
    fun build(file: KSClassDeclaration, text: String) {
        // get all biomes
        val biomes = json.decodeFromString<List<PrismarineBiome>>(text)

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
        val biomeProps = biomes.map { biome ->
            PropertySpec.builder(biome.name.uppercase(), ClassName("io.github.daylightnebula.meld.server.generated", "Biome"))
                .initializer("""
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
                """.trimIndent())
                .build()
        }
        val allBiomes = biomes.joinToString(",") { biome -> biome.name.uppercase() }

        // build companion object
        val companion = TypeSpec.companionObjectBuilder()
            .addProperties(biomeProps)
            .addProperty(
                PropertySpec.builder(
                    name = "all",
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
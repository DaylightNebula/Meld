package io.github.daylightnebula.meld.ksp.generators

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import io.github.daylightnebula.meld.ksp.MeldProcessor.json
import io.github.daylightnebula.meld.ksp.prismarine.PrismarineEntity
import io.github.daylightnebula.meld.ksp.toPropPair
import jdk.jfr.internal.TypeLibrary.addType
import java.io.File

object MeldEntities {
    internal fun buildEntitiesFile(file: KSClassDeclaration, prismarineText: String) {
        val prismarine: List<PrismarineEntity> = json.decodeFromString(prismarineText)

        // generate category enum
        val catEnum = TypeSpec.enumBuilder("Category")
            .apply {
                prismarine.forEach { entity ->
                    this.addEnumConstant(entity.category.uppercase().replace(" ", "_"))
                }
            }.build()

        // generate type enum
        val subtypeEnum = TypeSpec.enumBuilder("Subtype")
            .apply {
                prismarine.forEach { entity ->
                    this.addEnumConstant(entity.type.uppercase().replace(" ", "_"))
                }
            }.build()

        // generate properties for entity data class
        val properties = mapOf(
            "id".toPropPair(Int::class),
            "internalId".toPropPair(Int::class),
            "name".toPropPair(String::class),
            "displayName".toPropPair(String::class),
            "width".toPropPair(Float::class),
            "height".toPropPair(Float::class),
            "type".toPropPair(ClassName("", "Subtype")),
            "category".toPropPair(ClassName("", "Category")),
            "metadataKeys".toPropPair(ClassName("kotlin.collections", "List").parameterizedBy(ClassName("kotlin", "String"))),
        )

        // add constructor function
        val construct = FunSpec.constructorBuilder()
            .addParameters(properties.map { (k, v) -> ParameterSpec.builder(k, v.type).build() })
            .build()

        // generate companion for above data class
        val companion = TypeSpec.companionObjectBuilder()
            .addProperties(prismarine.map { entity ->
                PropertySpec.builder(entity.name.uppercase(), ClassName("io.github.daylightnebula.meld.server.generated", "EntityType"))
                    .initializer("""
                        EntityType(
                            id = ${entity.id},
                            internalId = ${entity.internalId},
                            name = "${entity.name}",
                            displayName = "${entity.displayName}",
                            width = ${entity.width}f,
                            height = ${entity.height}f,
                            type = Subtype.${entity.type.uppercase().replace(" ", "_")},
                            category = Category.${entity.category.uppercase().replace(" ", "_")},
                            metadataKeys = listOf(${entity.metadataKeys.joinToString(", ") { "\"$it\"" }})
                        )
                    """.trimIndent())
                    .build()
            })
            .addProperty(
                PropertySpec.builder(
                    name = "all",
                    type = ClassName("kotlin.collections", "List")
                        .parameterizedBy(ClassName("io.github.daylightnebula.meld.server.generated", "EntityType"))
                )
                    .initializer("listOf(${prismarine.joinToString(", ") { it.name.uppercase() }})")
                    .build()
            )
            .build()

        // finalize data class creation
        val entityType = TypeSpec.classBuilder("EntityType")
            .addModifiers(KModifier.DATA)
            .addType(catEnum)
            .addType(subtypeEnum)
            .addType(companion)
            .addProperties(properties.values)
            .primaryConstructor(construct)
            .build()

        // save file
        val collection = FileSpec.builder(file.packageName.asString() + ".generated", "EntityType")
            .addType(entityType)
            .build()
        val numDrops = file.packageName.asString().count { it == '.' } + 2 // +2 to deal with types
        var outFile = File(file.containingFile!!.filePath)
        (0 until numDrops).forEach { outFile = outFile.parentFile }
        if (!outFile.exists()) {
            outFile.mkdirs()
        }
        collection.writeTo(outFile)
    }
}
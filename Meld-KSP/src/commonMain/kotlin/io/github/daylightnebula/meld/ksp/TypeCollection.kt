package io.github.daylightnebula.meld.ksp

import com.squareup.kotlinpoet.TypeSpec

interface TypeCollection {

    // Add a new TypeSpec to the collection
    fun addType(spec: TypeSpec)

    // Add a collection of TypeSpec's to the collection
    fun addAll(types: List<TypeSpec>) = types.forEach(this::addType)
//    fun addAll(vararg types: TypeSpec) = types.forEach(this::addType)

    // Build the final TypeSpec collection into a list of types
    fun build(): List<TypeSpec>

    // Builds and returns a flat list of types
    class ListTypeCollection(
        val list: MutableList<TypeSpec> = mutableListOf<TypeSpec>()
    ): TypeCollection {
        override fun addType(spec: TypeSpec) { list.add(spec) }
        override fun build(): List<TypeSpec> = list
    }

    // Builds and returns a TypeSpec with all types added to the internal TypeSpec.Builder
    class InternalTypeCollection(
        val outer: TypeSpec.Builder
    ): TypeCollection {
        override fun addType(spec: TypeSpec) { outer.addType(spec) }
        override fun build(): List<TypeSpec> = listOf(outer.build())
    }
}
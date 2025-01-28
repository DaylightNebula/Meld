package io.github.daylightnebula.meld.ksp

import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import java.util.Locale
import kotlin.reflect.KClass

fun snakeToCamelCase(input: String) = input
    .split("_")
    .joinToString("") { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }

fun lowerCamelCase(input: String) = snakeToCamelCase(input).let { it[0].lowercase() + it.substring(1 until it.length) }

fun String.toPropPair(type: KClass<*>) = this to PropertySpec.builder(this, type).initializer(this).build()
fun String.toPropPair(type: TypeName) = this to PropertySpec.builder(this, type).initializer(this).build()

package io.github.daylightnebula.meld.ksp

import java.util.Locale

fun snakeToCamelCase(input: String) = input
    .split("_")
    .joinToString("") { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }

fun lowerCamelCase(input: String) = snakeToCamelCase(input).let { it[0].lowercase() + it.substring(1 until it.length) }

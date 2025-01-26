package io.github.daylightnebula.meld.ksp.data

import kotlin.reflect.KClass

interface IReader {
    fun read(): Byte
    fun readMany(count: Int): ByteArray
    fun remaining(): Int
}

interface Codec<T> {
    fun encode(data: T): ByteArray
    fun decode(reader: IReader): T
}

annotation class BuildJavaPackets
annotation class BuildBiomeRegistry

@Target(AnnotationTarget.CLASS)
annotation class RegisterCodec(val target: String, val type: KClass<*>)

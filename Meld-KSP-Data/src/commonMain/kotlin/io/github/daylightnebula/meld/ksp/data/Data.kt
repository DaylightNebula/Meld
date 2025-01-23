package io.github.daylightnebula.meld.ksp.data

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

@Target(AnnotationTarget.CLASS)
annotation class RegisterCodec(val target: String)

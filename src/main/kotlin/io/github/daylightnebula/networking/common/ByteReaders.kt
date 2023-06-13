package io.github.daylightnebula.networking.common

import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import java.nio.ByteBuffer
import java.util.*

abstract class AbstractReader() {
    // important abstract functions
    abstract fun nextByte(): Byte
    abstract fun readArray(count: Int): ByteArray

    // constants
    companion object {
        const val SEGMENT_BITS = 0x7F
        const val CONTINUE_BIT = 0x80
    }

    // read a variable int from the above abstract functions
    fun readVarInt(): Int {
        var value = 0
        var position = 0
        var currentByte: Byte
        while (true) {
            currentByte = nextByte()
            value = value or (currentByte.toInt() and SEGMENT_BITS shl position)
            if (currentByte.toInt() and CONTINUE_BIT == 0) break
            position += 7
            if (position >= 32) throw RuntimeException("VarInt is too big")
        }
        return value
    }

    // simple primitive reads
    fun readBoolean(): Boolean = nextByte() > 0
    fun readUShort(): UShort = ByteBuffer.wrap(readArray(2)).getShort().toUShort()
    fun readLong() = ByteBuffer.wrap(readArray(8)).getLong(0)

    // complex object reads
    fun readVarString(): String = String(readArray(readVarInt()))
    fun readUUID(): UUID = UUID(readLong(), readLong())
}

class ChannelReader(val channel: ByteReadChannel): AbstractReader() {
    override fun nextByte(): Byte {
        return runBlocking { channel.readByte() }
    }

    override fun readArray(count: Int): ByteArray {
        return runBlocking {
            val array = ByteArray(count)
            channel.readFully(array, 0, count)
            array
        }
    }
}

class ByteArrayReader(val array: ByteArray): AbstractReader() {
    var currentByte = 0

    override fun nextByte(): Byte {
        return array[currentByte++]
    }

    override fun readArray(count: Int): ByteArray {
        val startIndex = currentByte++
        currentByte += count - 1
        return array.sliceArray(startIndex until startIndex + count)
    }
}
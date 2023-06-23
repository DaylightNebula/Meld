package io.github.daylightnebula.networking.common

import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import jdk.jshell.spi.ExecutionControl.NotImplementedException
import kotlinx.coroutines.runBlocking
import java.nio.ByteBuffer
import java.util.*
import kotlin.text.String

abstract class AbstractReader() {
    // important abstract functions
    abstract fun nextByte(): Byte
    abstract fun readArray(count: Int): ByteArray
    abstract fun reset()
    abstract fun hasNext(): Boolean
    abstract fun remaining(): Int

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
    fun readShort(): Short = ByteBuffer.wrap(readArray(2)).getShort()
    fun readUShort(): UShort = readShort().toUShort()
    fun readInt(): Int = ByteBuffer.wrap(readArray(4)).getInt()
    fun read3Int(): Int = nextByte() + (nextByte().toInt() shl 8) + (nextByte().toInt() shl 16) // reknet sends 3 byte integers sometimes
    fun readLong() = ByteBuffer.wrap(readArray(8)).getLong(0)

    // complex object reads
    fun readVarString(): String = String(readArray(readVarInt()))
    fun readShortString(): String = String(readArray(readUShort().toInt()))
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

    override fun reset() {
        throw NotImplementedException("")
    }

    override fun hasNext(): Boolean {
        return channel.availableForRead > 0
    }

    override fun remaining(): Int
        = channel.availableForRead
}

class ByteArrayReader(private val array: ByteArray): AbstractReader() {
    private var currentByte = 0

    override fun nextByte(): Byte {
        return array[currentByte++]
    }

    override fun readArray(count: Int): ByteArray {
        val startIndex = currentByte
        currentByte += count
        return array.sliceArray(startIndex until startIndex + count)
    }

    override fun reset() {
        currentByte = 0
    }

    override fun hasNext(): Boolean {
        return currentByte < array.size - 1
    }

    override fun remaining(): Int = array.size - currentByte
}

class ByteReadPacketReader(val packet: ByteReadPacket): AbstractReader() {
    override fun nextByte(): Byte = packet.readByte()
    override fun readArray(count: Int): ByteArray = packet.readBytes(count)
    override fun reset() { throw NotImplementedException("") }
    override fun hasNext(): Boolean {
        return !packet.endOfInput
    }
    override fun remaining(): Int = packet.remaining.toInt()
}
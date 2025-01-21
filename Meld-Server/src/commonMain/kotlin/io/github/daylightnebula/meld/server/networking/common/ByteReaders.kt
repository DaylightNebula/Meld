package io.github.daylightnebula.meld.server.networking.common

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.java
import io.github.daylightnebula.meld.server.utils.NotImplementedException
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.runBlocking
import kotlinx.io.readFloat
import okio.Buffer
import kotlin.experimental.and
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

abstract class AbstractReader {
    // important abstract functions
    abstract fun readByte(): Byte
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
            currentByte = readByte()
            value = value or (currentByte.toInt() and SEGMENT_BITS shl position)
            if (currentByte.toInt() and CONTINUE_BIT == 0) break
            position += 7
            if (position >= 32) throw IllegalStateException("VarInt is too big")
        }
        return value
    }

    // read var long
    open fun readVarLong(): Long {
        var value: Long = 0
        var position = 0
        var currentByte: Byte
        while (true) {
            currentByte = readByte()
            value = value or ((currentByte and SEGMENT_BITS.toByte()).toLong() shl position)
            if (currentByte.toInt() and CONTINUE_BIT == 0) break
            position += 7
            if (position >= 64) throw IllegalStateException("VarLong is too big")
        }
        return value
    }

    // simple primitive reads
    fun readBoolean(): Boolean = readByte() > 0
    fun readUByte(): UByte = readByte().toUByte()

//    fun readShort(): Short = ByteBuffer.wrap(readArray(2)).getShort()
    fun readShort(): Short = Buffer().write(readArray(2)).readShort()
    fun readUShort(): UShort = readShort().toUShort()

    fun read3Int(): Int = readByte() + (readByte().toInt() shl 8) + (readByte().toInt() shl 16) // reknet sends 3 byte integers sometimes
    fun readInt(): Int = Buffer().write(readArray(4)).readInt()

    fun readFloat(): Float = Float.fromBits(readInt())
    fun readDouble(): Double = Double.fromBits(readLong())

    fun readLong() = Buffer().write(readArray(8)).readLong()

    fun readBlockPosition(): Float3 {
        val value: Long = readLong()
        val x = (value shr 38).toInt().toFloat()
        val y = (value shl 52 shr 52).toInt().toFloat()
        val z = (value shl 26 shr 38).toInt().toFloat()
        return Float3(x, y, z)
    }

    // complex object reads
    fun readString(): String = String(readArray(readVarInt()))
    fun readShortString(): String = String(readArray(readUShort().toInt()))

    @OptIn(ExperimentalUuidApi::class)
    fun readUUID(): Uuid = Uuid.fromLongs(readLong(), readLong())
}

class ChannelReader(val channel: ByteReadChannel): AbstractReader() {
    override fun readByte(): Byte {
        return runBlocking { channel.readByte() }
    }

    override fun readArray(count: Int): ByteArray {
        return runBlocking {
            val array = ByteArray(count)
            try { channel.readFully(array, 0, count) } catch (_: Exception) {}
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

    override fun readByte(): Byte {
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
    override fun readByte(): Byte = packet.readByte()
    override fun readArray(count: Int): ByteArray = packet.readBytes(count)
    override fun reset() { throw NotImplementedException("") }
    override fun hasNext(): Boolean {
        return !packet.endOfInput
    }
    override fun remaining(): Int = packet.remaining.toInt()
}
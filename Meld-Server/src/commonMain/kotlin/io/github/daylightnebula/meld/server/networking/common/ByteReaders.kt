package io.github.daylightnebula.meld.server.networking.common

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.server.CONTINUE_BIT
import io.github.daylightnebula.meld.server.SEGMENT_BITS
import io.github.daylightnebula.meld.server.meldJson
import io.github.daylightnebula.meld.server.meldNbt
import io.github.daylightnebula.meld.server.utils.NotImplementedException
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.json.JsonObject
import net.benwoodworth.knbt.NbtCompound
import okio.Buffer
import kotlin.experimental.and
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

abstract class AbstractReader: IReader {
    // important abstract functions
    abstract fun reset()
    abstract fun hasNext(): Boolean

    // constants
    companion object {
    }

    // read a variable int from the above abstract functions
    fun readVarInt(): Int {
        var value = 0
        var position = 0
        var currentByte: Byte
        while (true) {
            currentByte = read()
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
            currentByte = read()
            value = value or ((currentByte and SEGMENT_BITS.toByte()).toLong() shl position)
            if (currentByte.toInt() and CONTINUE_BIT == 0) break
            position += 7
            if (position >= 64) throw IllegalStateException("VarLong is too big")
        }
        return value
    }

    fun readAngle() = readUByte().toInt().toFloat() / 256f * 360f

    // simple primitive reads
    fun readBoolean(): Boolean = read() > 0
    fun readUByte(): UByte = read().toUByte()
    fun readShort(): Short = Buffer().write(readMany(2)).readShort()
    fun readUShort(): UShort = readShort().toUShort()
    fun read3Int(): Int = read() + (read().toInt() shl 8) + (read().toInt() shl 16) // reknet sends 3 byte integers sometimes
    fun readInt(): Int = Buffer().write(readMany(4)).readInt()
    fun readFloat(): Float = Float.fromBits(readInt())
    fun readDouble(): Double = Double.fromBits(readLong())
    fun readLong() = Buffer().write(readMany(8)).readLong()
    fun readFloat3() = Float3(readFloat(), readFloat(), readFloat())

    fun readBlockPosition(): Float3 {
        val value: Long = readLong()
        val x = (value shr 38).toInt().toFloat()
        val y = (value shl 52 shr 52).toInt().toFloat()
        val z = (value shl 26 shr 38).toInt().toFloat()
        return Float3(x, y, z)
    }

    // complex object reads
    fun readByteArray() = readMany(remaining())
    fun readString(): String = String(readMany(readVarInt()))
    fun readShortString(): String = String(readMany(readUShort().toInt()))
    fun readJsonObject(): JsonObject = meldJson.decodeFromString(readString())
    fun readNBT(): NbtCompound = meldNbt.decodeFromByteArray(readByteArray())

    @OptIn(ExperimentalUuidApi::class)
    fun readUUID(): Uuid = Uuid.fromLongs(readLong(), readLong())
    @OptIn(ExperimentalUuidApi::class)
    fun readUuid() = readUUID()

    fun <T> readOptional(read: () -> T?): T? = if (readBoolean()) read() else null
    inline fun <reified T> readArray(read: () -> T): Array<T> = Array(readVarInt()) { read() }
}

class ChannelReader(val channel: ByteReadChannel): AbstractReader() {
    override fun read(): Byte {
        return runBlocking { channel.readByte() }
    }

    override fun readMany(count: Int): ByteArray {
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

    override fun read(): Byte {
        return array[currentByte++]
    }

    override fun readMany(count: Int): ByteArray {
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
    override fun read(): Byte = packet.readByte()
    override fun readMany(count: Int): ByteArray = packet.readBytes(count)
    override fun reset() { throw NotImplementedException("") }
    override fun hasNext(): Boolean {
        return !packet.endOfInput
    }
    override fun remaining(): Int = packet.remaining.toInt()
}
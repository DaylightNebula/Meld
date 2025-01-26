package io.github.daylightnebula.meld.server.networking.common

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.server.CONTINUE_BIT
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.SEGMENT_BITS
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
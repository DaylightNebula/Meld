package io.github.daylightnebula.meld.server.networking.common

import io.github.daylightnebula.meld.ksp.data.IReader
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.runBlocking

class ChannelReader(val channel: ByteReadChannel): IReader {
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

    override fun remaining(): Int
        = channel.availableForRead
}

class ByteArrayReader(private val array: ByteArray): IReader {
    private var currentByte = 0

    override fun read(): Byte {
        return array[currentByte++]
    }

    override fun readMany(count: Int): ByteArray {
        val startIndex = currentByte
        currentByte += count
        return array.sliceArray(startIndex until startIndex + count)
    }

    override fun remaining(): Int = array.size - currentByte
}

class ByteReadPacketReader(val packet: ByteReadPacket): IReader {
    override fun read(): Byte = packet.readByte()
    override fun readMany(count: Int): ByteArray = packet.readBytes(count)
    override fun remaining(): Int = packet.remaining.toInt()
}
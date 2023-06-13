package io.github.daylightnebula.networking.common

import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking

interface IReader {
    fun nextByte(): Byte
    fun readArray(count: Int): ByteArray
}

class ChannelReader(val channel: ByteReadChannel): IReader {
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

class ByteArrayReader(val array: ByteArray): IReader {
    var currentByte = 0

    override fun nextByte(): Byte {
        return array[currentByte++]
    }

    override fun readArray(count: Int): ByteArray {
        val startIndex = currentByte++
        return array.sliceArray(startIndex until startIndex + count)
    }
}
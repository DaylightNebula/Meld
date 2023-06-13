package io.github.daylightnebula.networking.common

import java.nio.ByteBuffer

object NetworkUtils {
    const val SEGMENT_BITS = 0x7F
    const val CONTINUE_BIT = 0x80

    fun readVarInt(reader: IReader): Int {
        var value = 0
        var position = 0
        var currentByte: Byte
        while (true) {
            currentByte = reader.nextByte()
            value = value or (currentByte.toInt() and SEGMENT_BITS shl position)
            if (currentByte.toInt() and CONTINUE_BIT == 0) break
            position += 7
            if (position >= 32) throw RuntimeException("VarInt is too big")
        }
        return value
    }

    fun readVarString(reader: IReader): String {
        val length = readVarInt(reader)
        return String(reader.readArray(length))
    }

    fun readUShort(reader: IReader, isBigEndian: Boolean): UShort {
        val bytes = reader.readArray(2) //.map { (it + 128).toUByte() }
        return if (isBigEndian) (((bytes[0].toInt() and 255) shl 8) or (bytes[1].toInt() and 255)).toUShort()
        else (((bytes[1].toInt() and 255) shl 8) or (bytes[0].toInt() and 255)).toUShort()
    }

    fun readLong(reader: IReader) = ByteBuffer.wrap(reader.readArray(8)).getLong(0)
}
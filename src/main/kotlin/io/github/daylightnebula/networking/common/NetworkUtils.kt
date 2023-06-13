package io.github.daylightnebula.networking.common

object NetworkUtils {
    private const val SEGMENT_BITS = 0x7F
    private const val CONTINUE_BIT = 0x80

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
}
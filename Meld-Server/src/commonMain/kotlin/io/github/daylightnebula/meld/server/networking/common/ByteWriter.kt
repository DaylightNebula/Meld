package io.github.daylightnebula.meld.server.networking.common

import io.github.daylightnebula.meld.server.VarIntCodec


open class ByteWriter(val id: Int, val mode: DataPacketMode) {
    private val data = mutableListOf<ByteArray>()

    // add just a byte array
    fun writeByteArray(array: ByteArray) = data.add(array)

    fun getRawData(): ByteArray {
        var offset = 0
        val output = ByteArray(data.sumOf { it.size })
        for (subdata in data) {
            subdata.copyInto(output, offset)
            offset += subdata.size
        }
        return output
    }

    // compile result
    fun getData(): ByteArray {
        // get original length of the data
        val length = data.sumOf { it.size }
        var offset = 0

        // build header
        val idLength = if (mode == DataPacketMode.BEDROCK) byteArrayOf(id.toByte()) else VarIntCodec.encode(id)
        val lengthLength =
            if (mode == DataPacketMode.BEDROCK) byteArrayOf() else VarIntCodec.encode(length + idLength.size)

        // return final output
        return byteArrayOf(
            *lengthLength,
            *idLength,
            *getRawData()
        )
    }
}

enum class DataPacketMode { JAVA, BEDROCK }
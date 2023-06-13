package io.github.daylightnebula.networking.common

import io.github.daylightnebula.networking.common.AbstractReader.Companion.CONTINUE_BIT
import io.github.daylightnebula.networking.common.AbstractReader.Companion.SEGMENT_BITS
import org.json.JSONObject
import java.nio.ByteBuffer

class DataPacket(val id: Int) {
    private val data = mutableListOf<ByteArray>()

    // add just a byte array
    fun writeByteArray(array: ByteArray) = data.add(array)

    // add a string
    fun writeString(string: String) {
        writeVarInt(string.length)
        data.add(string.toByteArray())
    }

    // add a json object
    fun writeJSON(json: JSONObject) = writeString(json.toString(0))

    // write varint
    fun writeVarInt(v: Int) = writeByteArray(convertVarInt(v))
    fun convertVarInt(v: Int): ByteArray {
        val output = mutableListOf<Byte>()
        var value = v
        while (true) {
            if (value and SEGMENT_BITS.inv() == 0) {
                output.add(value.toByte())
                return output.toByteArray()
            }
            output.add((value and SEGMENT_BITS or CONTINUE_BIT).toByte())

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value = value ushr 7
        }
    }

    // write long to output
    fun writeLong(long: Long) {
        val buffer = ByteBuffer.allocate(Long.SIZE_BYTES)
        buffer.putLong(long)
        data.add(buffer.array())
    }

    // compile result
    fun getData(): ByteArray {
        // get original length of the data
        val length = data.sumOf { it.size }

        // get id and length arrays
        val idLength = convertVarInt(id)
        val lengthLength = convertVarInt(length + idLength.size)

        // write initial output
        val output = ByteArray(length + idLength.size + lengthLength.size)
        lengthLength.copyInto(output, 0)
        idLength.copyInto(output, lengthLength.size)

        // write sub data's to the output
        var offset = idLength.size + lengthLength.size
        for (subdata in data) {
            subdata.copyInto(output, offset)
            offset += subdata.size
        }

        return output
    }
}
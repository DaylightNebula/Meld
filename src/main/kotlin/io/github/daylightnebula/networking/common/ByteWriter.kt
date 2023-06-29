package io.github.daylightnebula.networking.common

import io.github.daylightnebula.networking.common.AbstractReader.Companion.CONTINUE_BIT
import io.github.daylightnebula.networking.common.AbstractReader.Companion.SEGMENT_BITS
import org.json.JSONObject
import java.io.OutputStream
import java.nio.ByteBuffer

open class ByteWriter(val id: Int, val mode: DataPacketMode) {
    private val data = mutableListOf<ByteArray>()

    // add just a byte array
    fun writeByteArray(array: ByteArray) = data.add(array)

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

    // write primitives
    fun writeUByte(byte: UByte) { data.add(byteArrayOf(byte.toByte())) }
    fun writeByte(byte: Byte) { data.add(ByteArray(1) { byte }) }
    fun writeBoolean(bool: Boolean) { data.add(ByteBuffer.allocate(1).put(if (bool) 0x01 else 0x00).array()) }
    fun writeShort(short: Short) { data.add(ByteBuffer.allocate(2).putShort(short).array()) }
    fun writeUShort(short: UShort) { data.add(ByteBuffer.allocate(2).putShort(short.toShort()).array()) }
    fun write3Int(int: Int) { data.add(ByteBuffer.allocate(4).putInt(int).array().slice(0 until 3).toByteArray()) }
    fun writeInt(int: Int) { data.add(ByteBuffer.allocate(4).putInt(int).array()) }
    fun writeLong(long: Long) { data.add(ByteBuffer.allocate(8).putLong(long).array()) }

    // NBT
//    fun writeNBT(compound: NBT<*>) {
//        data.add(compound.encode())
//    }

    // write complex objects
    fun writeString(string: String) { if (mode == DataPacketMode.BEDROCK) writeShort(string.length.toShort()) else writeVarInt(string.length); data.add(string.toByteArray()) }
    fun writeJSON(json: JSONObject) = writeString(json.toString(0))

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
        val idLength = if (mode == DataPacketMode.BEDROCK) byteArrayOf(id.toByte()) else convertVarInt(id)
        val lengthLength =
            if (mode == DataPacketMode.BEDROCK) byteArrayOf() else convertVarInt(length + idLength.size)

        // return final output
        return byteArrayOf(
            *lengthLength,
            *idLength,
            *getRawData()
        )
    }
}

enum class DataPacketMode { JAVA, BEDROCK }
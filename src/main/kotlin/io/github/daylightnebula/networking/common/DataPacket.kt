package io.github.daylightnebula.networking.common

import io.github.daylightnebula.networking.common.AbstractReader.Companion.CONTINUE_BIT
import io.github.daylightnebula.networking.common.AbstractReader.Companion.SEGMENT_BITS
import kotlinx.serialization.encodeToByteArray
import net.benwoodworth.knbt.Nbt
import net.benwoodworth.knbt.NbtCompound
import net.benwoodworth.knbt.NbtCompression
import net.benwoodworth.knbt.NbtVariant
import org.json.JSONObject
import java.nio.ByteBuffer

class DataPacket(val id: Int, val mode: DataPacketMode) {
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
    fun writeByte(byte: Byte) { data.add(ByteArray(1) { byte }) }
    fun writeBoolean(bool: Boolean) { data.add(ByteBuffer.allocate(1).put(if (bool) 0x01 else 0x00).array()) }
    fun writeInt(int: Int) { data.add(ByteBuffer.allocate(4).putInt(int).array()) }
    fun writeShort(short: Short) { data.add(ByteBuffer.allocate(2).putShort(short).array()) }
    fun writeUShort(short: UShort) { data.add(ByteBuffer.allocate(2).putShort(short.toShort()).array()) }
    fun writeLong(long: Long) { data.add(ByteBuffer.allocate(8).putLong(long).array()) }

    // NBT
    fun writeNBTCompound(compound: NbtCompound) {
        val nbt = Nbt {
            variant = when(mode) {
                DataPacketMode.BEDROCK -> NbtVariant.Bedrock
                DataPacketMode.JAVA -> NbtVariant.Java
            }
            compression = NbtCompression.None
        }
        val bytes = nbt.encodeToByteArray(compound)
        data.add(bytes)
    }

    // write complex objects
    fun writeString(string: String) { if (mode == DataPacketMode.BEDROCK) writeShort(string.length.toShort()) else writeVarInt(string.length); data.add(string.toByteArray()) }
    fun writeJSON(json: JSONObject) = writeString(json.toString(0))

    // compile result
    fun getData(): ByteArray {
        // get original length of the data
        val length = data.sumOf { it.size }

        // get id and length arrays
        val idLength = convertVarInt(id)
        val lengthLength = if (mode == DataPacketMode.BEDROCK) byteArrayOf() else convertVarInt(length + idLength.size)

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

enum class DataPacketMode { JAVA, BEDROCK }
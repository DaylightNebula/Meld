package io.github.daylightnebula.meld.server.networking.common

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.meldJson
import io.github.daylightnebula.meld.server.meldNbt
import io.github.daylightnebula.meld.server.networking.common.AbstractReader.Companion.CONTINUE_BIT
import io.github.daylightnebula.meld.server.networking.common.AbstractReader.Companion.SEGMENT_BITS
import io.github.daylightnebula.meld.server.utils.ItemContainer
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonObject
import net.benwoodworth.knbt.NbtCompound
import okio.Buffer


open class ByteWriter(val id: Int, val mode: DataPacketMode) {
    private val data = mutableListOf<ByteArray>()

    // add just a byte array
    fun writeByteArray(array: ByteArray) = data.add(array)

    // write var int
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

    // write var long
    fun writeVarLong(value: Long) = writeByteArray(convertVarLong(value))
    fun convertVarLong(value: Long): ByteArray {
        val output = mutableListOf<Byte>()
        var value = value
        while (true) {
            if (value and SEGMENT_BITS.toLong().inv() == 0L) {
                output.add(value.toByte())
                return output.toByteArray()
            }
            output.add((value and SEGMENT_BITS.toLong() or CONTINUE_BIT.toLong()).toByte())

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value = value ushr 7
        }
    }

    // write item to output
    fun writeItem(item: ItemContainer?) {
        // write if present
        writeBoolean(item != null)
        if (item != null) {
            // write basic info
            writeVarInt(item.id)
            writeByte(item.count)

            // write nbt if present otherwise a 0
            TODO("Write item container NBT")
//            if (item.nbt != null) writeNBT(item.nbt)
//            else writeByte(0x00)
        }
    }

    // write primitives
    fun writeUByte(byte: UByte) { data.add(byteArrayOf(byte.toByte())) }
    fun writeByte(byte: Byte) { data.add(ByteArray(1) { byte }) }
    fun writeBoolean(bool: Boolean) { data.add(byteArrayOf(if (bool) 1 else 0)) }

    fun writeShort(short: Short) { data.add(Buffer().writeShort(short.toInt()).readByteArray()) }
    fun writeUShort(short: UShort) { data.add(Buffer().writeShort(short.toShort().toInt()).readByteArray()) }
    fun writeInt(int: Int) { data.add(Buffer().writeInt(int).readByteArray()) }

    fun writeFloat(float: Float) { data.add(Buffer().writeInt(float.toBits()).readByteArray()) }
    fun writeDouble(double: Double) { data.add(Buffer().writeLong(double.toBits()).readByteArray()) }

    fun writeLong(long: Long) { data.add(Buffer().writeLong(long).readByteArray()) }

    fun writeBlockPosition(position: Float3) =
        writeLong(position.x.toLong() and 0x3FFFFFFL shl 38 or
                (position.z.toLong() and 0x3FFFFFFL shl 12) or
                (position.y.toLong() and 0xFFFL))

    // NBT
    fun writeNBT(compound: NbtCompound) {
        val out = meldNbt.encodeToByteArray(compound)
        data.add(out)

//        buffer.writeByte(0x0A)
//        val writer = NBTWriter(object : OutputStream() {
//            override fun write(b: Int) {
//                buffer.writeByte(b.toByte())
//            }
//        }, CompressedProcesser.NONE)
//        writer.writeRaw(compound)
//        data.add(buffer.getRawData())
    }

    // write complex objects
    fun writeString(string: String) {
        val bytes = string.toByteArray()
        if (mode == DataPacketMode.BEDROCK) writeShort(string.length.toShort())
        else writeVarInt(string.length)
        data.add(bytes)
    }
    fun writeJSON(json: JsonObject) = writeString(meldJson.encodeToString(json))

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
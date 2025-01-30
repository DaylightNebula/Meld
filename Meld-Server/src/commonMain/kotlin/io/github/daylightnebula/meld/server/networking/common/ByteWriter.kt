package io.github.daylightnebula.meld.server.networking.common

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.VarIntCodec
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

    // write primitives
    fun writeVarInt(int: Int) { data.add(VarIntCodec.encode(int)) }
    fun writeUByte(byte: UByte) { data.add(byteArrayOf(byte.toByte())) }
    fun writeByte(byte: Byte) { data.add(ByteArray(1) { byte }) }
    fun writeBoolean(bool: Boolean) { data.add(byteArrayOf(if (bool) 1 else 0)) }
    fun writeShort(short: Short) { data.add(Buffer().writeShort(short.toInt()).readByteArray()) }
    fun writeUShort(short: UShort) { data.add(Buffer().writeShort(short.toShort().toInt()).readByteArray()) }
    fun writeInt(int: Int) { data.add(Buffer().writeInt(int).readByteArray()) }
    fun writeFloat(float: Float) { data.add(Buffer().writeInt(float.toBits()).readByteArray()) }
    fun writeDouble(double: Double) { data.add(Buffer().writeLong(double.toBits()).readByteArray()) }
    fun writeLong(long: Long) { data.add(Buffer().writeLong(long).readByteArray()) }
    fun writeAngle(angle: Float) = writeUByte((angle / 360f * 256f) as UByte)
    fun writeFloat3(vec: Float3) { writeFloat(vec.x); writeFloat(vec.y); writeFloat(vec.z) }

    fun writeBlockPosition(position: Float3) =
        writeLong(position.x.toLong() and 0x3FFFFFFL shl 38 or
                (position.z.toLong() and 0x3FFFFFFL shl 12) or
                (position.y.toLong() and 0xFFFL))

    // write complex objects
    fun writeString(string: String) {
        val bytes = string.toByteArray()
        if (mode == DataPacketMode.BEDROCK) writeShort(string.length.toShort())
        else writeVarInt(string.length)
        data.add(bytes)
    }
    fun writeNBT(compound: NbtCompound) = data.add(Meld.nbt.encodeToByteArray(compound))
    fun writeJSON(json: JsonObject) = writeString(Meld.json.encodeToString(json))
    fun writeJsonObject(json: JsonObject) = writeJSON(json)
}

enum class DataPacketMode { JAVA, BEDROCK }
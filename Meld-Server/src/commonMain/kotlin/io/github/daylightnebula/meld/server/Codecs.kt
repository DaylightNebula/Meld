package io.github.daylightnebula.meld.server

import io.github.daylightnebula.meld.ksp.data.Codec
import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.ksp.data.RegisterCodec
import io.ktor.utils.io.core.String
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import net.benwoodworth.knbt.NbtString
import net.benwoodworth.knbt.NbtTag
import okio.Buffer
import kotlin.experimental.and
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

const val SEGMENT_BITS = 0x7F
const val CONTINUE_BIT = 0x80

@RegisterCodec("bool", Boolean::class)
object BoolCodec: Codec<Boolean> {
    override fun encode(data: Boolean) = if (data) byteArrayOf(0x01) else byteArrayOf(0x00)

    override fun decode(reader: IReader) = reader.read() > 0
}

@RegisterCodec("i8", Byte::class)
object ByteCodec: Codec<Byte> {
    override fun encode(data: Byte) = byteArrayOf(data)
    override fun decode(reader: IReader) = reader.read()
}

@RegisterCodec("i32", Int::class)
object IntCodec: Codec<Int> {
    override fun decode(reader: IReader) = Buffer().write(reader.readMany(4)).readInt()
    override fun encode(data: Int) = Buffer().writeInt(data).readByteArray()
}

@RegisterCodec("i64", Long::class)
object LongCodec: Codec<Long> {
    override fun encode(data: Long) = Buffer().writeLong(data).readByteArray()
    override fun decode(reader: IReader) = Buffer().write(reader.readMany(8)).readLong()
}

@RegisterCodec("u8", UByte::class)
object UByteCodec: Codec<UByte> {
    override fun encode(data: UByte) = byteArrayOf(data.toByte())
    override fun decode(reader: IReader) = reader.read().toUByte()
}

@RegisterCodec("u16", UShort::class)
object UShortCodec: Codec<UShort> {
    override fun decode(reader: IReader) = Buffer().write(reader.readMany(2)).readShort().toUShort()
    override fun encode(data: UShort) = Buffer().writeShort(data.toInt()).readByteArray()
}

@RegisterCodec("varint", Int::class)
object VarIntCodec: Codec<Int> {
    override fun decode(reader: IReader): Int {
        var value = 0
        var position = 0
        var currentByte: Byte
        while (true) {
            currentByte = reader.read()
            value = value or (currentByte.toInt() and SEGMENT_BITS shl position)
            if (currentByte.toInt() and CONTINUE_BIT == 0) break
            position += 7
            if (position >= 32) throw IllegalStateException("VarInt is too big")
        }
        return value
    }

    override fun encode(data: Int): ByteArray {
        val output = mutableListOf<Byte>()
        var value = data
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
}

@RegisterCodec("varlong", Long::class)
object VarLongCodec: Codec<Long> {
    override fun decode(reader: IReader): Long {
        var value: Long = 0
        var position = 0
        var currentByte: Byte
        while (true) {
            currentByte = reader.read()
            value = value or ((currentByte and SEGMENT_BITS.toByte()).toLong() shl position)
            if (currentByte.toInt() and CONTINUE_BIT == 0) break
            position += 7
            if (position >= 64) throw IllegalStateException("VarLong is too big")
        }
        return value
    }

    override fun encode(data: Long): ByteArray {
        val output = mutableListOf<Byte>()
        var value = data
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
}

@RegisterCodec("string", String::class)
object StringCodec: Codec<String> {
    override fun encode(data: String) = VarIntCodec.encode(data.length) + data.toByteArray()
    override fun decode(reader: IReader) = String(reader.readMany(VarIntCodec.decode(reader)))
}

@OptIn(ExperimentalUuidApi::class)
@RegisterCodec("UUID", Uuid::class)
object UuidCodec: Codec<Uuid> {
    override fun encode(data: Uuid) = data.toLongs { most, least -> LongCodec.encode(most) + LongCodec.encode(least) }
    override fun decode(reader: IReader) = Uuid.fromByteArray(reader.readMany(16))
}

@RegisterCodec("restBuffer", ByteArray::class)
object RestBufferCodec: Codec<ByteArray> {
    override fun encode(data: ByteArray) = data
    override fun decode(reader: IReader) = reader.readMany(reader.remaining())
}

@RegisterCodec("anonymousNbt", NbtTag::class)
object AnonymousNBT: Codec<NbtTag> {
    override fun encode(data: NbtTag) = meldNbt.encodeToByteArray(data)
    override fun decode(reader: IReader) = TODO()
}

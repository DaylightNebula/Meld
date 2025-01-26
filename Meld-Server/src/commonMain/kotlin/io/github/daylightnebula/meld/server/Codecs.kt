package io.github.daylightnebula.meld.server

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import dev.romainguy.kotlin.math.Float4
import io.github.daylightnebula.meld.ksp.data.Codec
import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.ksp.data.RegisterCodec
import io.github.daylightnebula.meld.server.VarIntCodec
import io.ktor.utils.io.core.String
import io.ktor.utils.io.core.toByteArray
import kotlinx.io.readDouble
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

@RegisterCodec("i16", Short::class)
object ShortCodec: Codec<Short> {
    override fun encode(data: Short) = Buffer().writeShort(data.toInt()).readByteArray()
    override fun decode(reader: IReader) = Buffer().write(reader.readMany(2)).readShort()
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

@RegisterCodec("u32", UInt::class)
object UIntCodec: Codec<UInt> {
    override fun decode(reader: IReader) = Buffer().write(reader.readMany(4)).readInt().toUInt()
    override fun encode(data: UInt) = Buffer().writeInt(data.toInt()).readByteArray()
}

@RegisterCodec("u64", ULong::class)
object ULongCodec: Codec<ULong> {
    override fun decode(reader: IReader) = Buffer().write(reader.readMany(8)).readLong().toULong()
    override fun encode(data: ULong) = Buffer().writeLong(data.toLong()).readByteArray()
}

@RegisterCodec("f32", Float::class)
object FloatCodec: Codec<Float> {
    override fun encode(data: Float) = Buffer().writeInt(data.toBits()).readByteArray()
    override fun decode(reader: IReader) = Float.fromBits(Buffer().write(reader.readMany(4)).readInt())
}

@RegisterCodec("f64", Double::class)
object DoubleCodec: Codec<Double> {
    override fun decode(reader: IReader) = Double.fromBits(LongCodec.decode(reader))
    override fun encode(data: Double) = LongCodec.encode(data.toBits())
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
    override fun decode(reader: IReader) = reader.readMany(VarIntCodec.decode(reader)).decodeToString()
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
    override fun encode(data: NbtTag) = Meld.nbt.encodeToByteArray(data)
    override fun decode(reader: IReader) = TODO()
}

interface IDSet {
    fun encode(): ByteArray

    companion object {
        fun decode(reader: IReader): IDSet {
            val len = VarIntCodec.decode(reader)
            return if (len == 0) IDSetIdentifier(StringCodec.decode(reader))
            else IDSetIDs((0 until (len - 1)).map { VarIntCodec.decode(reader) })
        }
    }

    class IDSetIdentifier(val id: String): IDSet {
        override fun encode() = VarIntCodec.encode(0) + StringCodec.encode(id)
    }

    class IDSetIDs(val ids: List<Int>): IDSet {
        override fun encode() = VarIntCodec.encode(ids.size + 1) +
                ids.map { id -> VarIntCodec.encode(id) }.fold(byteArrayOf()) { acc, bytes -> acc + bytes }
    }
}

@RegisterCodec("IDSet", IDSet::class)
object IDSetCodec: Codec<IDSet> {
    override fun decode(reader: IReader) = IDSet.decode(reader)
    override fun encode(data: IDSet) = data.encode()
}

@RegisterCodec("ByteArray", ByteArray::class)
object StdByteArrayCodec: Codec<ByteArray> {
    override fun decode(reader: IReader) = reader.readMany(VarIntCodec.decode(reader))
    override fun encode(data: ByteArray) = VarIntCodec.encode(data.size) + data
}

@RegisterCodec("vec2f", Float2::class)
object Float2Codec: Codec<Float2> {
    override fun decode(reader: IReader) = Float2(FloatCodec.decode(reader), FloatCodec.decode(reader))
    override fun encode(data: Float2) = FloatCodec.encode(data.x) + FloatCodec.encode(data.y)
}

@RegisterCodec("vec2f64", Float2::class)
object Float264Codec: Codec<Float2> {
    override fun decode(reader: IReader) = Float2(DoubleCodec.decode(reader).toFloat(), DoubleCodec.decode(reader).toFloat())
    override fun encode(data: Float2) = DoubleCodec.encode(data.x.toDouble()) + DoubleCodec.encode(data.y.toDouble())
}

@RegisterCodec("vec3f", Float3::class)
object Float3Codec: Codec<Float3> {
    override fun decode(reader: IReader) = Float3(FloatCodec.decode(reader), FloatCodec.decode(reader), FloatCodec.decode(reader))
    override fun encode(data: Float3) = FloatCodec.encode(data.x) + FloatCodec.encode(data.y) + FloatCodec.encode(data.z)
}

@RegisterCodec("vec3f64", Float3::class)
object Float364Codec: Codec<Float3> {
    override fun decode(reader: IReader) = Float3(DoubleCodec.decode(reader).toFloat(), DoubleCodec.decode(reader).toFloat(), DoubleCodec.decode(reader).toFloat())
    override fun encode(data: Float3) = DoubleCodec.encode(data.x.toDouble()) + DoubleCodec.encode(data.y.toDouble()) + DoubleCodec.encode(data.z.toDouble())
}

@RegisterCodec("vec4f", Float4::class)
object Float4Codec: Codec<Float4> {
    override fun decode(reader: IReader) = Float4(FloatCodec.decode(reader), FloatCodec.decode(reader), FloatCodec.decode(reader), FloatCodec.decode(reader))
    override fun encode(data: Float4) = FloatCodec.encode(data.x) + FloatCodec.encode(data.y) + FloatCodec.encode(data.z) + FloatCodec.encode(data.w)
}

@RegisterCodec("vec4f64", Float4::class)
object Float464Codec: Codec<Float4> {
    override fun decode(reader: IReader) = Float4(DoubleCodec.decode(reader).toFloat(), DoubleCodec.decode(reader).toFloat(), DoubleCodec.decode(reader).toFloat(), DoubleCodec.decode(reader).toFloat())
    override fun encode(data: Float4) = DoubleCodec.encode(data.x.toDouble()) + DoubleCodec.encode(data.y.toDouble()) + DoubleCodec.encode(data.z.toDouble()) + DoubleCodec.encode(data.w.toDouble())
}

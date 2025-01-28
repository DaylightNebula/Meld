package io.github.daylightnebula.meld.server.utils

import io.github.daylightnebula.meld.ksp.data.Codec
import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.ksp.data.RegisterCodec

class BitFlagSet(
    internal val flags: BooleanArray = BooleanArray(8),
) {
    fun get(flag: Int) = flags[flag]
    fun set(flag: Int, value: Boolean) { flags[flag] = value }
}

@RegisterCodec("bitflags", BitFlagSet::class)
object BitFlagCodec: Codec<BitFlagSet> {
    override fun encode(data: BitFlagSet): ByteArray {
        var result = 0
        for (i in data.flags.indices) {
            if (data.flags[i]) {
                result = result or (1 shl i)
            }
        }
        return byteArrayOf(result.toByte())
    }

    override fun decode(reader: IReader): BitFlagSet {
        val byte = reader.read()
        val booleans = BooleanArray(8)
        for (i in 0..7) {
            booleans[i] = (byte.toInt() shr i and 1) == 1
        }
        return BitFlagSet(booleans)
    }
}

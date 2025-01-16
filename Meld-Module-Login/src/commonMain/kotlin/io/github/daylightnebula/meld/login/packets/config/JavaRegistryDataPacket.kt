package io.github.daylightnebula.meld.login.packets.config

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.server.registries.RegistryCodec
import net.benwoodworth.knbt.NbtCompound

class JavaRegistryDataPacket(
    val name: String,
    val data: List<Pair<String, NbtCompound?>>
): JavaPacket {

    constructor(codec: RegistryCodec.Codec): this(codec.name(), codec.build())

    override val OUTGOING_ID: Int = 0x07
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeString(name)
        writer.writeVarInt(data.size)
        data.forEach { (key, value) ->
            writer.writeString(key)
            writer.writeBoolean(value != null)
            if (value != null) writer.writeNBT(value)
        }
    }
}
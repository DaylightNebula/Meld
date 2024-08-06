package io.github.daylightnebula.meld.login.packets.config

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.server.registries.RegistryCodec
import org.jglrxavpok.hephaistos.nbt.NBTCompound

class JavaRegistryDataPacket(
    val registryCodec: NBTCompound = RegistryCodec.nbt
): JavaPacket {
    override val id: Int = 0x07
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeNBT(registryCodec)
    }
}
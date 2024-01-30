package io.github.daylightnebula.meld.world.chunks

import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.common.DataPacketMode
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.jglrxavpok.hephaistos.nbt.NBTLongArray

object ChunkRegistry {
    val defaultHeightmap: NBTCompound = NBT.Compound(
        mapOf<String, NBTLongArray>(
            "MOTION_BLOCKING" to NBT.LongArray(*LongArray(37) { 0x100804020100804 }),
            "WORLD_SURFACE" to NBT.LongArray(*LongArray(37) { 0x100804020100804 }),
//            "WORLD_SURFACE" to NBT.LongArray(*net.minestom.server.instance.DynamicChunk.encodeBlocks(worldSurface, bitsForHeight))
        )
    )

    val emptyChunk: ByteArray

    init {
        val writer = ByteWriter(0, DataPacketMode.JAVA)

        // for all 24 sections
        repeat(24) {
            // number of non-air block palettes in the section
            writer.writeShort(1)

            // empty block palette
            writer.writeUByte(0u)     // bits per entry
            writer.writeVarInt(1)       // single value of id 0 (air)
            writer.writeVarInt(0)       //

            // empty biomes palette
            writer.writeUByte(0u)
            writer.writeVarInt(0)
            writer.writeVarInt(0)
        }

        emptyChunk = writer.getRawData()
    }
}
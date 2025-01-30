package io.github.daylightnebula.meld.server.world.chunks

import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.common.DataPacketMode
import net.benwoodworth.knbt.NbtCompound
import net.benwoodworth.knbt.NbtLongArray
import net.benwoodworth.knbt.NbtTag

object ChunkRegistry {
    val defaultHeightmap: NbtCompound = NbtCompound(
        mapOf<String, NbtTag>(
            "MOTION_BLOCKING" to NbtLongArray(listOf(
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                26761596615
            )),
            "WORLD_SURFACE" to NbtLongArray(listOf(
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                3591880695370059463,
                26761596615
            ))
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
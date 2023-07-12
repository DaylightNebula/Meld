package io.github.daylightnebula.inventories

import io.github.daylightnebula.*
import io.github.daylightnebula.inventories.packets.JavaCloseInventoryPacket
import io.github.daylightnebula.inventories.packets.JavaCreativeModeSlotPacket
import io.github.daylightnebula.inventories.packets.JavaSetSelectedSlotPacket
import io.github.daylightnebula.networking.java.JavaConnectionState
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.inventories.packets.JavaUseItemPacket
import io.github.daylightnebula.worlds.World
import io.github.daylightnebula.worlds.chunks.toChunkPosition

class InventoryBundle: PacketBundle(
    bedrock(),
    java(
        JavaUseItemPacket::class.java.name to { connection, packet ->
            packet as JavaUseItemPacket
            val player = connection.player!!
            val selectedItem = player.inventory.getItem(player.inventory.selectedSlot + 36)
            if (selectedItem != null) {
                val placeLocation = packet.location.clone().add(packet.face.offset.clone().mul(-1)).add(0, 1, 0)
                val chunkPos = placeLocation.toChunkPosition()
                val chunk = World.dimensions[player.dimensionID]?.loadedChunks?.get(chunkPos)
                    ?: throw RuntimeException("No chunk $chunkPos found for place block")
                chunk.setBlock(player, placeLocation, selectedItem.id)
            } else println("No selected")
        },

        JavaSetSelectedSlotPacket::class.java.name to { connection, packet ->
            packet as JavaSetSelectedSlotPacket
            connection.player!!.inventory.selectedSlot = packet.slot
            // TODO broadcast change
        },

        JavaCloseInventoryPacket::class.java.name to { connection, packet ->
            packet as JavaCloseInventoryPacket
            println("Received close inventory ${packet.inventoryID}")
            // TODO handle close inventory
        },

        JavaCreativeModeSlotPacket::class.java.name to { connection, packet ->
            packet as JavaCreativeModeSlotPacket
            connection.player!!.inventory.setItem(packet.slot, packet.item)
            println("Packet creative mode slot ${packet.slot} ${packet.item}")
        }
    )
) {
    override fun registerJavaPackets(): HashMap<Pair<Int, JavaConnectionState>, () -> JavaPacket> = javaPackets(
        javaGamePacket(0x31) to { JavaUseItemPacket() },
        javaGamePacket(0x28) to { JavaSetSelectedSlotPacket() },
        javaGamePacket(0x0C) to { JavaCloseInventoryPacket() },
        javaGamePacket(0x2B) to { JavaCreativeModeSlotPacket() }
    )
}
package io.github.daylightnebula.player

import io.github.daylightnebula.*
import io.github.daylightnebula.networking.java.JavaConnectionState
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.player.extensions.handleBlockAction
import io.github.daylightnebula.player.packets.*
import io.github.daylightnebula.player.packets.join.JavaClientInfoPacket
import io.github.daylightnebula.player.packets.join.JavaPluginMessagePacket
import org.cloudburstmc.protocol.bedrock.packet.AnimatePacket
import org.cloudburstmc.protocol.bedrock.packet.ChunkRadiusUpdatedPacket
import org.cloudburstmc.protocol.bedrock.packet.EmoteListPacket
import org.cloudburstmc.protocol.bedrock.packet.InteractPacket
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket
import org.cloudburstmc.protocol.bedrock.packet.MovePlayerPacket
import org.cloudburstmc.protocol.bedrock.packet.RequestChunkRadiusPacket
import org.cloudburstmc.protocol.bedrock.packet.SetLocalPlayerAsInitializedPacket
import org.cloudburstmc.protocol.bedrock.packet.TickSyncPacket

// TODO handle https://wiki.vg/Protocol#Client_Information
// TODO handle https://wiki.vg/Protocol#Plugin_Message
// TODO handle https://wiki.vg/Protocol#Set_Player_Position_and_Rotation
class PlayerBundle: PacketBundle(
    bedrock(
        // handle chunk radius packets
        RequestChunkRadiusPacket::class.java.name to { connection, packet ->
            packet as RequestChunkRadiusPacket
            connection.sendPacket(ChunkRadiusUpdatedPacket().apply {
                radius = Meld.viewDistance
            })
        },

        // the packets we don't care about
        TickSyncPacket::class.java.name to { _, _ -> },
        SetLocalPlayerAsInitializedPacket::class.java.name to { _, _ -> },

        // emotes
        EmoteListPacket::class.java.name to { connection, packet ->
            println("TODO what about emotes?")
        },

        MovePlayerPacket::class.java.name to { connection, packet ->
            packet as MovePlayerPacket
//            println("Position: ${packet.position.x} ${packet.position.y} ${packet.position.z} ${packet.rotation.x} ${packet.rotation.y} ${packet.isOnGround}")
        },

        AnimatePacket::class.java.name to { connection, packet ->
            println("TODO sync animations here")
        },

        LevelSoundEventPacket::class.java.name to { connection, packet ->
            println("TODO sync sounds")
        },

        InteractPacket::class.java.name to { connection, packet ->
            packet as InteractPacket
            println("Received interaction for ${packet.action}")
        }
    ),
    java(
        JavaKeepAlivePacket::class.java.name to { connection, packet -> },

        JavaReceivePlayerPositionPacket::class.java.name to { connection, packet ->
            packet as JavaReceivePlayerPositionPacket
//            println("Position: ${packet.x} ${packet.y} ${packet.z} ${packet.onGround}")
        },

        JavaReceivePlayerPositionAndRotationPacket::class.java.name to { connection, packet ->
            packet as JavaReceivePlayerPositionAndRotationPacket
//            println("Position: ${packet.x} ${packet.y} ${packet.z} Rotation: ${packet.yaw} ${packet.pitch} ${packet.onGround}")
        },

        JavaReceivePlayerRotationPacket::class.java.name to { connection, packet ->
            packet as JavaReceivePlayerRotationPacket
//            println("Rotation: ${packet.yaw} ${packet.pitch} ${packet.onGround}")
        },

        JavaClientInfoPacket::class.java.name to { connection, packet ->
            packet as JavaClientInfoPacket
            println("TODO handle client info packet")
        },

        JavaPluginMessagePacket::class.java.name to { connection, packet ->
            packet as JavaPluginMessagePacket
            when (packet.channel) {
                "minecraft:brand" -> {
                    connection.sendPacket(JavaPluginMessagePacket("minecraft:brand", packet.data))
                }
                else -> println("Unknown plugin message channel ${packet.channel}")
            }
        },

        JavaConfirmTeleportPacket::class.java.name to { connection, packet ->
            packet as JavaConfirmTeleportPacket
            println("Teleport ${packet.teleportID} confirmed")
        },

        JavaReceivePlayerAbilitiesPacket::class.java.name to { connection, packet ->
            packet as JavaReceivePlayerAbilitiesPacket
            println("Received player abilities ${packet.flags}")
        },

        JavaPlayerCommandPacket::class.java.name to { connection, packet ->
            packet as JavaPlayerCommandPacket
            println("TODO handle player command action ${packet.action}")
        },

        JavaBlockActionPacket::class.java.name to { connection, packet ->
            packet as JavaBlockActionPacket
            connection.player!!.handleBlockAction(packet.action, packet.blockPosition, packet.face)
        },

        JavaSwingArmPacket::class.java.name to { connection, packet ->
            packet as JavaSwingArmPacket
            println("TODO handle player swing hand")
        },

        JavaUseItemPacket::class.java.name to { connection, packet ->
            packet as JavaUseItemPacket
            println("TODO Use item packet: ${packet.hand} ${packet.location} ${packet.face} ${packet.cursorPosition} ${packet.insideBlock} ${packet.sequence}")
        }
    )
) {
    override fun registerJavaPackets(): HashMap<Pair<Int, JavaConnectionState>, () -> JavaPacket> = javaPackets(
        javaGamePacket(0x12) to { JavaKeepAlivePacket() },
        javaGamePacket(0x14) to { JavaReceivePlayerPositionPacket() },
        javaGamePacket(0x16) to { JavaReceivePlayerRotationPacket() },
        javaGamePacket(0x15) to { JavaReceivePlayerPositionAndRotationPacket() },
        javaGamePacket(0x08) to { JavaClientInfoPacket() },
        javaGamePacket(0x0D) to { JavaPluginMessagePacket() },
        javaGamePacket(0x00) to { JavaConfirmTeleportPacket() },
        javaGamePacket(0x1C) to { JavaReceivePlayerAbilitiesPacket() },
        javaGamePacket(0x1E) to { JavaPlayerCommandPacket() },
        javaGamePacket(0x2F) to { JavaSwingArmPacket() },
        javaGamePacket(0x1D) to { JavaBlockActionPacket() },
        javaGamePacket(0x31) to { JavaUseItemPacket() }
    )
}
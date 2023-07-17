package io.github.daylightnebula.meld.player

import io.github.daylightnebula.meld.entities.EntityAnimation
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.player.packets.join.JavaClientInfoPacket
import io.github.daylightnebula.meld.player.packets.join.JavaPluginMessagePacket
import io.github.daylightnebula.meld.player.extensions.player
import io.github.daylightnebula.meld.player.packets.*
import io.github.daylightnebula.meld.server.javaGamePacket
import io.github.daylightnebula.meld.server.javaPackets
import io.github.daylightnebula.meld.server.utils.BlockFace
import org.cloudburstmc.math.vector.Vector3i
import org.cloudburstmc.protocol.bedrock.packet.AnimatePacket
import org.cloudburstmc.protocol.bedrock.packet.ChunkRadiusUpdatedPacket
import org.cloudburstmc.protocol.bedrock.packet.EmoteListPacket
import org.cloudburstmc.protocol.bedrock.packet.InteractPacket
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket
import org.cloudburstmc.protocol.bedrock.packet.MovePlayerPacket
import org.cloudburstmc.protocol.bedrock.packet.RequestChunkRadiusPacket
import org.cloudburstmc.protocol.bedrock.packet.SetLocalPlayerAsInitializedPacket
import org.cloudburstmc.protocol.bedrock.packet.TickSyncPacket

class PlayerBundle: io.github.daylightnebula.meld.server.PacketBundle(
    io.github.daylightnebula.meld.server.bedrock(
        // handle chunk radius packets
        RequestChunkRadiusPacket::class.java.name to { connection, packet ->
            packet as RequestChunkRadiusPacket
            connection.sendPacket(ChunkRadiusUpdatedPacket().apply {
                radius = io.github.daylightnebula.meld.server.Meld.viewDistance
            })
        },

        // the packets we don't care about
        TickSyncPacket::class.java.name to { _, _ -> },
        SetLocalPlayerAsInitializedPacket::class.java.name to { _, _ -> },

        // emotes
        EmoteListPacket::class.java.name to { _, _ ->
            println("TODO what about emotes?")
        },

        MovePlayerPacket::class.java.name to { _, packet ->
            packet as MovePlayerPacket
//            println("Position: ${packet.position.x} ${packet.position.y} ${packet.position.z} ${packet.rotation.x} ${packet.rotation.y} ${packet.isOnGround}")
        },

        AnimatePacket::class.java.name to { _, _ ->
            println("TODO sync animations here")
        },

        LevelSoundEventPacket::class.java.name to { _, _ ->
            println("TODO sync sounds")
        },

        InteractPacket::class.java.name to { connection, packet ->
            packet as InteractPacket
            println("Received interaction for ${packet.action}")
        }
    ),
    io.github.daylightnebula.meld.server.java(
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

            // start event
            EventBus.callEvent(PlayerActionEvent(connection.player, packet.action, packet.entityID, packet.jumpBoost))

            // handle base functions
            when (packet.action) {
                PlayerCommandAction.START_SNEAKING -> connection.player.sneaking = true
                PlayerCommandAction.STOP_SNEAKING -> connection.player.sneaking = false
                PlayerCommandAction.START_SPRINTING -> connection.player.sprinting = true
                PlayerCommandAction.STOP_SPRINTING -> connection.player.sprinting = false
                else -> {}
            }
        },

        JavaBlockActionPacket::class.java.name to { connection, packet ->
            packet as JavaBlockActionPacket
            EventBus.callEvent(
                PlayerBlockActionEvent(
                    connection.player,
                    packet.action,
                    packet.blockPosition,
                    packet.face
                )
            )
//            connection.player.handleBlockAction(packet.action, packet.blockPosition, packet.face)
        },

        JavaSwingArmPacket::class.java.name to { connection, packet ->
            packet as JavaSwingArmPacket
            EventBus.callEvent(PlayerAnimationEvent(connection.player, EntityAnimation.SWING_ARM))
        },

        JavaEntityInteractPacket::class.java.name to { connection, packet ->
            packet as JavaEntityInteractPacket
            println("Interact packet $packet")
        }
    )
) {
    override fun registerJavaPackets(): HashMap<Pair<Int, JavaConnectionState>, () -> JavaPacket> =
        javaPackets(
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
            javaGamePacket(0x10) to { JavaEntityInteractPacket() }
        )
}

data class PlayerActionEvent(val player: Player, val action: PlayerCommandAction, val entityID: Int, val jumpBoost: Int): Event
data class PlayerBlockActionEvent(val player: Player, val action: PlayerBlockAction, val blockPosition: Vector3i, val face: BlockFace): Event
data class PlayerAnimationEvent(val player: Player, val animation: EntityAnimation): Event

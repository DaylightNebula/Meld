package io.github.daylightnebula.meld.player

import io.github.daylightnebula.meld.entities.EntityAnimation
import io.github.daylightnebula.meld.entities.metadata.metaPose
import io.github.daylightnebula.meld.player.extensions.player
import io.github.daylightnebula.meld.player.packets.*
import io.github.daylightnebula.meld.player.packets.join.JavaPluginMessagePacket
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.javaGamePacket
import io.github.daylightnebula.meld.server.javaPackets
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.networking.java.JavaPlayKeepAlivePacket
import io.github.daylightnebula.meld.server.utils.BlockFace
import io.github.daylightnebula.meld.server.utils.Pose
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i
import org.cloudburstmc.protocol.bedrock.packet.*

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
        JavaPlayKeepAlivePacket::class.java.name to { connection, packet -> },

        JavaReceivePlayerPositionPacket::class.java.name to { connection, packet ->
            packet as JavaReceivePlayerPositionPacket

            // get player and broadcast event
            val player = connection.player
            val event = PlayerMoveEvent(player, player.position, packet.position)
            EventBus.callEvent(event)

            // if cancelled, send sync packet, otherwise, set position
            if (event.cancelled) player.teleport()
            else player.setPosition(packet.position)
        },

        JavaReceivePlayerPositionAndRotationPacket::class.java.name to { connection, packet ->
            packet as JavaReceivePlayerPositionAndRotationPacket

            // get player and broadcast events
            val player = connection.player
            val moveEvent = PlayerMoveEvent(player, player.position, packet.position)
            val rotateEvent = PlayerRotateEvent(player, player.rotation, packet.rotation)
            EventBus.callEvent(moveEvent)
            EventBus.callEvent(rotateEvent)

            // get new position and rotation
            val position = if (moveEvent.cancelled) player.position else packet.position
            val rotation = if (rotateEvent.cancelled) player.rotation else packet.rotation

            // if either event is cancelled, call teleport
            if (moveEvent.cancelled || rotateEvent.cancelled) player.teleport(position, rotation)

            // call update position and rotation if their respective events are not cancelled
            if (!moveEvent.cancelled) player.setPosition(position)
            if (!rotateEvent.cancelled) player.setRotation(rotation)
        },

        JavaReceivePlayerRotationPacket::class.java.name to { connection, packet ->
            packet as JavaReceivePlayerRotationPacket

            // get player and broadcast event
            val player = connection.player
            val event = PlayerRotateEvent(player, player.rotation, packet.rotation)
            EventBus.callEvent(event)

            // if cancelled, send sync packet, otherwise, set rotation
            if (event.cancelled) player.teleport()
            else player.setRotation(packet.rotation)
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
            EventBus.callEvent(PlayerConfirmTeleportEvent(connection.player, packet.teleportID))
        },

        JavaReceivePlayerAbilitiesPacket::class.java.name to { connection, packet ->
            packet as JavaReceivePlayerAbilitiesPacket
            EventBus.callEvent(PlayerAbilitiesReceivedEvent(connection.player, packet.flags))
        },

        JavaPlayerCommandPacket::class.java.name to { connection, packet ->
            packet as JavaPlayerCommandPacket

            // start event
            EventBus.callEvent(PlayerActionEvent(connection.player, packet.action, packet.entityID, packet.jumpBoost))

            // handle base functions
            when (packet.action) {
                PlayerCommandAction.START_SNEAKING -> connection.player.replaceMetadataAtIndex(6, metaPose(6, Pose.SNEAKING))  //sneaking = true
                PlayerCommandAction.STOP_SNEAKING -> connection.player.replaceMetadataAtIndex(6, metaPose(6, Pose.STANDING))  //.sneaking = false
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
        },

        JavaSwingArmPacket::class.java.name to { connection, packet ->
            packet as JavaSwingArmPacket
            connection.player.playAnimation(EntityAnimation.SWING_ARM)
        },

        JavaEntityInteractPacket::class.java.name to { connection, packet ->
            packet as JavaEntityInteractPacket
            EventBus.callEvent(PlayerEntityInteractEvent(connection.player, packet.type, packet.entityID, packet.sneaking, packet.targetPosition))
        }
    )
) {
    override fun registerJavaPackets(): HashMap<Pair<Int, JavaConnectionState>, () -> JavaPacket> =
        javaPackets(
            javaGamePacket(0x15) to { JavaPlayKeepAlivePacket() },
            javaGamePacket(0x17) to { JavaReceivePlayerPositionPacket() },
            javaGamePacket(0x19) to { JavaReceivePlayerRotationPacket() },
            javaGamePacket(0x18) to { JavaReceivePlayerPositionAndRotationPacket() },
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
data class PlayerConfirmTeleportEvent(val player: Player, val teleportID: Int): Event
data class PlayerAbilitiesReceivedEvent(val player: Player, val abilities: Byte): Event
data class PlayerEntityInteractEvent(val player: Player, val type: PlayerInteractType, val entityID: Int, val sneaking: Boolean, val targetPosition: Vector3f?): Event
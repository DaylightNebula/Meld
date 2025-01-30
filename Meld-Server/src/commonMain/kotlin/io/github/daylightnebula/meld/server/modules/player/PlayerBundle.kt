package io.github.daylightnebula.meld.server.modules.player

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.PacketBundle
import io.github.daylightnebula.meld.server.entities.*
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.javaPacket
import io.github.daylightnebula.meld.server.javaPackets
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.generated.*
import io.github.daylightnebula.meld.server.networking.java.packets.JavaServerPlayUseEntity
import io.github.daylightnebula.meld.server.utils.BitFlagCodec
import io.github.daylightnebula.meld.server.utils.BlockFace
import io.github.daylightnebula.meld.server.utils.player
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class PlayerBundle: PacketBundle {
    override fun registerJavaPackets() =
        javaPackets(
            javaPacket(JavaServerPlayPosition, this::onReceivePlayerPosition),
            javaPacket(JavaServerPlayLook, this::onReceiveRotation),
            javaPacket(JavaServerPlayPositionLook, this::onReceivePlayerPositionAndRotation),
            javaPacket(JavaServerPlayCustomPayload, this::onPluginMessage),
            javaPacket(JavaServerPlayTeleportConfirm, this::onConfirmTeleport),
            javaPacket(JavaServerPlayAbilities, this::onReceivePlayerAbilities),
            javaPacket(JavaServerPlayEntityAction, this::onEntityAction),
            javaPacket(JavaServerPlayArmAnimation, this::onSwingArm),
            javaPacket(JavaServerPlayBlockDig, this::onBlockAction),
            javaPacket(JavaServerPlayUseEntity, this::onEntityInteraction),
            javaPacket(JavaServerPlayTickEnd, this::onClientTick),
            javaPacket(JavaServerPlayPlayerInput, this::onPlayerInput)
        )

    // When a player updates there primary inputs, record them to the player entity
    fun onPlayerInput(connection: JavaConnection, packet: JavaServerPlayPlayerInput) {
        val flags = mutableListOf<Player.PlayerInput>()
        packet.inputs.flags.forEachIndexed { idx, flag -> if (flag) flags.add(Player.PlayerInput.entries[idx]) }
        connection.player.lastInputFlags = flags
    }

    fun onReceivePlayerPosition(connection: JavaConnection, packet: JavaServerPlayPosition) {
        // get player and broadcast event
        val player = connection.player
        val position = Float3(packet.x.toFloat(), packet.y.toFloat(), packet.z.toFloat())
        val event = PlayerMoveEvent(player, player.position, position)
        EventBus.callEvent(event)

        // if cancelled, send sync packet, otherwise, set position
        if (event.cancelled) player.teleport()
        else player.setPosition(position)
    }

    @Suppress("unused")
    fun onClientTick(connection: JavaConnection, tick: JavaServerPlayTickEnd) {}

    fun onReceivePlayerPositionAndRotation(connection: JavaConnection, packet: JavaServerPlayPositionLook) {
        // get player and broadcast events
        val player = connection.player
        val packetPosition = Float3(packet.x.toFloat(), packet.y.toFloat(), packet.z.toFloat())
        val packetRotation = Float2(packet.pitch, packet.yaw)
        val moveEvent = PlayerMoveEvent(player, player.position, packetPosition)
        val rotateEvent = PlayerRotateEvent(player, player.rotation, packetRotation)
        EventBus.callEvent(moveEvent)
        EventBus.callEvent(rotateEvent)

        // get new position and rotation
        val position = if (moveEvent.cancelled) player.position else packetPosition
        val rotation = if (rotateEvent.cancelled) player.rotation else packetRotation

        // if either event is cancelled, call teleport
        if (moveEvent.cancelled || rotateEvent.cancelled) player.teleport(position, rotation)

        // call update position and rotation if their respective events are not cancelled
        if (!moveEvent.cancelled) player.setPosition(position)
        if (!rotateEvent.cancelled) player.setRotation(rotation)
    }

    fun onReceiveRotation(connection: JavaConnection, packet: JavaServerPlayLook) {
        // get player and broadcast event
        val player = connection.player
        val packetRotation = Float2(packet.pitch, packet.yaw)
        val event = PlayerRotateEvent(player, player.rotation, packetRotation)
        EventBus.callEvent(event)

        // if cancelled, send sync packet, otherwise, set rotation
        if (event.cancelled) player.teleport()
        else player.setRotation(packetRotation)
    }

    fun onPluginMessage(connection: JavaConnection, packet: JavaServerPlayCustomPayload) {
        when (packet.channel) {
            "minecraft:brand" -> {
                connection.sendPacket(JavaClientPlayCustomPayload("minecraft:brand", packet.data))
            }

            else -> println("Unknown plugin message channel ${packet.channel}")
        }
    }

    fun onConfirmTeleport(connection: JavaConnection, packet: JavaServerPlayTeleportConfirm) =
        EventBus.callEvent(PlayerConfirmTeleportEvent(connection.player, packet.teleportId))

    fun onReceivePlayerAbilities(connection: JavaConnection, packet: JavaServerPlayAbilities) =
        EventBus.callEvent(PlayerAbilitiesReceivedEvent(connection.player, packet.flags))

    fun onEntityAction(connection: JavaConnection, packet: JavaServerPlayEntityAction) {
        // start event
        val action = when(packet.actionId) {
            0 -> PlayerAction.START_SNEAKING
            1 -> PlayerAction.STOP_SNEAKING
            2 -> PlayerAction.LEAVE_BED
            3 -> PlayerAction.START_SPRINTING
            4 -> PlayerAction.STOP_SPRINTING
            5 -> PlayerAction.START_JUMP_HORSE
            6 -> PlayerAction.STOP_JUMP_HORSE
            7 -> PlayerAction.OPEN_VEHICLE_INVENTORY
            8 -> PlayerAction.START_FLYING_ELYTRA
            else -> throw IllegalStateException("Unknown player action ${packet.actionId}")
        }
        EventBus.callEvent(PlayerActionEvent(connection.player, action, packet.entityId, packet.jumpBoost))

        // handle base functions
        when (action) {
            PlayerAction.START_SNEAKING -> println("WARN Not updating sneak metadata") //connection.player.replaceMetadataAtIndex(6, metaPose(6, Pose.SNEAKING))  //sneaking = true
            PlayerAction.STOP_SNEAKING -> println("WARN Not updating sneak metadata") //connection.player.replaceMetadataAtIndex(6, metaPose(6, Pose.STANDING))  //.sneaking = false
            PlayerAction.START_SPRINTING -> connection.player.sprinting = true
            PlayerAction.STOP_SPRINTING -> connection.player.sprinting = false
            else -> {}
        }
    }

    fun onBlockAction(connection: JavaConnection, packet: JavaServerPlayBlockDig) =
        EventBus.callEvent(
            PlayerBlockActionEvent(
                connection.player,
                PlayerBlockAction.entries[packet.status],
                packet.location,
                BlockFace.entries[packet.face.toInt()]
            )
        )

    @Suppress("unused")
    fun onSwingArm(connection: JavaConnection, packet: JavaServerPlayArmAnimation) =
        connection.player.playAnimation(EntityAnimation.SWING_ARM)

    fun onEntityInteraction(connection: JavaConnection, packet: JavaServerPlayUseEntity) =
        EventBus.callEvent(PlayerEntityInteractEvent(
            player = connection.player,
            type = packet.type,
            entityID = packet.entityId,
            sneaking = packet.sneakPressed,
            targetPosition = packet.targetPosition,
            hand = packet.hand
        ))
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerActionEvent(val player: Player, val action: PlayerAction, val entityID: Int, val jumpBoost: Int): Event {
    companion object: Event.Data<PlayerActionEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerActionEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerBlockActionEvent(val player: Player, val action: PlayerBlockAction, val blockPosition: Float3, val face: BlockFace): Event {
    companion object: Event.Data<PlayerBlockActionEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerBlockActionEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerConfirmTeleportEvent(val player: Player, val teleportID: Int): Event {
    companion object: Event.Data<PlayerConfirmTeleportEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerConfirmTeleportEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerAbilitiesReceivedEvent(val player: Player, val abilities: Byte): Event {
    companion object: Event.Data<PlayerAbilitiesReceivedEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerAbilitiesReceivedEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerEntityInteractEvent(val player: Player, val type: PlayerInteractType, val entityID: Int, val sneaking: Boolean, val hand: Int?, val targetPosition: Float3?): Event {
    companion object: Event.Data<PlayerEntityInteractEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerEntityInteractEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

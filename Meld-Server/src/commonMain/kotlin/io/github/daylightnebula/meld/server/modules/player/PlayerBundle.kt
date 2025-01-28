package io.github.daylightnebula.meld.server.modules.player

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.PacketBundle
import io.github.daylightnebula.meld.server.entities.EntityAnimation
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.javaPacket
import io.github.daylightnebula.meld.server.javaPackets
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.entities.Player
import io.github.daylightnebula.meld.server.entities.PlayerBlockAction
import io.github.daylightnebula.meld.server.entities.PlayerCommandAction
import io.github.daylightnebula.meld.server.entities.PlayerInteractType
import io.github.daylightnebula.meld.server.entities.PlayerMoveEvent
import io.github.daylightnebula.meld.server.entities.PlayerRotateEvent
import io.github.daylightnebula.meld.server.utils.BlockFace
import io.github.daylightnebula.meld.server.utils.Pose
import io.github.daylightnebula.meld.server.utils.player
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class PlayerBundle: PacketBundle {
    override fun registerJavaPackets() =
        javaPackets(
            javaPacket(JavaPlayKeepAlivePacket, this::onGameKeepAlive),
            javaPacket(JavaReceivePlayerPositionPacket, this::onReceivePlayerPosition),
            javaPacket(JavaReceivePlayerRotationPacket, this::onReceiveRotation),
            javaPacket(JavaReceivePlayerPositionAndRotationPacket, this::onReceivePlayerPositionAndRotation),
            javaPacket(JavaPluginMessagePacket, this::onPluginMessage),
            javaPacket(JavaConfirmTeleportPacket, this::onConfirmTeleport),
            javaPacket(JavaReceivePlayerAbilitiesPacket, this::onReceivePlayerAbilities),
            javaPacket(JavaPlayerCommandPacket, this::onPlayerCommand),
            javaPacket(JavaSwingArmPacket, this::onSwingArm),
            javaPacket(JavaBlockActionPacket, this::onBlockAction),
            javaPacket(JavaEntityInteractPacket, this::onEntityInteraction),
            javaPacket(JavaPlayerClientTick, this::onClientTick)
        )

//    BEDROCK @PacketHandler
//    fun onBedrockRequestChunkRadius(connection: BedrockConnection, packet: RequestChunkRadiusPacket) {
//        connection.sendPacket(ChunkRadiusUpdatedPacket().apply {
//            radius = io.github.daylightnebula.meld.server.Meld.viewDistance
//        })
//    }
//
//    @PacketHandler
//    fun onBedrockEmoteListRequest(connection: BedrockConnection, packet: EmoteListPacket) = println("TODO what about emotes?")

    fun onReceivePlayerPosition(connection: JavaConnection, packet: JavaReceivePlayerPositionPacket) {
        // get player and broadcast event
        val player = connection.player
        val event = PlayerMoveEvent(player, player.position, packet.position)
        EventBus.callEvent(event)

        // if cancelled, send sync packet, otherwise, set position
        if (event.cancelled) player.teleport()
        else player.setPosition(packet.position)
    }

    fun onClientTick(connection: JavaConnection, tick: JavaPlayerClientTick) {}

    fun onReceivePlayerPositionAndRotation(connection: JavaConnection, packet: JavaReceivePlayerPositionAndRotationPacket) {
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
    }

    fun onReceiveRotation(connection: JavaConnection, packet: JavaReceivePlayerRotationPacket) {
        // get player and broadcast event
        val player = connection.player
        val event = PlayerRotateEvent(player, player.rotation, packet.rotation)
        EventBus.callEvent(event)

        // if cancelled, send sync packet, otherwise, set rotation
        if (event.cancelled) player.teleport()
        else player.setRotation(packet.rotation)
    }

    fun onPluginMessage(connection: JavaConnection, packet: JavaPluginMessagePacket) {
        when (packet.channel) {
            "minecraft:brand" -> {
                connection.sendPacket(JavaPluginMessagePacket("minecraft:brand", packet.data))
            }

            else -> println("Unknown plugin message channel ${packet.channel}")
        }
    }

    fun onConfirmTeleport(connection: JavaConnection, packet: JavaConfirmTeleportPacket) =
        EventBus.callEvent(PlayerConfirmTeleportEvent(connection.player, packet.teleportID))

    fun onReceivePlayerAbilities(connection: JavaConnection, packet: JavaReceivePlayerAbilitiesPacket) =
        EventBus.callEvent(PlayerAbilitiesReceivedEvent(connection.player, packet.flags))

    fun onPlayerCommand(connection: JavaConnection, packet: JavaPlayerCommandPacket) {
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
    }

    fun onBlockAction(connection: JavaConnection, packet: JavaBlockActionPacket) =
        EventBus.callEvent(
            PlayerBlockActionEvent(
                connection.player,
                packet.action,
                packet.blockPosition,
                packet.face
            )
        )

    fun onSwingArm(connection: JavaConnection, packet: JavaSwingArmPacket) =
        connection.player.playAnimation(EntityAnimation.SWING_ARM)

    fun onEntityInteraction(connection: JavaConnection, packet: JavaEntityInteractPacket) =
        EventBus.callEvent(PlayerEntityInteractEvent(connection.player, packet.type, packet.entityID, packet.sneaking, packet.targetPosition))

    fun onGameKeepAlive(connection: JavaConnection, packet: JavaPlayKeepAlivePacket) {}
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerActionEvent(val player: Player, val action: PlayerCommandAction, val entityID: Int, val jumpBoost: Int): Event {
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
data class PlayerEntityInteractEvent(val player: Player, val type: PlayerInteractType, val entityID: Int, val sneaking: Boolean, val targetPosition: Float3?): Event {
    companion object: Event.Data<PlayerEntityInteractEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerEntityInteractEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

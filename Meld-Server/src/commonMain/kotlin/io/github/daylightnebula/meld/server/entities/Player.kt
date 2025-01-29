package io.github.daylightnebula.meld.server.entities

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.events.CancellableEvent
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.generated.EntityType
import io.github.daylightnebula.meld.server.generated.JavaClientPlayEntityMetadata
import io.github.daylightnebula.meld.server.generated.JavaClientPlayPosition
import io.github.daylightnebula.meld.server.generated.JavaClientPlaySpawnEntity
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.networking.java.packets.JavaClientPlayPlayerInfo
import io.github.daylightnebula.meld.server.utils.BitFlagSet
import io.github.daylightnebula.meld.server.utils.Pose
import io.github.daylightnebula.meld.server.utils.TeleportCounter
import io.github.daylightnebula.meld.server.utils.toAngleByte
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class Player(
    val connection: IConnection<*>,
    uid: Uuid,
    id: Int = EntityController.nextID(),
    entityType: EntityType = EntityType.PLAYER,
    metadata: EntityMetadata = EntityMetadata(),
    dimensionID: String = "overworld",
    position: Float3 = Float3(),
    velocity: Float3 = Float3(),
    rotation: Float2 = Float2(),
    startHeadYaw: Float = 0f,
    health: Health = Health(20.0)
): LivingEntity(
    uid, id, entityType, metadata,
    dimensionID, position, velocity,
    rotation, startHeadYaw, health
) {
    // marks if the player has been sent their join packets
    var joinSent = false
        internal set

    // todo handle sneaking
//    fun setSneaking(sneaking: Boolean) {
//        if ((isSneaking() && !sneaking) || (!isSneaking() && sneaking))
//            replaceMetadataAtIndex(6, metaPose(6, if (sneaking) Pose.SNEAKING else Pose.STANDING))
//    }
//    fun isSneaking() = getMetadataAtIndex<Pose>(6)?.value == Pose.SNEAKING

    // TODO on set, broadcast packet
    var sprinting = false

    // TODO on set, broadcast packet
    enum class GameMode { ADVENTURE, CREATIVE, SPECTATOR, SURVIVAL }
    var gameMode: GameMode = GameMode.CREATIVE
        private set

    // do not broadcast changes to self
    override var watcherFilter: (connection: IConnection<*>) -> Boolean = { other -> other != connection }

    // teleports the player to the given position and rotation
    fun teleport(position: Float3 = this.position, rotation: Float2 = this.rotation) {
        setPosition(position)
        setRotation(rotation)
        when(connection) {
//            is JavaConnection -> connection.sendPacket(JavaSetPlayerPositionPacket(position, Float3(), rotation))
            is JavaConnection -> connection.sendPacket(JavaClientPlayPosition(
                TeleportCounter.nextID(),
                position.x.toDouble(), position.y.toDouble(), position.z.toDouble(),
                0.0, 0.0, 0.0,
                rotation.y, rotation.x
            ))
        }
    }

    // use player spawn packets only if type is set too player
    override fun getSpawnJavaPackets(): List<JavaPacket> =
        if (type == EntityType.PLAYER)
            listOf(
                JavaClientPlayPlayerInfo(
                    action = BitFlagSet(flags = BooleanArray(8).apply { set(0, true) }),
                    data = listOf(JavaClientPlayPlayerInfo.Data(uid))
                ),
                JavaClientPlaySpawnEntity(
                    entityId = id,
                    objectUUID = uid,
                    type = EntityType.PLAYER.id,
                    x = position.x.toDouble(),
                    y = position.y.toDouble(),
                    z = position.z.toDouble(),
                    pitch = rotation.y.toAngleByte(),
                    yaw = rotation.x.toAngleByte(),
                    headPitch = rotation.y.toAngleByte(),
                    objectData = 0,
                    velocityX = 0,
                    velocityY = 0,
                    velocityZ = 0
                ),
                JavaClientPlayEntityMetadata(id, metadata)
            )
        else super.getSpawnJavaPackets()
}

// events
@OptIn(ExperimentalUuidApi::class)
data class PlayerMoveEvent(val player: Player, val oldPosition: Float3, val position: Float3, override var cancelled: Boolean = false): CancellableEvent {
    companion object: Event.Data<PlayerMoveEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerMoveEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerRotateEvent(val player: Player, val oldRotation: Float2, val rotation: Float2, override var cancelled: Boolean = false): CancellableEvent {
    companion object: Event.Data<PlayerRotateEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerRotateEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

// enums
enum class PlayerChatMode { ENABLED, COMMANDS_ONLY, HIDDEN }
enum class PlayerMainHand { LEFT, RIGHT }
enum class PlayerHand { MAIN, OFF }
enum class PlayerCommandAction { START_SNEAKING, STOP_SNEAKING, LEAVE_BED, START_SPRINTING, STOP_SPRINTING, START_JUMP_HORSE, STOP_JUMP_HORSE, OPEN_HORSE_INVENTORY, START_FLYING_ELYTRA }
enum class PlayerBlockAction { START_DIGGING, CANCELLED_DIGGING, FINISHED_DIGGING, DROP_ITEM_STACK, DROP_ITEM, SHOOT_ARROW_FINISH_EATING, SWAP_ITEM_IN_HAND }
enum class PlayerInteractType { INTERACT, ATTACK, INTERACT_AT }
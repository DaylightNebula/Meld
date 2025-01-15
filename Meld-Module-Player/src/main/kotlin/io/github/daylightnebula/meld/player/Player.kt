package io.github.daylightnebula.meld.player

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.entities.EntityController
import io.github.daylightnebula.meld.entities.EntityType
import io.github.daylightnebula.meld.entities.Health
import io.github.daylightnebula.meld.entities.LivingEntity
import io.github.daylightnebula.meld.entities.metadata.EntityMetadata
import io.github.daylightnebula.meld.entities.metadata.entityMetadata
import io.github.daylightnebula.meld.entities.metadata.metaPose
import io.github.daylightnebula.meld.entities.packets.JavaEntityMetadataPacket
import io.github.daylightnebula.meld.entities.packets.JavaSpawnEntityPacket
import io.github.daylightnebula.meld.player.packets.JavaPlayerInfoUpdatePacket
import io.github.daylightnebula.meld.player.packets.JavaSetPlayerPositionPacket
import io.github.daylightnebula.meld.player.packets.PlayerInfoAction
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.events.CancellableEvent
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.utils.Pose
import java.util.*

class Player(
    val connection: IConnection<*>,
    uid: UUID,
    id: Int = EntityController.nextID(),
    entityType: EntityType = EntityType.PLAYER,
    metadata: EntityMetadata = entityMetadata(),
    dimensionID: String = "overworld",
    position: Float3 = Float3(),
    velocity: Float3 = Float3(),
    rotation: Float2 = Float2(),
    startHeadYaw: Float = 0f,
    health: Health = Health(20.0),
    val infoActions: MutableList<PlayerInfoAction> = mutableListOf(PlayerInfoAction.AddPlayer("player"))
): LivingEntity(
    uid, id, entityType, metadata, dimensionID, position, velocity, rotation, startHeadYaw, health
) {
    // marks if the player has been sent their join packets
    var joinSent = false
        internal set

    // handle sneaking
    fun setSneaking(sneaking: Boolean) {
        if ((isSneaking() && !sneaking) || (!isSneaking() && sneaking))
            replaceMetadataAtIndex(6, metaPose(6, if (sneaking) Pose.SNEAKING else Pose.STANDING))
    }
    fun isSneaking() = getMetadataAtIndex<Pose>(6)?.value == Pose.SNEAKING

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
            is JavaConnection -> connection.sendPacket(JavaSetPlayerPositionPacket(position, Float3(), rotation))
        }
    }

    // use player spawn packets only if type is set too player
    override fun getSpawnJavaPackets(): List<JavaPacket> =
        if (type == EntityType.PLAYER)
            listOf(
                JavaPlayerInfoUpdatePacket(uid, infoActions),
                JavaSpawnEntityPacket(
                    id, uid, EntityType.PLAYER.mcID,
                    position = position, rotation = rotation
                ),
                JavaEntityMetadataPacket(id, metadata)
            )
        else super.getSpawnJavaPackets()
}

// events
data class PlayerMoveEvent(val player: Player, val oldPosition: Float3, val position: Float3, override var cancelled: Boolean = false): CancellableEvent
data class PlayerRotateEvent(val player: Player, val oldRotation: Float2, val rotation: Float2, override var cancelled: Boolean = false): CancellableEvent

// enums
enum class PlayerChatMode { ENABLED, COMMANDS_ONLY, HIDDEN }
enum class PlayerMainHand { LEFT, RIGHT }
enum class PlayerHand { MAIN, OFF }
enum class PlayerCommandAction { START_SNEAKING, STOP_SNEAKING, LEAVE_BED, START_SPRINTING, STOP_SPRINTING, START_JUMP_HORSE, STOP_JUMP_HORSE, OPEN_HORSE_INVENTORY, START_FLYING_ELYTRA }
enum class PlayerBlockAction { START_DIGGING, CANCELLED_DIGGING, FINISHED_DIGGING, DROP_ITEM_STACK, DROP_ITEM, SHOOT_ARROW_FINISH_EATING, SWAP_ITEM_IN_HAND }
enum class PlayerInteractType { INTERACT, ATTACK, INTERACT_AT }
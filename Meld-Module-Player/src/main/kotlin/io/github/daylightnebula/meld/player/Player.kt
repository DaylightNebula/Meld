package io.github.daylightnebula.meld.player

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.JavaKeepAlivePacket
import io.github.daylightnebula.meld.entities.EntityController
import io.github.daylightnebula.meld.entities.EntityType
import io.github.daylightnebula.meld.entities.Health
import io.github.daylightnebula.meld.entities.LivingEntity
import io.github.daylightnebula.meld.player.packets.JavaSetPlayerPositionPacket
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.events.CancellableEvent
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.data.GameType
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.thread

// TODO declare recipes packet + crafting data packet
// TODO tags packet (may need more integration)
// TODO update view distance packets
// TODO simulation distance packets
class Player(
    val connection: IConnection<*>,
    uid: UUID,
    id: Int = EntityController.nextID(),
    dimensionID: String = "overworld",
    position: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    velocity: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    rotation: Vector2f = Vector2f.from(0.0, 0.0),
    startHeadYaw: Float = 0f,
    health: Health = Health(20.0)
): LivingEntity(
    uid, id, EntityType.PLAYER, dimensionID, position, velocity, rotation, startHeadYaw, health
) {
    // marks if the player has been sent their join packets
    var joinSent = false
        internal set

    // TODO on set, broadcast packet
    var sneaking = false
    var sprinting = false

    // TODO on set, broadcast packet
    var gameMode: GameMode = GameMode.CREATIVE
        private set

    // do not broadcast changes to self
    override fun broadcastPositionUpdatesTo() = Meld.connections.filter { it != connection }

    // teleports the player to the given position and rotation
    fun teleport(position: Vector3f = this.position, rotation: Vector2f = this.rotation) {
        setPosition(position)
        setRotation(rotation)
        when(connection) {
            is JavaConnection -> connection.sendPacket(JavaSetPlayerPositionPacket(position, rotation))
            is BedrockConnection -> NeedsBedrock()
        }
    }
}

// events
data class PlayerMoveEvent(val player: Player, val oldPosition: Vector3f, val position: Vector3f, override var cancelled: Boolean = false): CancellableEvent
data class PlayerRotateEvent(val player: Player, val oldRotation: Vector2f, val rotation: Vector2f, override var cancelled: Boolean = false): CancellableEvent

// extensions
fun GameMode.bedrockGameMode() = when(this) {
    GameMode.SURVIVAL -> GameType.SURVIVAL
    GameMode.CREATIVE -> GameType.CREATIVE
    GameMode.ADVENTURE -> GameType.ADVENTURE
    GameMode.SPECTATOR -> GameType.SPECTATOR
}

// enums
enum class PlayerChatMode { ENABLED, COMMANDS_ONLY, HIDDEN }
enum class PlayerMainHand { LEFT, RIGHT }
enum class PlayerHand { MAIN, OFF }
enum class PlayerCommandAction { START_SNEAKING, STOP_SNEAKING, LEAVE_BED, START_SPRINTING, STOP_SPRINTING, START_JUMP_HORSE, STOP_JUMP_HORSE, OPEN_HORSE_INVENTORY, START_FLYING_ELYTRA }
enum class PlayerBlockAction { START_DIGGING, CANCELLED_DIGGING, FINISHED_DIGGING, DROP_ITEM_STACK, DROP_ITEM, SHOOT_ARROW_FINISH_EATING, SWAP_ITEM_IN_HAND }
enum class PlayerInteractType { INTERACT, ATTACK, INTERACT_AT }
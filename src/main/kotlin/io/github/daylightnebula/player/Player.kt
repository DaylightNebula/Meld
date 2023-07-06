package io.github.daylightnebula.player

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import io.github.daylightnebula.entities.EntityController
import io.github.daylightnebula.entities.Health
import io.github.daylightnebula.entities.LivingEntity
import io.github.daylightnebula.networking.common.IConnection
import io.github.daylightnebula.networking.java.JavaConnection
import io.github.daylightnebula.player.packets.JavaKeepAlivePacket
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.data.GameType
import java.lang.Thread.sleep
import kotlin.concurrent.thread

// TODO declare recipes packet + crafting data packet
// TODO tags packet (may need more integration)
// TODO update view distance packets
// TODO simulation distance packets
class Player(
    val connection: IConnection<*>,
    id: Int = EntityController.nextID(),
    position: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    health: Health = Health(20.0)
): LivingEntity(
    id, position, health
) {
    // TODO replace
    val keepAliveThread = thread {
        while (true) {
            sleep(1000)
            if (connection is JavaConnection) connection.sendPacket(JavaKeepAlivePacket())
        }
    }

    // marks if the player has been sent their join packets
    var joinSent = false
        internal set

    // TODO on set, broadcast packet
    var gameMode: GameMode = GameMode.CREATIVE
        private set

    // TODO teleport functions
}

fun GameMode.bedrockGameMode() = when(this) {
    GameMode.SURVIVAL -> GameType.SURVIVAL
    GameMode.CREATIVE -> GameType.CREATIVE
    GameMode.ADVENTURE -> GameType.ADVENTURE
    GameMode.SPECTATOR -> GameType.SPECTATOR
}

enum class PlayerChatMode { ENABLED, COMMANDS_ONLY, HIDDEN }
enum class PlayerMainHand { LEFT, RIGHT }
enum class PlayerHand { MAIN, OFF }
enum class PlayerCommandAction { START_SNEAKING, STOP_SNEAKING, LEAVE_BED, START_SPRINTING, STOP_SPRINTING, START_JUMP_HORSE, STOP_JUMP_HORSE, OPEN_HORSE_INVENTORY, START_FLYING_ELYTRA }
enum class PlayerAction { START_DIGGING, CANCELLED_DIGGING, FINISHED_DIGGING, DROP_ITEM_STACK, DROP_ITEM, SHOOT_ARROW_FINISH_EATING, SWAP_ITEM_IN_HAND }
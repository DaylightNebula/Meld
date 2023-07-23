package io.github.daylightnebula.meld.player.packets

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import java.util.*

// https://wiki.vg/Protocol#Player_Info_Update
class JavaPlayerInfoUpdatePacket(
    var uuid: UUID = UUID.randomUUID(),
    var actions: Collection<PlayerInfoAction> = listOf()
): JavaPacket {
    override val id: Int = 0x3A
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        // create and write bit set
        val set = BitSet()
        actions.forEach { set.set(it.bitIndex, true) }
        writer.writeByteArray(set.toByteArray())

        // write action count and uuid
        writer.writeVarInt(actions.size)
        writer.writeLong(uuid.mostSignificantBits)
        writer.writeLong(uuid.leastSignificantBits)

        // write actions
        for (it in actions) { it.writeJava(writer) }
    }
}

// interface for player info actions
interface PlayerInfoAction {
    // add player action (defines player name, and properties for skin and capes and such)
    class AddPlayer(var playerName: String = ""): PlayerInfoAction {
        override val bitIndex: Int = 0
        override fun writeJava(writer: ByteWriter) {
            writer.writeString(playerName)
            writer.writeVarInt(0)
        }
    }

    // initialize chat for players
    class InitChat: PlayerInfoAction {
        override val bitIndex: Int = 1
        override fun writeJava(writer: ByteWriter) {
            writer.writeBoolean(false)
        }
    }

    // update game mode for players
    class UpdateGameMode(var gameMode: GameMode = GameMode.ADVENTURE): PlayerInfoAction {
        override val bitIndex: Int = 2
        override fun writeJava(writer: ByteWriter) {
            writer.writeVarInt(gameMode.ordinal)
        }
    }

    // update whether a player is listed in the tab list
    class UpdateListed(var listed: Boolean = false): PlayerInfoAction {
        override val bitIndex: Int = 3
        override fun writeJava(writer: ByteWriter) {
            writer.writeBoolean(listed)
        }
    }

    // update the latency of a player showed in the tab list
    class UpdateLatency(var latency: Int = 0): PlayerInfoAction {
        override val bitIndex: Int = 4
        override fun writeJava(writer: ByteWriter) {
            writer.writeVarInt(latency)
        }
    }

    // update display name of players
    class UpdateDisplayName(var displayName: String? = null): PlayerInfoAction {
        override val bitIndex: Int = 5
        override fun writeJava(writer: ByteWriter) {
            writer.writeBoolean(displayName != null)
            if (displayName != null) writer.writeString(displayName!!)
        }
    }

    val bitIndex: Int
    fun writeJava(writer: ByteWriter)
}
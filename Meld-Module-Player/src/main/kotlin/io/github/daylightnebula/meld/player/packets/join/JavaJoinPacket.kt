package io.github.daylightnebula.meld.player.packets.join

import io.github.daylightnebula.meld.entities.EntityController
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.server.Meld

// https://wiki.vg/Protocol#Login_.28play.29
@Suppress("MemberVisibilityCanBePrivate")
class JavaJoinPacket(
    val playerID: Int = EntityController.nextID(),
    val isHardcore: Boolean = false,
    val gameMode: Player.GameMode = Player.GameMode.SURVIVAL,
    val previousGameMode: Byte = -1,
    val dimensionCount: Int = 1,
    val dimensionNames: List<String> = listOf("minecraft:overworld"),
    val dimensionType: String = "minecraft:overworld",
    val dimensionName: String = "minecraft:overworld",
    val seed: Long = 0L,
    val maxPlayers: Int = Meld.maxPlayers,
    val viewDistance: Int = Meld.viewDistance,
    val simDistance: Int = Meld.simDistance,
    val reducedDebugInfo: Boolean = false,
    val enableRespawnScreen: Boolean = true,
    val doLimitedCrafting: Boolean = false,
    val isDebug: Boolean = false,
    val isFlat: Boolean = Meld.isFlatWorld,
    val portalCooldown: Int = Meld.portalCooldown
): JavaPacket {

    constructor(player: Player): this(
        playerID = player.id,
        isHardcore = false,
        gameMode = player.gameMode
    )

    companion object { val ID = 0x2C }
    override val id: Int = ID
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        // header
        writer.writeInt(playerID)
        writer.writeBoolean(isHardcore)
        writer.writeVarInt(0)
        writer.writeVarInt(dimensionCount)
        dimensionNames.forEach { writer.writeString(it) }
        writer.writeVarInt(maxPlayers)
        writer.writeVarInt(viewDistance)
        writer.writeVarInt(simDistance)
        writer.writeBoolean(reducedDebugInfo)
        writer.writeBoolean(enableRespawnScreen)
        writer.writeBoolean(doLimitedCrafting)
        writer.writeVarInt(0) // dimension type
        writer.writeString(dimensionName)
        writer.writeLong(0) // hashed seed
        writer.writeUByte(gameMode.ordinal.toUByte())   // todo get ID from game mode
        writer.writeByte(previousGameMode)
        writer.writeBoolean(isDebug)
        writer.writeBoolean(isFlat)
        writer.writeBoolean(false)
        writer.writeVarInt(portalCooldown)
        writer.writeVarInt(40)
        writer.writeBoolean(Meld.enforceSecureChat)
    }
}


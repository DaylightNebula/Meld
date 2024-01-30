package io.github.daylightnebula.meld.player.packets.join

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.registries.RegistryCodec
import io.github.daylightnebula.meld.entities.EntityController
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.player.Player
import org.jglrxavpok.hephaistos.nbt.NBTCompound


// https://wiki.vg/Protocol#Login_.28play.29
class JavaJoinPacket(
    val playerID: Int = EntityController.nextID(),
    val isHardcore: Boolean = false,
    val gameMode: GameMode = GameMode.SURVIVAL,
    val previousGameMode: Byte = -1,
    val dimensionCount: Int = 1,
    val dimensionNames: List<String> = listOf("minecraft:overworld"),
    val registryCodec: NBTCompound = RegistryCodec.nbt,
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

    companion object { val ID = 0x29 }
    override val id: Int = ID
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        // header
        writer.writeInt(playerID)
        writer.writeBoolean(isHardcore)
        writer.writeVarInt(dimensionCount)
        dimensionNames.forEach { writer.writeString(it) }
        writer.writeVarInt(maxPlayers)
        writer.writeVarInt(viewDistance)
        writer.writeVarInt(simDistance)
        writer.writeBoolean(reducedDebugInfo)
        writer.writeBoolean(enableRespawnScreen)
        writer.writeBoolean(doLimitedCrafting)
        writer.writeString(dimensionType)
        writer.writeString(dimensionName)
        writer.writeLong(0) // hashed seed
        writer.writeUByte(gameMode.ordinal.toUByte())
        writer.writeByte(previousGameMode)
        writer.writeBoolean(isDebug)
        writer.writeBoolean(isFlat)
        writer.writeBoolean(false)
        writer.writeVarInt(portalCooldown)
    }
}


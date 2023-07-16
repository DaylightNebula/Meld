package io.github.daylightnebula.meld.core.player.packets.join

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.registries.RegistryCodec
import io.github.daylightnebula.meld.core.entities.EntityController
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.core.player.Player
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
    val maxPlayers: Int = io.github.daylightnebula.meld.server.Meld.maxPlayers,
    val viewDistance: Int = io.github.daylightnebula.meld.server.Meld.viewDistance,
    val simDistance: Int = io.github.daylightnebula.meld.server.Meld.simDistance,
    val reducedDebugInfo: Boolean = false,
    val enableRespawnScreen: Boolean = true,
    val isDebug: Boolean = false,
    val isFlat: Boolean = io.github.daylightnebula.meld.server.Meld.isFlatWorld,
    val portalCooldown: Int = io.github.daylightnebula.meld.server.Meld.portalCooldown
): JavaPacket {

    constructor(player: Player): this(
        playerID = player.id,
        isHardcore = false,
        gameMode = player.gameMode
    )

    companion object { val ID = 0x28 }
    override val id: Int = ID
    override fun decode(reader: AbstractReader) = io.github.daylightnebula.meld.server.noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeInt(playerID)
        writer.writeBoolean(isHardcore)
        writer.writeUByte(gameMode.ordinal.toUByte())
        writer.writeByte(previousGameMode)
        writer.writeVarInt(dimensionCount)
        dimensionNames.forEach { writer.writeString(it) }
        writer.writeNBT(registryCodec)
        writer.writeString(dimensionType)
        writer.writeString(dimensionName)
        writer.writeLong(seed)
        writer.writeVarInt(maxPlayers)
        writer.writeVarInt(viewDistance)
        writer.writeVarInt(simDistance)
        writer.writeBoolean(reducedDebugInfo)
        writer.writeBoolean(enableRespawnScreen)
        writer.writeBoolean(isDebug)
        writer.writeBoolean(isFlat)
        writer.writeBoolean(false)
        writer.writeVarInt(portalCooldown)
    }
}


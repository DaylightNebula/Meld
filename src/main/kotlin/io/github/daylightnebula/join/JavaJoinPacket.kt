package io.github.daylightnebula.join

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import io.github.daylightnebula.Meld
import io.github.daylightnebula.entities.EntityController
import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noDecode
import org.jglrxavpok.hephaistos.nbt.NBTCompound


// https://wiki.vg/Protocol#Login_.28play.29
class JavaJoinPacket(
    val playerID: Int = EntityController.nextID(),
    val isHardcore: Boolean = false,
    val gameMode: UByte = GameMode.SURVIVAL.ordinal.toUByte(),
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
    val enableRespawnScreen: Boolean = false,
    val isDebug: Boolean = false,
    val isFlat: Boolean = Meld.isFlatWorld,
    val portalCooldown: Int = Meld.portalCooldown
): JavaPacket {
    companion object {
        val ID = 0x28
    }

    override val id: Int = ID
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeInt(playerID)
        writer.writeBoolean(isHardcore)
        writer.writeUByte(gameMode)
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


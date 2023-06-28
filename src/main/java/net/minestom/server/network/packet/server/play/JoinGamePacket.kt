package net.minestom.server.network.packet.server.play

import io.github.daylightnebula.math.DeathLocation
import io.github.daylightnebula.math.GameMode
import net.minestom.server.network.NetworkBuffer
import net.minestom.server.network.packet.server.JavaServerPacket
import net.minestom.server.network.packet.server.ServerPacketIdentifier
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound


class JoinGamePacket(
    entityId: Int,
    isHardcore: Boolean,
    gameMode: GameMode,
    previousGameMode: GameMode?,
    worlds: List<String?>?,
    dimensionCodec: NBTCompound,
    dimensionType: String,
    world: String,
    hashedSeed: Long,
    maxPlayers: Int,
    viewDistance: Int,
    simulationDistance: Int,
    reducedDebugInfo: Boolean,
    enableRespawnScreen: Boolean,
    isDebug: Boolean,
    isFlat: Boolean,
    deathLocation: DeathLocation
) : JavaServerPacket {
    constructor(reader: NetworkBuffer) : this(
        reader.read<Int>(NetworkBuffer.INT),
        reader.read<Boolean>(NetworkBuffer.BOOLEAN),
        GameMode.fromId(reader.read(NetworkBuffer.BYTE).toInt()),
        getNullableGameMode(reader.read<Byte>(NetworkBuffer.BYTE)),
        reader.readCollection<String?>(NetworkBuffer.STRING),
        reader.read<NBT>(NetworkBuffer.NBT) as NBTCompound,
        reader.read<String>(NetworkBuffer.STRING),
        reader.read<String>(NetworkBuffer.STRING),
        reader.read<Long>(NetworkBuffer.LONG),
        reader.read<Int>(NetworkBuffer.VAR_INT),
        reader.read<Int>(NetworkBuffer.VAR_INT),
        reader.read<Int>(NetworkBuffer.VAR_INT),
        reader.read<Boolean>(NetworkBuffer.BOOLEAN),
        reader.read<Boolean>(NetworkBuffer.BOOLEAN),
        reader.read<Boolean>(NetworkBuffer.BOOLEAN),
        reader.read<Boolean>(NetworkBuffer.BOOLEAN),
        reader.read(NetworkBuffer.DEATH_LOCATION)
    )

    override fun write(writer: NetworkBuffer) {
        writer.write(NetworkBuffer.INT, entityId)
        writer.write(NetworkBuffer.BOOLEAN, isHardcore)
        writer.write(NetworkBuffer.BYTE, gameMode.id())
        if (previousGameMode != null) {
            writer.write(NetworkBuffer.BYTE, previousGameMode.id())
        } else {
            writer.write(NetworkBuffer.BYTE, (-1.toByte()).toByte())
        }
        writer.writeCollection(NetworkBuffer.STRING, worlds?.filterNotNull())
        writer.write(NetworkBuffer.NBT, dimensionCodec as NBTCompound)
        writer.write(NetworkBuffer.STRING, dimensionType)
        writer.write(NetworkBuffer.STRING, world)
        writer.write(NetworkBuffer.LONG, hashedSeed)
        writer.write(NetworkBuffer.VAR_INT, maxPlayers)
        writer.write(NetworkBuffer.VAR_INT, viewDistance)
        writer.write(NetworkBuffer.VAR_INT, simulationDistance)
        writer.write(NetworkBuffer.BOOLEAN, reducedDebugInfo)
        writer.write(NetworkBuffer.BOOLEAN, enableRespawnScreen)
        //debug
        writer.write(NetworkBuffer.BOOLEAN, isDebug)
        //is flat
        writer.write(NetworkBuffer.BOOLEAN, isFlat)
        writer.write(NetworkBuffer.DEATH_LOCATION, deathLocation)
    }

    override fun getId(): Int {
        return ServerPacketIdentifier.JOIN_GAME
    }

    val id: Int
        get() = ServerPacketIdentifier.JOIN_GAME
    val entityId: Int
    val isHardcore: Boolean
    val gameMode: GameMode
    val previousGameMode: GameMode?
    val worlds: List<String?>?
    val dimensionCodec: NBTCompound?
    val dimensionType: String
    val world: String
    val hashedSeed: Long
    val maxPlayers: Int
    val viewDistance: Int
    val simulationDistance: Int
    val reducedDebugInfo: Boolean
    val enableRespawnScreen: Boolean
    val isDebug: Boolean
    val isFlat: Boolean
    val deathLocation: DeathLocation

    init {
        this.deathLocation = deathLocation
        this.isFlat = isFlat
        this.isDebug = isDebug
        this.enableRespawnScreen = enableRespawnScreen
        this.reducedDebugInfo = reducedDebugInfo
        this.simulationDistance = simulationDistance
        this.viewDistance = viewDistance
        this.maxPlayers = maxPlayers
        this.hashedSeed = hashedSeed
        this.world = world
        this.dimensionType = dimensionType
        this.dimensionCodec = dimensionCodec
        this.previousGameMode = previousGameMode
        this.gameMode = gameMode
        this.isHardcore = isHardcore
        this.entityId = entityId
        var worlds = worlds
        worlds = java.util.List.copyOf(worlds)
        this.worlds = worlds
    }

    companion object {
        /**
         * This method exists in lieu of a NetworkBufferType since -1 is only a
         * valid value in this packet and changing behaviour of GameMode.fromId()
         * to be nullable would be too big of a change. Also, game modes are often
         * represented as other data types, including floats.
         */
        private fun getNullableGameMode(id: Byte): GameMode? {
            return if (id.toInt() == -1) null else GameMode.fromId(id.toInt())
        }
    }
}


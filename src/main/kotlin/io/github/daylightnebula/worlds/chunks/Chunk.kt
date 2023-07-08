package io.github.daylightnebula.worlds.chunks

import io.github.daylightnebula.events.Event
import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaConnection
import io.github.daylightnebula.player.Player
import io.github.daylightnebula.worlds.chunks.packets.JavaChunkPacket
import org.cloudburstmc.math.vector.Vector3i
import kotlin.math.floor

// TODO fill blocks function
// TODO clear blocks function
// TODO broadcast changes
data class Chunk(
    var chunkX: Int = 0,
    var chunkY: Int = 0,
    var sections: Array<Section> = Array(24) { Section() }
) {
    fun writeJava(writer: ByteWriter) {
        // synchronously loop over the sections
        for (section in sections)
            section.writeJava(writer)
    }

    fun writeBedrock(writer: ByteWriter) {
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Chunk

        if (chunkX != other.chunkX) return false
        if (chunkY != other.chunkY) return false
        return sections.contentEquals(other.sections)
    }
    override fun hashCode(): Int {
        var result = chunkX
        result = 31 * result + chunkY
        result = 31 * result + sections.contentHashCode()
        return result
    }

    fun setBlock(player: Player, position: Vector3i, blockID: Int) {
        // get chunk location
        val chunkLocation = Vector3i.from(
            floor((position.x % 16).inc16IfNegative().toDouble()).toInt(),
            floor((position.y % 16).inc16IfNegative().toDouble()).toInt(),
            floor((position.z % 16).inc16IfNegative().toDouble()).toInt(),
        )

        // call setting block event
        val settingEvent = SettingBlockEvent(this, position, chunkLocation)
        EventBus.callEvent(settingEvent)

        // if the setting event was cancelled, stop here
        if (settingEvent.isCancelled) return

        // get section
        val section = sections[position.y.toSectionID()]

        // update palette
        val palette = section.blockPalette
        palette.set(chunkLocation, blockID)

        // send update packets
        when (player.connection) {
            is JavaConnection -> player.connection.sendPacket(JavaChunkPacket(this))
            else -> TODO()
        }

        // call set event
        EventBus.callEvent(SetBlockEvent(this, position, chunkLocation))
    }

    fun getBlock(position: Vector3i): Int {
        // get chunk location
        val chunkLocation = Vector3i.from(
            floor((position.x % 16).inc16IfNegative().toDouble()).toInt(),
            floor((position.y % 16).inc16IfNegative().toDouble()).toInt(),
            floor((position.z % 16).inc16IfNegative().toDouble()).toInt(),
        )

        // get section
        val section = sections[position.y.toSectionID()]

        // get block and return
        return section.blockPalette.get(chunkLocation)
    }
}

data class SettingBlockEvent(val chunk: Chunk, val location: Vector3i, val chunkLocation: Vector3i, var isCancelled: Boolean = false): Event
data class SetBlockEvent(val chunk: Chunk, val location: Vector3i, val chunkLocation: Vector3i): Event
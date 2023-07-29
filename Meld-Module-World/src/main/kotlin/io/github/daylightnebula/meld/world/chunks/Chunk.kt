package io.github.daylightnebula.meld.world.chunks

import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.entities.currentTick
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.extensions.inc16IfNegative
import io.github.daylightnebula.meld.server.extensions.toChunkPosition
import io.github.daylightnebula.meld.server.extensions.toSectionID
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.world.Dimension
import io.github.daylightnebula.meld.world.World
import io.github.daylightnebula.meld.world.WorldModule
import io.github.daylightnebula.meld.world.packets.JavaChunkPacket
import org.cloudburstmc.math.vector.Vector2i
import org.cloudburstmc.math.vector.Vector3i
import kotlin.math.floor
import kotlin.text.Typography.section

data class Chunk(
    var dimensionRef: String = "",
    var position: Vector2i = Vector2i.from(0, 0),
    var sections: Array<Section> = Array(24) { Section() },
    var entities: MutableList<Entity> = mutableListOf()
) {
    fun writeJava(writer: ByteWriter) {
        // synchronously loop over the sections
        for (section in sections)
            section.writeJava(writer)
    }

    fun writeBedrock(writer: ByteWriter) {}

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Chunk

        if (position.x != other.position.x) return false
        if (position.y != other.position.y) return false
        return sections.contentEquals(other.sections)
    }
    override fun hashCode(): Int {
        var result = position.x
        result = 31 * result + position.y
        result = 31 * result + sections.contentHashCode()
        return result
    }

    private fun broadcastChanges() {
        if (!WorldModule.module.broadcastEnabled) return

        // get all players in view distance
        val dimension = World.dimensions[dimensionRef] ?: return
        val players = dimension.getChunksInViewDistanceOfChunk(position)
            .flatMap { entities.filterIsInstance<Player>() }

        // send changes
        val javaPacket = JavaChunkPacket(this)
        players.forEach {
            when (val connection = it.connection) {
                is JavaConnection -> connection.sendPacket(javaPacket)
                is BedrockConnection -> NeedsBedrock()
            }
        }
    }

    fun fill(from: Vector3i, to: Vector3i, blockID: Int) {
        // loop through all sections
        (from.y.toSectionID() .. to.y.toSectionID()).forEach { section ->
            // get lowest and highest y
            val lowY = if (from.y.toSectionID() == section) from.y.toSectionPosition() else 0
            val highY = if (to.y.toSectionID() == section) to.y.toSectionPosition() else 15

            // call fill on section
            sections[section].blockPalette.fill(Vector3i.from(from.x, lowY, from.z), Vector3i.from(to.x, highY, to.z), blockID)
        }

        broadcastChanges()
    }
    fun clear(from: Vector3i, to: Vector3i) = fill(from, to, 0)

    fun setBlock(position: Vector3i, blockID: Int) {
        // get chunk location
        val chunkLocation = position.toSectionPosition()

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

        // call set event
        EventBus.callEvent(SetBlockEvent(this, position, chunkLocation))

        broadcastChanges()
    }

    fun getBlock(position: Vector3i): Int {
        // get chunk location
        val chunkLocation = position.toSectionPosition()

        // get section
        val section = sections[position.y.toSectionID()]

        // get block and return
        return section.blockPalette.get(chunkLocation)
    }
}

fun Vector3i.toSectionPosition() = Vector3i.from(
    x.toSectionPosition(),
    y.toSectionPosition(),
    z.toSectionPosition(),
)

fun Int.toSectionPosition() = floor((this % 16).inc16IfNegative().toDouble()).toInt()

data class SettingBlockEvent(val chunk: Chunk, val location: Vector3i, val chunkLocation: Vector3i, var isCancelled: Boolean = false): Event
data class SetBlockEvent(val chunk: Chunk, val location: Vector3i, val chunkLocation: Vector3i): Event
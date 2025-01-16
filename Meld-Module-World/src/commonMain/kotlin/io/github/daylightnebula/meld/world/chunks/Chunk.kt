package io.github.daylightnebula.meld.world.chunks

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.extensions.inc16IfNegative
import io.github.daylightnebula.meld.server.extensions.toSectionID
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.world.World
import io.github.daylightnebula.meld.world.WorldModule
import io.github.daylightnebula.meld.world.packets.JavaChunkPacket
import kotlin.math.floor
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Chunk(
    var dimensionRef: String = "",
    var position: Float2 = Float2(),
    var sections: Array<Section> = Array(24) { FilledSection() },
    var entities: MutableList<Entity> = mutableListOf()
) {
    fun writeJava(writer: ByteWriter) {
        // synchronously loop over the sections
        for (section in sections)
            section.writeJava(writer)
    }

    fun writeBedrock(writer: ByteWriter) {}

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
//              BEDROCK  is BedrockConnection -> NeedsBedrock()
            }
        }
    }

    fun fill(from: Float3, to: Float3, blockID: Int) {
        // loop through all sections
        (from.y.toSectionID() .. to.y.toSectionID()).forEach { section ->
            // get lowest and highest y
            val lowY = if (from.y.toSectionID() == section) from.y.toSectionPosition() else 0
            val highY = if (to.y.toSectionID() == section) to.y.toSectionPosition() else 15

            // call fill on section
            sections[section].blockPalette?.fill(Float3(from.x, lowY.toFloat(), from.z), Float3(to.x, highY.toFloat(), to.z), blockID)
        }

        broadcastChanges()
    }
    fun clear(from: Float3, to: Float3) = fill(from, to, 0)

    fun setBlock(position: Float3, blockID: Int) {
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
        palette?.set(chunkLocation, blockID)

        // call set event
        EventBus.callEvent(SetBlockEvent(this, position, chunkLocation))

        broadcastChanges()
    }

    fun getBlock(position: Float3): Int {
        // get chunk location
        val chunkLocation = position.toSectionPosition()

        // get section
        val section = sections[position.y.toSectionID()]

        // get block and return
        return section.blockPalette?.get(chunkLocation) ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Chunk

        if (dimensionRef != other.dimensionRef) return false
        if (position != other.position) return false
        if (!sections.contentEquals(other.sections)) return false
        if (entities != other.entities) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dimensionRef.hashCode()
        result = 31 * result + position.hashCode()
        result = 31 * result + sections.contentHashCode()
        result = 31 * result + entities.hashCode()
        return result
    }
}

fun Float3.toSectionPosition() = Float3(
    x.toSectionPosition(),
    y.toSectionPosition(),
    z.toSectionPosition(),
)

fun Int.toSectionPosition() = (this % 16).inc16IfNegative()
fun Float.toSectionPosition() = floor((this % 16).inc16IfNegative())

@OptIn(ExperimentalUuidApi::class)
data class SettingBlockEvent(val chunk: Chunk, val location: Float3, val chunkLocation: Float3, var isCancelled: Boolean = false): Event {
    companion object: Event.Data<SettingBlockEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(SettingBlockEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class SetBlockEvent(val chunk: Chunk, val location: Float3, val chunkLocation: Float3): Event {
    companion object: Event.Data<SetBlockEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(SetBlockEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}
package io.github.daylightnebula.worlds.chunks

import io.github.daylightnebula.networking.common.ByteWriter
import org.cloudburstmc.math.vector.Vector3i



class FlexiblePalette(
    var blockIDs: IntArray = intArrayOf(0),
    var blockReferences: ByteArray = ByteArray(16 * 16 * 16) { 0 },
    var startCount: Short = 0
) {
    companion object {
        fun filled(blockID: Int = 0) = FlexiblePalette(
            blockIDs = if (blockID != 0) intArrayOf(0, blockID) else intArrayOf(0),
            blockReferences = ByteArray(16 * 16 * 16) { if (blockID != 0) 1 else 0 },
            startCount = if (blockID == 0) 0 else 4096
        )
    }

    // count variable with private set
    var count: Short = startCount
        private set

    // writer to byte writer
    fun write(writer: ByteWriter) {
        // if the template is filled, send a filled chunk
        if (count.toInt() == 0 || count.toInt() == blockReferences.size) {
            writer.writeUByte(0u)
            writer.writeVarInt(if (count.toInt() == 0) blockIDs.first() else blockIDs.last())
            writer.writeVarInt(0)
            return
        }

        // 8 bits per entry (byte per block (could this be lowered for compression?))
        writer.writeUByte(8u)

        // write block ids
        writer.writeVarInt(blockIDs.size)
        for (id in blockIDs) writer.writeVarInt(id)

        // write block references
        if (blockReferences.size % 8 != 0) throw IllegalArgumentException("Block references was not divisible by 8.  Recommended size is 4096.")
        writer.writeVarInt(blockReferences.size / 8)
        writer.writeByteArray(blockReferences)
    }

    fun set(position: Vector3i, newID: Int) {
        println("Setting block at $position to $newID")
        // range check
        if (position.x !in 0..15 || position.y !in 0 .. 15 || position.z !in 0 .. 15)
            throw IllegalArgumentException("Section set call out of range $position")

        // get ref index
        val refIndex = (position.y * 256) + (position.z * 16) + modChunkRefIndexByX(position.x)

        // get index of new id in block ids
        var index = blockIDs.indexOf(newID)

        // if old ref index and new ref index are equal, cancel
        if (blockReferences[refIndex].toInt() == index) return

        // update index if necessary
        if (index == -1) {
            index = blockIDs.size
            blockIDs = intArrayOf(*blockIDs, newID)
        }

        // update block count
        if (newID == 0) count-- else count++

        // set block
        val oldIndex = blockReferences[refIndex]
        blockReferences[refIndex] = index.toByte()

        // check if old index does not exist in block references anymore
        if (!blockReferences.any { it == oldIndex }) {
            // remove the old index
            blockIDs = blockIDs.filterIndexed { index, _ -> index != oldIndex.toInt() }.toIntArray()

            // any items in block references greater than old index, remove 1 from that item
            for (i in blockReferences.indices) {
                if (blockReferences[i] > oldIndex) blockReferences[i]--
            }
        }
    }

    // why is this necessary for java edition clients?  IDK
    private fun modChunkRefIndexByX(index: Int) =
        when (index) {
            in 0 .. 7 -> 7 - index
            else -> 23 - index
        }
}
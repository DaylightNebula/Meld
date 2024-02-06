package io.github.daylightnebula.meld.world.anvil

import io.github.daylightnebula.meld.world.chunks.Chunk
import io.github.daylightnebula.meld.world.chunks.FilledSection
import io.github.daylightnebula.meld.world.chunks.FlexiblePalette
import io.github.daylightnebula.meld.world.chunks.Section
import org.cloudburstmc.math.vector.Vector2i
import org.cloudburstmc.math.vector.Vector3i
import org.jglrxavpok.hephaistos.mca.RegionFile
import org.jglrxavpok.hephaistos.mca.readers.ChunkReader
import org.jglrxavpok.hephaistos.mca.readers.ChunkSectionReader
import java.io.File
import java.io.RandomAccessFile
import kotlin.text.Typography.section

fun loadRegionFiles(folder: File): HashMap<Vector2i, Chunk> {
    val output = hashMapOf<Vector2i, Chunk>()
    folder.listFiles()?.forEach {
        // load file
        val nameTokens = it.name.split(".")
        val regionX = nameTokens[1].toInt()
        val regionY = nameTokens[2].toInt()
        val regionFile = RegionFile(RandomAccessFile(it, "rw"), regionX, regionY, -128, 255)

        // load each chunk
        for(chunkX in 0 until 32) {
            for(chunkY in 0 until 32) {
                val position = Vector2i.from(chunkX, chunkY)

                // get and loop through chunk sections
                val chunkNBT = try { regionFile.getChunkData(chunkX, chunkY) ?: continue } catch (ex: Exception) { continue }
                val outSections = Array<Section>(25) { FilledSection() }
                val reader = ChunkReader(chunkNBT)
                for ((index, section) in reader.getSections().withIndex()) {
                    // read section
                    val sectionReader = ChunkSectionReader(reader.getMinecraftVersion(), section)
                    if (sectionReader.isSectionEmpty() || !sectionReader.hasBlockStates()) continue
//                    println("NBT ${sectionReader.nbt.getCompound("block_states")}")
////                    sectionReader.

                    // loop through all blocks and copy
                    if (sectionReader.getBlockPalette() != null) {
                        val blockStateIndices = sectionReader.getUncompressedBlockStateIDs()
                        for (x in 0 until 16) {
                            for (y in 0 until 16) {
                                for (z in 0 until 16) {
                                    val blockIndex = (y * 256) + (z * 16) + x
                                    val block = blockStateIndices[blockIndex]
                                    outSections[index].blockPalette?.set(
                                        Vector3i.from(x, y, z),
                                        block
                                    )
                                }
                            }
                        }
                    }
                }

                // save chunk
                output[Vector2i.from(32 * regionX + chunkX, 32 * regionY + chunkY)] = Chunk("overworld", position, outSections, mutableListOf())
            }
        }
    }
    return output
}

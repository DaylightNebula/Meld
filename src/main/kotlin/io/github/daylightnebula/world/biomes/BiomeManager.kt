package io.github.daylightnebula.world.biomes

import io.github.daylightnebula.utils.NamespaceID
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.jglrxavpok.hephaistos.nbt.NBTType
import java.util.*
import java.util.Map
import kotlin.collections.Collection
import kotlin.collections.List


class BiomeManager {
    private val biomes: Int2ObjectMap<Biome> = Int2ObjectOpenHashMap<Biome>()

    init {
        addBiome(Biome.PLAINS)
    }

    /**
     * Adds a new biome. This does NOT send the new list to players.
     *
     * @param biome the biome to add
     */
    @Synchronized
    fun addBiome(biome: Biome) {
        biomes.put(biome.id(), biome)
    }

    /**
     * Removes a biome. This does NOT send the new list to players.
     *
     * @param biome the biome to remove
     */
    @Synchronized
    fun removeBiome(biome: Biome) {
        biomes.remove(biome.id())
    }

    /**
     * Returns an immutable copy of the biomes already registered.
     *
     * @return an immutable copy of the biomes already registered
     */
    @Synchronized
    fun unmodifiableCollection(): Collection<Biome> {
        return Collections.unmodifiableCollection(biomes.values)
    }

    /**
     * Gets a biome by its id.
     *
     * @param id the id of the biome
     * @return the [Biome] linked to this id
     */
    @Synchronized
    fun getById(id: Int): Biome {
        return biomes[id]
    }

    @Synchronized
    fun getByName(namespaceID: NamespaceID?): Biome? {
        var biome: Biome? = null
        for (biomeT in biomes.values) {
            if (biomeT.name()!! == namespaceID) {
                biome = biomeT
                break
            }
        }
        return biome
    }

    @Synchronized
    fun toNBT(): NBTCompound {
        return NBT.Compound(
            Map.of(
                "type", NBT.String("minecraft:worldgen/biome"),
                "value", NBT.List(NBTType.TAG_Compound, biomes.values.stream().map { it.toNbt() }.toList())
            )
        )
    }
}

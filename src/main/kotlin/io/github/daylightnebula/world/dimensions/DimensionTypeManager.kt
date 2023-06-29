package io.github.daylightnebula.world.dimensions

import io.github.daylightnebula.utils.NamespaceID
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.jglrxavpok.hephaistos.nbt.NBTType
import org.jglrxavpok.hephaistos.nbt.mutable.MutableNBTCompound
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList


class DimensionTypeManager {
    private val dimensionTypes: MutableList<DimensionType> = CopyOnWriteArrayList()

    init {
        addDimension(DimensionType.OVERWORLD)
    }

    /**
     * Adds a new dimension type. This does NOT send the new list to players.
     *
     * @param dimensionType the dimension to add
     */
    fun addDimension(dimensionType: DimensionType) {
        dimensionType.isRegistered = true
        dimensionTypes.add(dimensionType)
    }

    /**
     * Removes a dimension type. This does NOT send the new list to players.
     *
     * @param dimensionType the dimension to remove
     * @return if the dimension type was removed, false if it was not present before
     */
    fun removeDimension(dimensionType: DimensionType): Boolean {
        dimensionType.isRegistered = false
        return dimensionTypes.remove(dimensionType)
    }

    /**
     * @param namespaceID The dimension name
     * @return true if the dimension is registered
     */
    fun isRegistered(namespaceID: NamespaceID): Boolean {
        return isRegistered(getDimension(namespaceID))
    }

    /**
     * @param dimensionType dimension to check if is registered
     * @return true if the dimension is registered
     */
    fun isRegistered(dimensionType: DimensionType?): Boolean {
        return dimensionType != null && dimensionTypes.contains(dimensionType) && dimensionType.isRegistered
    }

    /**
     * Return to a @[DimensionType] only if present and registered
     *
     * @param namespaceID The Dimension Name
     * @return a DimensionType if it is present and registered
     */
    fun getDimension(namespaceID: NamespaceID): DimensionType? {
        return unmodifiableList().stream().filter { dimensionType: DimensionType? ->
            dimensionType!!.getName()!!.equals(namespaceID)
        }.filter { it!!.isRegistered }.findFirst().orElse(null)
    }

    /**
     * Returns an immutable copy of the dimension types already registered.
     *
     * @return an unmodifiable [List] containing all the added dimensions
     */
    fun unmodifiableList(): List<DimensionType?> {
        return Collections.unmodifiableList(dimensionTypes)
    }

    /**
     * Creates the [NBTCompound] containing all the registered dimensions.
     *
     *
     * Used when a player connects.
     *
     * @return an nbt compound containing the registered dimensions
     */
    fun toNBT(): NBTCompound {
        return NBT.Compound { dimensions: MutableNBTCompound ->
            dimensions.setString("type", "minecraft:dimension_type")
            dimensions["value"] = NBT.List(
                NBTType.TAG_Compound,
                dimensionTypes.stream()
                    .map { it.toIndexedNBT() }
                    .toList()
            )
        }
    }
}

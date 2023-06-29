package io.github.daylightnebula.world.biomes

import io.github.daylightnebula.utils.NamespaceID
import io.github.daylightnebula.utils.Point
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.jglrxavpok.hephaistos.nbt.mutable.MutableNBTCompound
import java.util.concurrent.atomic.AtomicInteger


class Biome internal constructor(
    name: NamespaceID?,
    depth: Float,
    temperature: Float,
    scale: Float,
    downfall: Float,
    category: Category,
    effects: BiomeEffects,
    precipitation: Precipitation,
    temperatureModifier: TemperatureModifier
) {
    private val id = ID_COUNTER.getAndIncrement()
    private val name: NamespaceID?
    private val depth: Float
    private val temperature: Float
    private val scale: Float
    private val downfall: Float
    private val category: Category
    private val effects: BiomeEffects
    private val precipitation: Precipitation
    private val temperatureModifier: TemperatureModifier

    init {
        this.name = name
        this.depth = depth
        this.temperature = temperature
        this.scale = scale
        this.downfall = downfall
        this.category = category
        this.effects = effects
        this.precipitation = precipitation
        this.temperatureModifier = temperatureModifier
    }

    fun toNbt(): NBTCompound {
//        Check.notNull(name, "The biome namespace cannot be null")
//        Check.notNull(effects, "The biome effects cannot be null")
        return NBT.Compound { nbt: MutableNBTCompound ->
            nbt.setString("name", name.toString())
            nbt.setInt("id", id())
            nbt["element"] = NBT.Compound { element: MutableNBTCompound ->
                element.setFloat("depth", depth)
                element.setFloat("temperature", temperature)
                element.setFloat("scale", scale)
                element.setFloat("downfall", downfall)
                element.setString("category", category.name.lowercase())
                element.setString("precipitation", precipitation.name.lowercase())
                if (temperatureModifier != TemperatureModifier.NONE) element.setString(
                    "temperature_modifier",
                    temperatureModifier.name.lowercase()
                )
                element["effects"] = effects.toNbt()
            }
        }
    }

    fun id(): Int {
        return id
    }

    fun name(): NamespaceID? {
        return name
    }

    fun depth(): Float {
        return depth
    }

    fun temperature(): Float {
        return temperature
    }

    fun scale(): Float {
        return scale
    }

    fun downfall(): Float {
        return downfall
    }

    fun category(): Category {
        return this.category
    }

    fun effects(): BiomeEffects {
        return effects
    }

    fun precipitation(): Precipitation {
        return precipitation
    }

    fun temperatureModifier(): TemperatureModifier {
        return temperatureModifier
    }

    enum class Precipitation {
        NONE,
        RAIN,
        SNOW
    }

    enum class Category {
        NONE,
        TAIGA,
        EXTREME_HILLS,
        JUNGLE,
        MESA,
        PLAINS,
        SAVANNA,
        ICY,
        THE_END,
        BEACH,
        FOREST,
        OCEAN,
        DESERT,
        RIVER,
        SWAMP,
        MUSHROOM,
        NETHER,
        UNDERGROUND,
        MOUNTAIN
    }

    enum class TemperatureModifier {
        NONE,
        FROZEN
    }

    class Builder internal constructor() {
        private var name: NamespaceID? = null
        private var depth = 0.2f
        private var temperature = 0.25f
        private var scale = 0.2f
        private var downfall = 0.8f
        private var category = Category.NONE
        private var effects: BiomeEffects = DEFAULT_EFFECTS
        private var precipitation = Precipitation.RAIN
        private var temperatureModifier = TemperatureModifier.NONE
        fun name(name: NamespaceID?): Builder {
            this.name = name
            return this
        }

        fun depth(depth: Float): Builder {
            this.depth = depth
            return this
        }

        fun temperature(temperature: Float): Builder {
            this.temperature = temperature
            return this
        }

        fun scale(scale: Float): Builder {
            this.scale = scale
            return this
        }

        fun downfall(downfall: Float): Builder {
            this.downfall = downfall
            return this
        }

        fun category(category: Category): Builder {
            this.category = category
            return this
        }

        fun effects(effects: BiomeEffects): Builder {
            this.effects = effects
            return this
        }

        fun precipitation(precipitation: Precipitation): Builder {
            this.precipitation = precipitation
            return this
        }

        fun temperatureModifier(temperatureModifier: TemperatureModifier): Builder {
            this.temperatureModifier = temperatureModifier
            return this
        }

        fun build(): Biome {
            return Biome(
                name,
                depth,
                temperature,
                scale,
                downfall,
                category,
                effects,
                precipitation,
                temperatureModifier
            )
        }
    }

    interface Setter {
        fun setBiome(x: Int, y: Int, z: Int, biome: Biome)
        fun setBiome(blockPosition: Point, biome: Biome) {
            setBiome(blockPosition.blockX(), blockPosition.blockY(), blockPosition.blockZ(), biome)
        }
    }

    interface Getter {
        fun getBiome(x: Int, y: Int, z: Int): Biome
        fun getBiome(point: Point): Biome {
            return getBiome(point.blockX(), point.blockY(), point.blockZ())
        }
    }

    companion object {
        val ID_COUNTER = AtomicInteger(0)
        private val DEFAULT_EFFECTS: BiomeEffects = BiomeEffects.builder()
            .fogColor(0xC0D8FF)
            .skyColor(0x78A7FF)
            .waterColor(0x3F76E4)
            .waterFogColor(0x50533)
            .build()

        //A plains biome has to be registered or else minecraft will crash
        val PLAINS = builder()
            .category(Category.NONE)
            .name(NamespaceID.from("minecraft:plains"))
            .temperature(0.8f)
            .downfall(0.4f)
            .depth(0.125f)
            .scale(0.05f)
            .effects(DEFAULT_EFFECTS)
            .build()

        fun builder(): Builder {
            return Builder()
        }
    }
}

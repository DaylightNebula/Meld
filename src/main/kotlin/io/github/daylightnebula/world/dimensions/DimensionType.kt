package io.github.daylightnebula.world.dimensions

import io.github.daylightnebula.utils.NamespaceID
import org.jglrxavpok.hephaistos.mcdata.VanillaMaxY
import org.jglrxavpok.hephaistos.mcdata.VanillaMinY
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.jglrxavpok.hephaistos.nbt.mutable.MutableNBTCompound
import java.util.*
import java.util.Map
import java.util.concurrent.atomic.AtomicInteger


class DimensionType internal constructor(
    name: NamespaceID?, natural: Boolean, ambientLight: Float, ceilingEnabled: Boolean,
    skylightEnabled: Boolean, fixedTime: Long?, raidCapable: Boolean,
    respawnAnchorSafe: Boolean, ultrawarm: Boolean, bedSafe: Boolean, effects: String?, piglinSafe: Boolean,
    minY: Int, height: Int, logicalHeight: Int, coordinateScale: Double, infiniburn: NamespaceID
) {
    val id = idCounter.getAndIncrement()

    @Volatile
    var isRegistered = false
    private val name: NamespaceID?
    val isNatural: Boolean
    val ambientLight: Float
    val isCeilingEnabled: Boolean
    val isSkylightEnabled: Boolean
    val fixedTime: Long?
    val isRaidCapable: Boolean
    val isRespawnAnchorSafe: Boolean
    val isUltrawarm: Boolean
    val isBedSafe: Boolean
    val effects: String?
    val isPiglinSafe: Boolean
    val minY: Int
    val height: Int
    val logicalHeight: Int
    val coordinateScale: Double
    private val infiniburn: NamespaceID

    init {
        this.name = name
        isNatural = natural
        this.ambientLight = ambientLight
        isCeilingEnabled = ceilingEnabled
        isSkylightEnabled = skylightEnabled
        this.fixedTime = fixedTime
        isRaidCapable = raidCapable
        isRespawnAnchorSafe = respawnAnchorSafe
        isUltrawarm = ultrawarm
        isBedSafe = bedSafe
        this.effects = effects
        isPiglinSafe = piglinSafe
        this.minY = minY
        this.height = height
        this.logicalHeight = logicalHeight
        this.coordinateScale = coordinateScale
        this.infiniburn = infiniburn
    }

    fun toIndexedNBT(): NBTCompound {
        return NBT.Compound(
            Map.of(
                "name", NBT.String(name.toString()),
                "id", NBT.Int(id),
                "element", toNBT()
            )
        )
    }

    fun toNBT(): NBTCompound {
        return NBT.Compound { nbt: MutableNBTCompound ->
            nbt.setFloat("ambient_light", ambientLight)
            nbt.setString("infiniburn", "#" + infiniburn.toString())
            nbt.setByte("natural", (if (isNatural) 0x01 else 0x00).toByte())
            nbt.setByte("has_ceiling", (if (isCeilingEnabled) 0x01 else 0x00).toByte())
            nbt.setByte("has_skylight", (if (isSkylightEnabled) 0x01 else 0x00).toByte())
            nbt.setByte("ultrawarm", (if (isUltrawarm) 0x01 else 0x00).toByte())
            nbt.setByte("has_raids", (if (isRaidCapable) 0x01 else 0x00).toByte())
            nbt.setByte("respawn_anchor_works", (if (isRespawnAnchorSafe) 0x01 else 0x00).toByte())
            nbt.setByte("bed_works", (if (isBedSafe) 0x01 else 0x00).toByte())
            nbt.setString("effects", effects!!)
            nbt.setByte("piglin_safe", (if (isPiglinSafe) 0x01 else 0x00).toByte())
            nbt.setInt("min_y", minY)
            nbt.setInt("height", height)
            nbt.setInt("logical_height", logicalHeight)
            nbt.setDouble("coordinate_scale", coordinateScale)
            nbt.setString("name", name.toString())
            nbt.setInt("monster_spawn_block_light_limit", 0)
            nbt.setInt("monster_spawn_light_level", 11)
            if (fixedTime != null) nbt.setLong("fixed_time", fixedTime)
        }
    }

    override fun toString(): String {
        return name.toString()
    }

    fun getName(): NamespaceID? {
        return name
    }

    val maxY: Int
        get() = minY + height

    fun getInfiniburn(): NamespaceID {
        return infiniburn
    }

    val totalHeight: Int
        get() = minY + height

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as DimensionType
        return id == that.id && name == that.name
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name)
    }

    class DimensionTypeBuilder internal constructor() {
        private var name: NamespaceID? = null
        private var natural = false
        private var ambientLight = 0f
        private var ceilingEnabled = false
        private var skylightEnabled = false
        private var fixedTime: Long? = null
        private var raidCapable = false
        private var respawnAnchorSafe = false
        private var ultrawarm = false
        private var bedSafe = true
        private var effects: String? = "minecraft:overworld"
        private var piglinSafe = false
        private var minY = VanillaMinY
        private var logicalHeight = VanillaMaxY - VanillaMinY + 1
        private var height = VanillaMaxY - VanillaMinY + 1
        private var coordinateScale = 1.0
        private var infiniburn: NamespaceID = NamespaceID.from("minecraft:infiniburn_overworld")
        fun name(name: NamespaceID?): DimensionTypeBuilder {
            this.name = name
            return this
        }

        fun natural(natural: Boolean): DimensionTypeBuilder {
            this.natural = natural
            return this
        }

        fun ambientLight(ambientLight: Float): DimensionTypeBuilder {
            this.ambientLight = ambientLight
            return this
        }

        fun ceilingEnabled(ceilingEnabled: Boolean): DimensionTypeBuilder {
            this.ceilingEnabled = ceilingEnabled
            return this
        }

        fun skylightEnabled(skylightEnabled: Boolean): DimensionTypeBuilder {
            this.skylightEnabled = skylightEnabled
            return this
        }

        fun fixedTime(fixedTime: Long?): DimensionTypeBuilder {
            this.fixedTime = fixedTime
            return this
        }

        fun raidCapable(raidCapable: Boolean): DimensionTypeBuilder {
            this.raidCapable = raidCapable
            return this
        }

        fun respawnAnchorSafe(respawnAnchorSafe: Boolean): DimensionTypeBuilder {
            this.respawnAnchorSafe = respawnAnchorSafe
            return this
        }

        fun ultrawarm(ultrawarm: Boolean): DimensionTypeBuilder {
            this.ultrawarm = ultrawarm
            return this
        }

        fun bedSafe(bedSafe: Boolean): DimensionTypeBuilder {
            this.bedSafe = bedSafe
            return this
        }

        fun effects(effects: String?): DimensionTypeBuilder {
            this.effects = effects
            return this
        }

        fun piglinSafe(piglinSafe: Boolean): DimensionTypeBuilder {
            this.piglinSafe = piglinSafe
            return this
        }

        fun minY(minY: Int): DimensionTypeBuilder {
            this.minY = minY
            return this
        }

        fun height(height: Int): DimensionTypeBuilder {
            this.height = height
            return this
        }

        fun logicalHeight(logicalHeight: Int): DimensionTypeBuilder {
            this.logicalHeight = logicalHeight
            return this
        }

        fun coordinateScale(coordinateScale: Double): DimensionTypeBuilder {
            this.coordinateScale = coordinateScale
            return this
        }

        fun infiniburn(infiniburn: NamespaceID): DimensionTypeBuilder {
            this.infiniburn = infiniburn
            return this
        }

        fun build(): DimensionType {
            return DimensionType(
                name, natural, ambientLight, ceilingEnabled, skylightEnabled,
                fixedTime, raidCapable, respawnAnchorSafe, ultrawarm, bedSafe, effects,
                piglinSafe, minY, height, logicalHeight, coordinateScale, infiniburn
            )
        }
    }

    companion object {
        private val idCounter = AtomicInteger(0)
        val OVERWORLD = builder(NamespaceID.from("minecraft:overworld"))
            .ultrawarm(false)
            .natural(true)
            .piglinSafe(false)
            .respawnAnchorSafe(false)
            .bedSafe(true)
            .raidCapable(true)
            .skylightEnabled(true)
            .ceilingEnabled(false)
            .fixedTime(null)
            .ambientLight(0.0f)
            .height(384)
            .minY(-64)
            .logicalHeight(384)
            .infiniburn(NamespaceID.from("minecraft:infiniburn_overworld"))
            .build()

        fun builder(name: NamespaceID?): DimensionTypeBuilder {
            return hiddenBuilder().name(name)
        }

        fun hiddenBuilder(): DimensionTypeBuilder {
            return DimensionTypeBuilder()
        }

        fun fromNBT(nbt: NBTCompound): DimensionType {
            return builder(NamespaceID.from(nbt.getString("name")!!))
                .ambientLight(nbt.getFloat("ambient_light")!!)
                .infiniburn(NamespaceID.from(nbt.getString("infiniburn")!!.replaceFirst("#".toRegex(), "")))
                .natural(nbt.getByte("natural")!!.toInt() != 0)
                .ceilingEnabled(nbt.getByte("has_ceiling")!!.toInt() != 0)
                .skylightEnabled(nbt.getByte("has_skylight")!!.toInt() != 0)
                .ultrawarm(nbt.getByte("ultrawarm")!!.toInt() != 0)
                .raidCapable(nbt.getByte("has_raids")!!.toInt() != 0)
                .respawnAnchorSafe(nbt.getByte("respawn_anchor_works")!!.toInt() != 0)
                .bedSafe(nbt.getByte("bed_works")!!.toInt() != 0)
                .effects(nbt.getString("effects"))
                .piglinSafe(nbt.getByte("piglin_safe")!!.toInt() != 0)
                .logicalHeight(nbt.getInt("logical_height")!!)
                .coordinateScale(nbt.getDouble("coordinate_scale")!!)
                .build()
        }
    }
}

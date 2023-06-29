package io.github.daylightnebula.world.biomes

import io.github.daylightnebula.utils.NamespaceID
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.jglrxavpok.hephaistos.nbt.mutable.MutableNBTCompound
import java.util.Map


class BiomeEffects(
    val fogColor: Int,
    val skyColor: Int,
    val waterColor: Int,
    val waterFogColor: Int,
    val foliageColor: Int,
    val grassColor: Int,
    val grassColorModifier: GrassColorModifier?,
    biomeParticle: BiomeParticle?,
    ambientSound: NamespaceID?,
    moodSound: MoodSound?,
    additionsSound: AdditionsSound?,
    music: Music?
) {
    fun toNbt(): NBTCompound {
        return NBT.Compound { nbt: MutableNBTCompound ->
            nbt.setInt("fog_color", fogColor)
            if (foliageColor != -1) nbt.setInt("foliage_color", foliageColor)
            if (grassColor != -1) nbt.setInt("grass_color", grassColor)
            nbt.setInt("sky_color", skyColor)
            nbt.setInt("water_color", waterColor)
            nbt.setInt("water_fog_color", waterFogColor)
            if (grassColorModifier != null) nbt.setString("grass_color_modifier", grassColorModifier.name.lowercase())
            if (biomeParticle != null) nbt["particle"] = biomeParticle.toNbt()
            if (ambientSound != null) nbt.setString("ambient_sound", ambientSound.toString())
            if (moodSound != null) nbt["mood_sound"] = moodSound.toNbt()
            if (additionsSound != null) nbt["additions_sound"] = additionsSound.toNbt()
            if (music != null) nbt["music"] = music.toNbt()
        }
    }

    enum class GrassColorModifier {
        NONE,
        DARK_FOREST,
        SWAMP
    }

    class MoodSound(sound: NamespaceID, tickDelay: Int, blockSearchExtent: Int, offset: Double) {
        fun toNbt(): NBTCompound {
            return NBT.Compound(
                Map.of(
                    "sound", NBT.String(sound.toString()),
                    "tick_delay", NBT.Int(tickDelay),
                    "block_search_extent", NBT.Int(blockSearchExtent),
                    "offset", NBT.Double(offset)
                )
            )
        }

        val sound: NamespaceID
        val tickDelay: Int
        val blockSearchExtent: Int
        val offset: Double

        init {
            this.sound = sound
            this.tickDelay = tickDelay
            this.blockSearchExtent = blockSearchExtent
            this.offset = offset
        }
    }

    class AdditionsSound(sound: NamespaceID, tickChance: Double) {
        fun toNbt(): NBTCompound {
            return NBT.Compound(
                Map.of(
                    "sound", NBT.String(sound.toString()),
                    "tick_chance", NBT.Double(tickChance)
                )
            )
        }

        val sound: NamespaceID
        val tickChance: Double

        init {
            this.sound = sound
            this.tickChance = tickChance
        }
    }

    class Music(sound: NamespaceID, minDelay: Int, maxDelay: Int, replaceCurrentMusic: Boolean) {
        fun toNbt(): NBTCompound {
            return NBT.Compound(
                Map.of(
                    "sound", NBT.String(sound.toString()),
                    "min_delay", NBT.Int(minDelay),
                    "max_delay", NBT.Int(maxDelay),
                    "replace_current_music", NBT.Boolean(replaceCurrentMusic)
                )
            )
        }

        val sound: NamespaceID
        val minDelay: Int
        val maxDelay: Int
        val replaceCurrentMusic: Boolean

        init {
            this.sound = sound
            this.minDelay = minDelay
            this.maxDelay = maxDelay
            this.replaceCurrentMusic = replaceCurrentMusic
        }
    }

    class Builder internal constructor() {
        private var fogColor = 0
        private var skyColor = 0
        private var waterColor = 0
        private var waterFogColor = 0
        private var foliageColor = -1
        private var grassColor = -1
        private var grassColorModifier: GrassColorModifier? = null
        private var biomeParticle: BiomeParticle? = null
        private var ambientSound: NamespaceID? = null
        private var moodSound: MoodSound? = null
        private var additionsSound: AdditionsSound? = null
        private var music: Music? = null
        fun fogColor(fogColor: Int): Builder {
            this.fogColor = fogColor
            return this
        }

        fun skyColor(skyColor: Int): Builder {
            this.skyColor = skyColor
            return this
        }

        fun waterColor(waterColor: Int): Builder {
            this.waterColor = waterColor
            return this
        }

        fun waterFogColor(waterFogColor: Int): Builder {
            this.waterFogColor = waterFogColor
            return this
        }

        fun foliageColor(foliageColor: Int): Builder {
            this.foliageColor = foliageColor
            return this
        }

        fun grassColor(grassColor: Int): Builder {
            this.grassColor = grassColor
            return this
        }

        fun grassColorModifier(grassColorModifier: GrassColorModifier?): Builder {
            this.grassColorModifier = grassColorModifier
            return this
        }

        fun biomeParticle(biomeParticle: BiomeParticle?): Builder {
            this.biomeParticle = biomeParticle
            return this
        }

        fun ambientSound(ambientSound: NamespaceID?): Builder {
            this.ambientSound = ambientSound
            return this
        }

        fun moodSound(moodSound: MoodSound?): Builder {
            this.moodSound = moodSound
            return this
        }

        fun additionsSound(additionsSound: AdditionsSound?): Builder {
            this.additionsSound = additionsSound
            return this
        }

        fun music(music: Music?): Builder {
            this.music = music
            return this
        }

        fun build(): BiomeEffects {
            return BiomeEffects(
                fogColor, skyColor, waterColor, waterFogColor, foliageColor,
                grassColor, grassColorModifier, biomeParticle,
                ambientSound, moodSound, additionsSound, music
            )
        }
    }

    val biomeParticle: BiomeParticle?
    val ambientSound: NamespaceID?
    val moodSound: MoodSound?
    val additionsSound: AdditionsSound?
    val music: Music?

    init {
        this.biomeParticle = biomeParticle
        this.ambientSound = ambientSound
        this.moodSound = moodSound
        this.additionsSound = additionsSound
        this.music = music
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }
}

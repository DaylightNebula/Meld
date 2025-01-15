package io.github.daylightnebula.meld.server.registries

import kotlinx.serialization.Serializable

@Serializable
data class BiomeRegistry(
    val name: String = "minecraft:worldgen/biome",
    val value: List<BiomeRegistryEntry> = listOf()
) {
    companion object {
        val default = BiomeRegistry()
    }
}

@Serializable
data class BiomeRegistryEntry(
    val name: String = "minecraft:plains",
    val id: Int = 0,
    val value: BiomeRegistryElement
)

@Serializable
data class BiomeRegistryElement(
    val has_precipitation: Byte = 0x01,
    val temperature: Float = 1f,
    val downfall: Float = 0f,
    val effects: BiomeRegistryEffects = BiomeRegistryEffects()
)

@Serializable
data class BiomeRegistryEffects(
    val fog_color: Int = 0xC0D8FF,
    val sky_color: Int = 0x78A7FF,
    val water_color: Int = 0x3F76E4,
    val water_fog_color: Int = 0x50533
)
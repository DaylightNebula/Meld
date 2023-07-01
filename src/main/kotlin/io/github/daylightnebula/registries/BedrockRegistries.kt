package io.github.daylightnebula.registries

object BedrockRegistries {
    val nbtLoader = NbtRegistryLoader()
    val BIOMES_NBT = SimpleRegistry.create("bedrock/biome_definitions.dat", nbtLoader)
}
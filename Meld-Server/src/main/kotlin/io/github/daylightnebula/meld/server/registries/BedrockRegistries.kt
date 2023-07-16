package io.github.daylightnebula.meld.server.registries

object BedrockRegistries {
    val nbtLoader = NbtRegistryLoader()
    val BIOMES_NBT = SimpleRegistry.create("bedrock/biome_definitions.dat", nbtLoader)
}
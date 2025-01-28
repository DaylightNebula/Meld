package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.generated.Biome
import io.github.daylightnebula.meld.server.registries.RegistryCodec
import net.benwoodworth.knbt.NbtCompound

object BiomeRegistry: RegistryCodec.Codec {
    override fun name() = "minecraft:worldgen/biome"
    override fun build() = Biome.all.map { biome -> "minecraft:${biome.name}" to null }
}
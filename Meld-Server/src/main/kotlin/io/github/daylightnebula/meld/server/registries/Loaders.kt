package io.github.daylightnebula.meld.server.registries

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtUtils


class NbtRegistryLoader : RegistryLoader<String, NbtMap> {
    override fun load(input: String): NbtMap {
        try {
            NbtUtils.createNetworkReader(this::class.java.classLoader.getResourceAsStream(input), true, true).use { nbtInputStream -> return nbtInputStream.readTag() as NbtMap }
        } catch (e: Exception) {
            throw AssertionError("Failed to load registrations for $input", e)
        }
    }
}
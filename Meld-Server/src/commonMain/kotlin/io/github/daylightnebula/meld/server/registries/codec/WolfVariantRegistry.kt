package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.registries.RegistryCodec

object WolfVariantRegistry: RegistryCodec.Codec {
    override fun name() = data.name
    override fun build() = data.entries

    val data = RegistryCodec.interpretSnifferData("""
        {
            "entries": [
                {
                    "id": {
                        "raw_string": "minecraft:ashen"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:black"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:chestnut"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:pale"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:rusty"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:snowy"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:spotted"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:striped"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:woods"
                    }
                }
            ],
            "registry": {
                "raw_string": "minecraft:wolf_variant"
            }
        }
    """.trimIndent())
}
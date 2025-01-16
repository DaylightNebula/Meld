package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.registries.RegistryCodec

object TrimMaterialRegistry: RegistryCodec.Codec {
    override fun name() = data.name
    override fun build() = data.entries

    val data = RegistryCodec.interpretSnifferData("""
        {
            "entries": [
                {
                    "id": {
                        "raw_string": "minecraft:amethyst"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:copper"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:diamond"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:emerald"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:gold"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:iron"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:lapis"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:netherite"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:quartz"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:redstone"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:resin"
                    }
                }
            ],
            "registry": {
                "raw_string": "minecraft:trim_material"
            }
        }
    """.trimIndent())
}
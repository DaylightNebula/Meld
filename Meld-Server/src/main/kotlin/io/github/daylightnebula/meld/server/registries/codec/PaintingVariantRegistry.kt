package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.registries.RegistryCodec

object PaintingVariantRegistry: RegistryCodec.Codec {
    override fun name() = data.name
    override fun build() = data.entries

    val data = RegistryCodec.interpretSnifferData("""
        {
            "entries": [
                {
                    "id": {
                        "raw_string": "minecraft:alban"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:aztec"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:aztec2"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:backyard"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:baroque"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:bomb"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:bouquet"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:burning_skull"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:bust"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:cavebird"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:changing"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:cotan"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:courbet"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:creebet"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:donkey_kong"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:earth"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:endboss"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:fern"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:fighters"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:finding"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:fire"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:graham"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:humble"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:kebab"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:lowmist"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:match"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:meditative"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:orb"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:owlemons"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:passage"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:pigscene"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:plant"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:pointer"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:pond"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:pool"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:prairie_ride"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:sea"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:skeleton"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:skull_and_roses"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:stage"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:sunflowers"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:sunset"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:tides"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:unpacked"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:void"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:wanderer"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:wasteland"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:water"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:wind"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:wither"
                    }
                }
            ],
            "registry": {
                "raw_string": "minecraft:painting_variant"
            }
        }
    """.trimIndent())
}
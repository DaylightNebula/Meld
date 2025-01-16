package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.registries.RegistryCodec

object EnchantmentRegistry: RegistryCodec.Codec {
    override fun name() = data.name
    override fun build() = data.entries

    val data = RegistryCodec.interpretSnifferData("""
        {
            "entries": [
                {
                    "id": {
                        "raw_string": "minecraft:aqua_affinity"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:bane_of_arthropods"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:binding_curse"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:blast_protection"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:breach"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:channeling"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:density"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:depth_strider"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:efficiency"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:feather_falling"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:fire_aspect"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:fire_protection"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:flame"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:fortune"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:frost_walker"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:impaling"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:infinity"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:knockback"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:looting"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:loyalty"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:luck_of_the_sea"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:lure"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:mending"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:multishot"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:piercing"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:power"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:projectile_protection"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:protection"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:punch"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:quick_charge"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:respiration"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:riptide"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:sharpness"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:silk_touch"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:smite"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:soul_speed"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:sweeping_edge"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:swift_sneak"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:thorns"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:unbreaking"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:vanishing_curse"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:wind_burst"
                    }
                }
            ],
            "registry": {
                "raw_string": "minecraft:enchantment"
            }
        }
    """.trimIndent())
}
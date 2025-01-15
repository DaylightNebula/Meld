package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.registries.RegistryCodec

object DamageTypeRegistry: RegistryCodec.Codec {
    override fun name() = TrimPatternRegistry.data.name
    override fun build() = TrimPatternRegistry.data.entries

    val data = RegistryCodec.interpretSnifferData("""
        {
            "entries": [
                {
                    "id": {
                        "raw_string": "minecraft:arrow"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:bad_respawn_point"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:cactus"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:campfire"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:cramming"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:dragon_breath"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:drown"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:dry_out"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:ender_pearl"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:explosion"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:fall"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:falling_anvil"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:falling_block"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:falling_stalactite"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:fireball"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:fireworks"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:fly_into_wall"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:freeze"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:generic"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:generic_kill"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:hot_floor"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:in_fire"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:in_wall"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:indirect_magic"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:lava"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:lightning_bolt"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:mace_smash"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:magic"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:mob_attack"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:mob_attack_no_aggro"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:mob_projectile"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:on_fire"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:out_of_world"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:outside_border"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:player_attack"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:player_explosion"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:sonic_boom"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:spit"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:stalagmite"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:starve"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:sting"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:sweet_berry_bush"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:thorns"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:thrown"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:trident"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:unattributed_fireball"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:wind_charge"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:wither"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:wither_skull"
                    }
                }
            ],
            "registry": {
                "raw_string": "minecraft:damage_type"
            }
        }
    """.trimIndent())
}
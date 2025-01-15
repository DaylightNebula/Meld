package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.registries.RegistryCodec

object BannerPatternRegistry: RegistryCodec.Codec {
    override fun name() = TrimPatternRegistry.data.name
    override fun build() = TrimPatternRegistry.data.entries

    val data = RegistryCodec.interpretSnifferData("""
        {
            "entries": [
                {
                    "id": {
                        "raw_string": "minecraft:base"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:border"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:bricks"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:circle"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:creeper"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:cross"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:curly_border"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:diagonal_left"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:diagonal_right"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:diagonal_up_left"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:diagonal_up_right"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:flow"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:flower"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:globe"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:gradient"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:gradient_up"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:guster"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:half_horizontal"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:half_horizontal_bottom"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:half_vertical"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:half_vertical_right"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:mojang"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:piglin"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:rhombus"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:skull"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:small_stripes"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:square_bottom_left"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:square_bottom_right"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:square_top_left"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:square_top_right"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:straight_cross"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:stripe_bottom"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:stripe_center"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:stripe_downleft"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:stripe_downright"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:stripe_left"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:stripe_middle"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:stripe_right"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:stripe_top"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:triangle_bottom"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:triangle_top"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:triangles_bottom"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:triangles_top"
                    }
                }
            ],
            "registry": {
                "raw_string": "minecraft:banner_pattern"
            }
        }
    """.trimIndent())
}
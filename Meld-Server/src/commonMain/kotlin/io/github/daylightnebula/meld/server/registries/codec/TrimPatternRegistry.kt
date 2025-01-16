package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.registries.RegistryCodec

object TrimPatternRegistry: RegistryCodec.Codec {
    override fun name() = data.name
    override fun build() = data.entries

    val data = RegistryCodec.interpretSnifferData("""
        {
            "entries": [
                {
                    "id": {
                        "raw_string": "minecraft:bolt"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:coast"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:dune"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:eye"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:flow"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:host"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:raiser"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:rib"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:sentry"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:shaper"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:silence"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:snout"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:spire"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:tide"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:vex"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:ward"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:wayfinder"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:wild"
                    }
                }
            ],
            "registry": {
                "raw_string": "minecraft:trim_pattern"
            }
        }
    """.trimIndent())

//    val data = RegistryCodec.interpretSnifferData("""
//        {
//            "entries": [
//                {
//                    "id": {
//                        "raw_string": "minecraft:bolt"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:coast"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:dune"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:eye"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:flow"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:host"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:raiser"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:rib"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:sentry"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:shaper"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:silence"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:snout"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:spire"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:tide"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:vex"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:ward"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:wayfinder"
//                    }
//                },
//                {
//                    "id": {
//                        "raw_string": "minecraft:wild"
//                    }
//                }
//            ],
//            "registry": {
//                "raw_string": "minecraft:trim_pattern"
//            }
//        }
//    """.trimIndent())
}
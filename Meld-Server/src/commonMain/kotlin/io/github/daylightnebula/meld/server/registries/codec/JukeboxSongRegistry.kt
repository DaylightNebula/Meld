package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.registries.RegistryCodec

object JukeboxSongRegistry: RegistryCodec.Codec {
    override fun name() = data.name
    override fun build() = data.entries

    val data = RegistryCodec.interpretSnifferData("""
        {
            "entries": [
                {
                    "id": {
                        "raw_string": "minecraft:11"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:13"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:5"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:blocks"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:cat"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:chirp"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:creator"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:creator_music_box"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:far"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:mall"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:mellohi"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:otherside"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:pigstep"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:precipice"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:relic"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:stal"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:strad"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:wait"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:ward"
                    }
                }
            ],
            "registry": {
                "raw_string": "minecraft:jukebox_song"
            }
        }
    """.trimIndent())
}
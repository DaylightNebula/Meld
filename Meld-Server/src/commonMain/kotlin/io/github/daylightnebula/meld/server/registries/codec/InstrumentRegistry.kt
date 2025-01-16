package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.registries.RegistryCodec

object InstrumentRegistry: RegistryCodec.Codec {
    override fun name() = data.name
    override fun build() = data.entries

    val data = RegistryCodec.interpretSnifferData("""
        {
            "entries": [
                {
                    "id": {
                        "raw_string": "minecraft:admire_goat_horn"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:call_goat_horn"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:dream_goat_horn"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:feel_goat_horn"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:ponder_goat_horn"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:seek_goat_horn"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:sing_goat_horn"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:yearn_goat_horn"
                    }
                }
            ],
            "registry": {
                "raw_string": "minecraft:instrument"
            }
        }
    """.trimIndent())
}
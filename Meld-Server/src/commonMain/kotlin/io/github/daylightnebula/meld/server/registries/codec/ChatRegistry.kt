package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.registries.RegistryCodec

object ChatRegistry: RegistryCodec.Codec {
    override fun name() = data.name
    override fun build() = data.entries

    val data = RegistryCodec.interpretSnifferData("""
        {
            "entries": [
                {
                    "id": {
                        "raw_string": "minecraft:chat"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:emote_command"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:msg_command_incoming"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:msg_command_outgoing"
                    }
                },
                {
                    "data": {
                        "content": {
                            "chat": {
                                "content": {
                                    "parameters": {
                                        "content": [
                                            "content"
                                        ],
                                        "name": "parameters",
                                        "type": "TagList"
                                    },
                                    "translation_key": {
                                        "content": "%s",
                                        "name": "translation_key",
                                        "type": "TagString"
                                    }
                                },
                                "name": "chat",
                                "type": "TagCompound"
                            },
                            "narration": {
                                "content": {
                                    "parameters": {
                                        "content": [
                                            "content"
                                        ],
                                        "name": "parameters",
                                        "type": "TagList"
                                    },
                                    "translation_key": {
                                        "content": "%s",
                                        "name": "translation_key",
                                        "type": "TagString"
                                    }
                                },
                                "name": "narration",
                                "type": "TagCompound"
                            }
                        },
                        "name": "",
                        "type": "TagCompound"
                    },
                    "id": {
                        "raw_string": "paper:raw"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:say_command"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:team_msg_command_incoming"
                    }
                },
                {
                    "id": {
                        "raw_string": "minecraft:team_msg_command_outgoing"
                    }
                }
            ],
            "registry": {
                "raw_string": "minecraft:chat_type"
            }
        }
    """.trimIndent())
}
package io.github.daylightnebula.meld.server.registries

import io.github.daylightnebula.meld.server.meldJson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatRegistry(
    val type: String = "minecraft:chat_type",
    val value: List<ChatRegistryEntry> = listOf()
) {
    companion object {
        val default = meldJson.decodeFromString<ChatRegistry>("""
            {
                "type": "minecraft:chat_type",
                "value": [
                     {
                        "name":"minecraft:chat",
                        "id":1,
                        "element":{
                           "chat":{
                              "translation_key":"chat.type.text",
                              "parameters":[
                                 "sender",
                                 "content"
                              ]
                           },
                           "narration":{
                              "translation_key":"chat.type.text.narrate",
                              "parameters":[
                                 "sender",
                                 "content"
                              ]
                           }
                        }
                     }
                ]
            }
        """)
    }
}

@Serializable
data class ChatRegistryEntry(
    val name: String,
    val id: Int,
    val element: ChatRegistryElement
)

@Serializable
data class ChatRegistryElement(
    val chat: ChatRegistryElementParam,
    val narration: ChatRegistryElementParam
)

@Serializable
data class ChatRegistryElementParam(
    @SerialName("translation_key")
    val translationKey: String,
    val parameters: List<String>
)
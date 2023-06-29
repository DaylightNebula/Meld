package io.github.daylightnebula

import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.join.JoinEventListener
import io.github.daylightnebula.login.LoginBundle
import io.github.daylightnebula.networking.bedrock.BedrockNetworkController
import io.github.daylightnebula.networking.common.IConnection
import io.github.daylightnebula.networking.java.JavaNetworkController
import io.github.daylightnebula.world.biomes.BiomeManager
import io.github.daylightnebula.world.dimensions.DimensionTypeManager
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.jglrxavpok.hephaistos.parser.SNBTParser
import java.io.StringReader

object Meld {
    // config TODO move to config file
    // java specific
    val javaPort = 25565
    val javaProtocol = 763
    val javaVersion = "1.20.1"

    // bedrock specific
    val bedrockPort = 19132
    val bedrockPortv6 = 19133
    val reknetProtcol = 11
    val bedrockProtocol = 593
    val bedrockVersion = "1.20.10"

    // other stuffs
    val maxPlayers = 100
    val players = 0
    val description = "Hello World!"
    val favicon = "data:image/png;base64,<data>"
    val enforceSecureChat = false
    val previewsChat = false
    val viewDistance = 8
    val simDistance = 4
    val isFlatWorld = false
    val portalCooldown = 20
    val compressionThreshold = 256

    val dimensionTypeManager = DimensionTypeManager()
    val biomeManager = BiomeManager()
    val chatRegistry: NBTCompound = SNBTParser(StringReader(
    """
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
                 }    ]
        }
    """.trimIndent())).parse() as NBTCompound

    // connections
    val connections = mutableListOf<IConnection<*>>()
}

fun main() {
    println("Registering event listeners")
    EventBus.register(JoinEventListener())

    println("Registering packet bundles...")
    PacketHandler.register(LoginBundle())

    println("Starting...")

    // start the network controllers
    JavaNetworkController.start()
    BedrockNetworkController.start()

    println("Started")
}

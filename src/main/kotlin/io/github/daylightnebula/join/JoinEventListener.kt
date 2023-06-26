package io.github.daylightnebula.join

import io.github.daylightnebula.events.EventHandler
import io.github.daylightnebula.events.EventListener
import io.github.daylightnebula.login.LoginEvent
import io.github.daylightnebula.networking.bedrock.BedrockConnection
import io.github.daylightnebula.networking.java.JavaConnection
import io.ktor.server.plugins.*
import org.jglrxavpok.hephaistos.json.NBTGsonReader
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader

val emptyRegistryCodecFile = File("empty_registry_codec.json")
val emptyRegistryCodec = NBTGsonReader(FileReader(emptyRegistryCodecFile)).read(NBTCompound::class.java)

class JoinEventListener: EventListener {
    @EventHandler
    fun onLoginEvent(event: LoginEvent) {
        println("Login from ${event.connection}")
        when (event.connection) {
            is JavaConnection -> {
                event.connection.sendPacket(JavaJoinPacket())
            }
            is BedrockConnection -> {
                TODO()
            }
        }
    }
}
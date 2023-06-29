package io.github.daylightnebula.join

import io.github.daylightnebula.events.EventHandler
import io.github.daylightnebula.events.EventListener
import io.github.daylightnebula.login.LoginEvent
import io.github.daylightnebula.networking.bedrock.BedrockConnection
import io.github.daylightnebula.networking.java.JavaConnection
import net.minestom.server.network.packet.server.play.JoinGamePacket

class JoinEventListener: EventListener {
    @EventHandler
    fun onLoginEvent(event: LoginEvent) {
        println("Login from ${event.connection}")
        when (event.connection) {
            is JavaConnection -> {
                event.connection.sendPacket(JoinGamePacket())
            }
            is BedrockConnection -> {
                TODO()
            }
        }
    }
}
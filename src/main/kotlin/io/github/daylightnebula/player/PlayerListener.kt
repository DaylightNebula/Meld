package io.github.daylightnebula.player

import io.github.daylightnebula.events.EventHandler
import io.github.daylightnebula.events.EventListener
import io.github.daylightnebula.login.LoginEvent
import io.github.daylightnebula.networking.bedrock.BedrockConnection
import io.github.daylightnebula.networking.java.JavaConnection

class PlayerListener: EventListener {
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

//interface JoinEvent()
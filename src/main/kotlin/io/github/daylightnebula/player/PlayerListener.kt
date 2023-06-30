package io.github.daylightnebula.player

import io.github.daylightnebula.entities.JavaEntityStatusPacket
import io.github.daylightnebula.events.EventHandler
import io.github.daylightnebula.events.EventListener
import io.github.daylightnebula.login.LoginEvent
import io.github.daylightnebula.networking.bedrock.BedrockConnection
import io.github.daylightnebula.networking.java.JavaConnection
import io.github.daylightnebula.player.packets.JavaSetPlayerPositionPacket
import io.github.daylightnebula.player.packets.login.JavaAbilitiesPacket
import io.github.daylightnebula.player.packets.login.JavaFeatureFlagsPacket
import io.github.daylightnebula.player.packets.login.JavaJoinPacket

class PlayerListener: EventListener {
    // on login
    @EventHandler
    fun onLoginEvent(event: LoginEvent) {
        println("Login from ${event.connection}")
        // TODO difficulty packet + bedrock
        // TODO held item slot packet + bedrock

        // match login handler based on connection type
        when (event.connection) {
            is JavaConnection -> {
                val player = Player(event.connection)
                event.connection.sendPacket(JavaJoinPacket())
                event.connection.sendPacket(JavaFeatureFlagsPacket())
                event.connection.sendPacket(JavaAbilitiesPacket())
                event.connection.sendPacket(JavaEntityStatusPacket(player))
                // TODO player info packet https://wiki.vg/Protocol#Player_Info_Update
                // TODO update view distance packet
                // TODO simulation distance packet
                // TODO broadcast join event
            }
            is BedrockConnection -> {
                TODO()
            }
        }
    }
}

//interface JoinEvent()
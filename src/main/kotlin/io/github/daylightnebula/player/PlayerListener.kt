package io.github.daylightnebula.player

import io.github.daylightnebula.entities.JavaEntityStatusPacket
import io.github.daylightnebula.events.Event
import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.events.EventHandler
import io.github.daylightnebula.events.EventListener
import io.github.daylightnebula.login.LoginEvent
import io.github.daylightnebula.networking.bedrock.BedrockConnection
import io.github.daylightnebula.networking.java.JavaConnection
import io.github.daylightnebula.player.packets.JavaSetPlayerPositionPacket
import io.github.daylightnebula.player.packets.login.JavaAbilitiesPacket
import io.github.daylightnebula.player.packets.login.JavaFeatureFlagsPacket
import io.github.daylightnebula.player.packets.login.JavaJoinPacket
import org.cloudburstmc.protocol.bedrock.packet.LevelChunkPacket

class PlayerListener: EventListener {
    // on login
    @EventHandler
    fun onLoginEvent(event: LoginEvent) {
        println("Login from ${event.connection}")
        // TODO difficulty packet + bedrock
        // TODO held item slot packet + bedrock

        // create player and broadcast pre join
        val player = Player(event.connection)
        EventBus.callEvent(PreJoinEvent(player))

        // match login handler based on connection type
        when (event.connection) {
            // java connections
            is JavaConnection -> {
                event.connection.sendPacket(JavaJoinPacket())
                event.connection.sendPacket(JavaFeatureFlagsPacket())
                event.connection.sendPacket(JavaAbilitiesPacket())
                event.connection.sendPacket(JavaEntityStatusPacket(player))
                // TODO player info packet https://wiki.vg/Protocol#Player_Info_Update
            }

            // bedrock connections
            is BedrockConnection -> {
                TODO()
            }
        }

        // mark player joined
        player.joinSent = true

        // call join event
        EventBus.callEvent(JoinEvent(player))
    }
}

class PreJoinEvent(val player: Player): Event
class JoinEvent(val player: Player): Event
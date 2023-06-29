package io.github.daylightnebula.join

import io.github.daylightnebula.Meld
import io.github.daylightnebula.entities.EntityController
import io.github.daylightnebula.events.EventHandler
import io.github.daylightnebula.events.EventListener
import io.github.daylightnebula.login.LoginEvent
import io.github.daylightnebula.utils.DeathLocation
import io.github.daylightnebula.utils.GameMode
import io.github.daylightnebula.utils.Vec
import io.github.daylightnebula.networking.bedrock.BedrockConnection
import io.github.daylightnebula.networking.java.JavaConnection
import io.github.daylightnebula.world.dimensions.DimensionType
import net.minestom.server.network.packet.server.play.JoinGamePacket
import org.jglrxavpok.hephaistos.nbt.NBT
import java.util.List

class JoinEventListener: EventListener {
    val dimension = DimensionType.OVERWORLD

    @EventHandler
    fun onLoginEvent(event: LoginEvent) {
        println("Login from ${event.connection}")
        when (event.connection) {
            is JavaConnection -> {
                event.connection.sendPacket(JoinGamePacket(
                    EntityController.nextID(),
                    false,
                    GameMode.SURVIVAL,
                    null,
                    List.of(dimension.getName()!!.asString()),
                    NBT.Compound(mapOf(
                        "minecraft:chat_type" to Meld.chatRegistry,
                        "minecraft:dimension_type" to Meld.dimensionTypeManager.toNBT(),
                        "minecraft:worldgen/biome" to Meld.biomeManager.toNBT()
                    )),
                    dimension.toString(),
                    dimension.getName()!!.asString(),
                    0,
                    0,
                    Meld.viewDistance,
                    Meld.simDistance,
                    false,
                    true,
                    false,
                    true,
                    DeathLocation(dimension.getName()!!.asString(), Vec(0.0, 0.0, 0.0))
                ))
            }
            is BedrockConnection -> {
                TODO()
            }
        }
    }
}
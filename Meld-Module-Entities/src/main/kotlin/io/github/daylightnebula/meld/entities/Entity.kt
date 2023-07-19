package io.github.daylightnebula.meld.entities

import io.github.daylightnebula.meld.entities.packets.*
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import java.util.*

open class Entity(
    val uid: UUID = UUID.randomUUID(),
    val id: Int = EntityController.nextID(),
    val type: EntityType = EntityType.ARROW,
    var dimensionID: String = "overworld",
    startPosition: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    startVelocity: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    startRotation: Vector2f = Vector2f.ZERO
) {
    var position = startPosition
        private set

    open fun setPosition(newPosition: Vector3f) {
        // get change in position
        val change = newPosition.clone().sub(position)

        // send packets
        broadcastPositionUpdatesTo().forEach { connection ->
            when(connection) {
                is JavaConnection -> {
                    // send packet based on if change is greater than 8 blocks, teleport if greater than 8, otherwise just update position
                    if (change.length() > 8) connection.sendPacket(JavaTeleportEntityPacket(
                        id, newPosition, rotation, true
                    )) else connection.sendPacket(JavaUpdateEntityPositionPacket(
                        id, Vector3f.from(
                            ((newPosition.x * 32f) - (position.x * 32f)) * 128f,
                            ((newPosition.y * 32f) - (position.y * 32f)) * 128f,
                            ((newPosition.z * 32f) - (position.z * 32f)) * 128f,
                        ), true
                    ))
                }
                is BedrockConnection -> NeedsBedrock()
            }
        }

        // update position
        position = newPosition

        // broadcast event
        EventBus.callEvent(EntityMoveEvent(this, position))
    }

    open fun broadcastPositionUpdatesTo() = Meld.connections.toList()

    var velocity = startVelocity
        private set

    open fun setVelocity(velocity: Vector3f) {
        // broadcast changes
        val javaPacket = JavaSetEntityVelocityPacket(id, velocity)
        broadcastPositionUpdatesTo().forEach { connection ->
            when (connection) {
                is JavaConnection -> connection.sendPacket(javaPacket)
                is BedrockConnection -> NeedsBedrock()
            }
        }

        // update velocity
        this.velocity = velocity

        // call event
        EventBus.callEvent(EntityVelocityChangeEvent(this, velocity))
    }

    var rotation = startRotation
        private set

    open fun setRotation(rotation: Vector2f) {
        // broadcast changes
        val javaPacket = JavaUpdateEntityRotationPacket(id, rotation, true)
        broadcastPositionUpdatesTo().forEach { connection ->
            when(connection) {
                is JavaConnection -> connection.sendPacket(javaPacket)
                is BedrockConnection -> NeedsBedrock()
            }
        }

        // update rotation
        this.rotation = rotation

        // call event
        EventBus.callEvent(EntityRotateEvent(this, rotation))
    }

    open fun getSpawnJavaPackets(): List<JavaPacket> = listOf(JavaSpawnEntityPacket(this))
}

enum class EntityType(val mcID: Int, val identifier: String) {
    ALLAY(0, "minecraft:allay"),
    AREA_EFFECT_CLOUD(1, "minecraft:area_effect_cloud"),
    ARMOR_STAND(2, "minecraft:armor_stand"),
    ARROW(3, "minecraft:arrow"),
    AXOLOTL(4, "minecraft:axolotl"),
    PLAYER(122, "minecraft:player")
}

enum class EntityAnimation { SWING_ARM, TAKE_DAMAGE, LEAVE_BED, SWING_OFFHAND, CRITICAL_EFFECT, MAGICAL_CRITICAL_EFFECT }

data class EntityMoveEvent(val entity: Entity, val position: Vector3f): Event
data class EntityRotateEvent(val entity: Entity, val rotation: Vector2f): Event
data class EntityVelocityChangeEvent(val entity: Entity, val velocity: Vector3f): Event
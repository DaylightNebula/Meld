package io.github.daylightnebula.meld.entities

import io.github.daylightnebula.meld.entities.packets.*
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.extensions.toChunkPosition
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.thread

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

    init {
        thread { sleep(1); EventBus.callEvent(EntitySpawnEvent(this)) }
    }

    open fun setPosition(newPosition: Vector3f) {
//        println("Entity $this moved to $newPosition")
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

        // broadcast event
        EventBus.callEvent(EntityMoveEvent(this, position, newPosition))

        // update position
        position = newPosition
    }

    open fun broadcastPositionUpdatesTo() = Meld.connections.toList()

    var velocity = startVelocity
        private set

    open fun setVelocity(velocity: Vector3f) {
        // broadcast changes
        val javaPacket = JavaSetEntityVelocityPacket(id, velocity)
        broadcastPositionUpdatesTo().forEach { connection ->
            when (connection) {
                is JavaConnection -> {
                    connection.sendPacket(javaPacket)
                }
                is BedrockConnection -> NeedsBedrock()
            }
        }

        // call event
        EventBus.callEvent(EntityVelocityChangeEvent(this, this.velocity, velocity))

        // update velocity
        this.velocity = velocity
    }

    var rotation = startRotation
        private set

    open fun setRotation(rotation: Vector2f) {
        // broadcast changes
        val javaPackets = listOf(
            JavaUpdateHeadYawPacket(id, rotation.x),
            JavaUpdateEntityRotationPacket(id, rotation, true)
        )
        broadcastPositionUpdatesTo().forEach { connection ->
            when(connection) {
                is JavaConnection -> for (packet in javaPackets) connection.sendPacket(packet)
                is BedrockConnection -> NeedsBedrock()
            }
        }

        // call event
        EventBus.callEvent(EntityRotateEvent(this, this.rotation, rotation))

        // update rotation
        this.rotation = rotation
    }

    open fun getSpawnJavaPackets(): List<JavaPacket> = listOf(JavaSpawnEntityPacket(this))

    open fun despawn() {
        EventBus.callEvent(EntityDespawnEvent(this))
    }
}

enum class EntityAnimation { SWING_ARM, TAKE_DAMAGE, LEAVE_BED, SWING_OFFHAND, CRITICAL_EFFECT, MAGICAL_CRITICAL_EFFECT }

data class EntityMoveEvent(val entity: Entity, val oldPosition: Vector3f, val newPosition: Vector3f): Event
data class EntityRotateEvent(val entity: Entity, val oldRotation: Vector2f, val newRotation: Vector2f): Event
data class EntityVelocityChangeEvent(val entity: Entity, val oldVelocity: Vector3f, val velocity: Vector3f): Event
data class EntitySpawnEvent(val entity: Entity): Event
data class EntityDespawnEvent(val entity: Entity): Event
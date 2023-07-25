package io.github.daylightnebula.meld.entities

import io.github.daylightnebula.meld.entities.metadata.EntityMetadata
import io.github.daylightnebula.meld.entities.metadata.EntityMetadataObject
import io.github.daylightnebula.meld.entities.metadata.IEntityMetadataParent
import io.github.daylightnebula.meld.entities.metadata.entityMetadata
import io.github.daylightnebula.meld.entities.packets.*
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.events.CancellableEvent
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.common.IConnection
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
    val metadata: EntityMetadata = entityMetadata(),
    var dimensionID: String = "overworld",
    startPosition: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    startVelocity: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    startRotation: Vector2f = Vector2f.ZERO
): IEntityMetadataParent {

    init {
        thread { sleep(1); EventBus.callEvent(EntitySpawnEvent(this)) }
    }

    // position of the entity
    var position = startPosition
        private set
    open fun setPosition(newPosition: Vector3f) {
        // get change in position
        val change = newPosition.clone().sub(position)

        // send packets
        watchers.forEach { connection ->
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

    // velocity of the entity
    var velocity = startVelocity
        private set
    open fun setVelocity(velocity: Vector3f) {
        // broadcast changes
        val javaPacket = JavaSetEntityVelocityPacket(id, velocity)
        watchers.forEach { connection ->
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

    // rotation of the entity
    var rotation = startRotation
        private set
    open fun setRotation(rotation: Vector2f) {
        // broadcast changes
        val javaPackets = listOf(
            JavaUpdateHeadYawPacket(id, rotation.x),
            JavaUpdateEntityRotationPacket(id, rotation, true)
        )
        watchers.forEach { connection ->
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

    // watchers (those who receive updates about this entity)
    private val watchers = mutableListOf<IConnection<*>>()

    fun getWatchers(): List<IConnection<*>> = watchers

    fun addWatcher(conn: IConnection<*>) {
        // if connection does not pass filter, cancel
        if (!watcherFilter(conn)) return

        // add the watcher
        watchers.add(conn)

        // spawn the entity
        val javaPackets = getSpawnJavaPackets()
        when(conn) {
            is JavaConnection -> for (packet in javaPackets) conn.sendPacket(packet)
            is BedrockConnection -> NeedsBedrock()
        }
    }

    fun removeWatcher(conn: IConnection<*>) {
        // remove watcher
        watchers.remove(conn)

        // send despawn packet
        val javaPacket = JavaRemoveEntitiesPacket(listOf(id))
        when(conn) {
            is JavaConnection -> conn.sendPacket(javaPacket)
            is BedrockConnection -> NeedsBedrock()
        }
    }

    // overridable functions for spawn packets and a watcher filter
    open fun getSpawnJavaPackets(): List<JavaPacket> =
        listOf(JavaSpawnEntityPacket(this), JavaEntityMetadataPacket(id, metadata))
    open var watcherFilter: (connection: IConnection<*>) -> Boolean = { true }

    // handle animations
    fun playAnimation(animation: EntityAnimation) {
        // send out animation event
        val event = EntityPlayAnimationEvent(this, animation)
        EventBus.callEvent(event)

        // stop here if event cancelled
        if (event.cancelled) return

        // broadcast animation to all players
        val javaPacket = JavaEntityAnimationPacket(id, animation)
        getWatchers().forEach { watcher ->
            when(watcher) {
                is JavaConnection -> watcher.sendPacket(javaPacket)
            }
        }
    }

    // when metadata is changed, call event and broadcast changes
    override fun replaceAtIndex(index: Int, obj: EntityMetadataObject) {
        metadata.replaceAtIndex(index, obj)
        EventBus.callEvent(EntityMetadataUpdateEvent(this, metadata))
        val javaPacket = JavaEntityMetadataPacket(id, metadata)
        watchers.forEach {
            when (it) {
                is JavaConnection -> it.sendPacket(javaPacket)
                is BedrockConnection -> NeedsBedrock()
            }
        }
    }

    // function to despawn an entity
    open fun despawn() {
        // despawn event
        EventBus.callEvent(EntityDespawnEvent(this))

        // remove all watchers
        watchers.forEach { removeWatcher(it) }
    }
}

enum class EntityAnimation { SWING_ARM, TAKE_DAMAGE, LEAVE_BED, SWING_OFFHAND, CRITICAL_EFFECT, MAGICAL_CRITICAL_EFFECT }

data class EntityMoveEvent(val entity: Entity, val oldPosition: Vector3f, val newPosition: Vector3f): Event
data class EntityRotateEvent(val entity: Entity, val oldRotation: Vector2f, val newRotation: Vector2f): Event
data class EntityVelocityChangeEvent(val entity: Entity, val oldVelocity: Vector3f, val velocity: Vector3f): Event
data class EntitySpawnEvent(val entity: Entity): Event
data class EntityDespawnEvent(val entity: Entity): Event
data class EntityPlayAnimationEvent(val entity: Entity, var animation: EntityAnimation, override var cancelled: Boolean = false): CancellableEvent
data class EntityMetadataUpdateEvent(val entity: Entity, val metadata: EntityMetadata): Event
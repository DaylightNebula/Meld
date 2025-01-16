package io.github.daylightnebula.meld.entities

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import dev.romainguy.kotlin.math.length
import io.github.daylightnebula.meld.entities.metadata.EntityMetadata
import io.github.daylightnebula.meld.entities.metadata.EntityMetadataObject
import io.github.daylightnebula.meld.entities.metadata.IEntityMetadataParent
import io.github.daylightnebula.meld.entities.metadata.entityMetadata
import io.github.daylightnebula.meld.entities.packets.*
import io.github.daylightnebula.meld.server.events.CancellableEvent
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.thread
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
open class Entity(
    val uid: Uuid = Uuid.random(),
    val id: Int = EntityController.nextID(),
    val type: EntityType = EntityType.ARROW,
    val metadata: EntityMetadata = entityMetadata(),
    var dimensionID: String = "overworld",
    startPosition: Float3 = Float3(),
    startVelocity: Float3 = Float3(),
    startRotation: Float2 = Float2()
): IEntityMetadataParent {

    init {
        EventBus.callEvent(EntitySpawnEvent(this))
    }

    // position of the entity
    var position = startPosition
        private set
    open fun setPosition(newPosition: Float3) {
        // get change in position
        val change = newPosition - position

        // send packets
        watchers.forEach { connection ->
            when(connection) {
                is JavaConnection -> {
                    // send packet based on if change is greater than 8 blocks, teleport if greater than 8, otherwise just update position
                    if (length(change) > 8) connection.sendPacket(JavaTeleportEntityPacket(
                        id, newPosition, Float3(), rotation, true
                    )) else connection.sendPacket(JavaUpdateEntityPositionPacket(
                        id, Float3(
                            ((newPosition.x * 32f) - (position.x * 32f)) * 128f,
                            ((newPosition.y * 32f) - (position.y * 32f)) * 128f,
                            ((newPosition.z * 32f) - (position.z * 32f)) * 128f,
                        ), true
                    ))
                }
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
    open fun setVelocity(velocity: Float3) {
        // broadcast changes
        val javaPacket = JavaSetEntityVelocityPacket(id, velocity)
        watchers.forEach { connection ->
            when (connection) {
                is JavaConnection -> {
                    connection.sendPacket(javaPacket)
                }
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
    open fun setRotation(rotation: Float2) {
        // broadcast changes
        val javaPackets = listOf(
            JavaUpdateHeadYawPacket(id, rotation.x),
            JavaUpdateEntityRotationPacket(id, rotation, true)
        )
        watchers.forEach { connection ->
            when(connection) {
                is JavaConnection -> for (packet in javaPackets) connection.sendPacket(packet)
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
        }
    }

    fun removeWatcher(conn: IConnection<*>) {
        // remove watcher
        watchers.remove(conn)

        // send despawn packet
        val javaPacket = JavaRemoveEntitiesPacket(listOf(id))
        when(conn) {
            is JavaConnection -> conn.sendPacket(javaPacket)
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
    override fun replaceMetadataAtIndex(index: Int, obj: EntityMetadataObject<*>) {
        metadata.replaceMetadataAtIndex(index, obj)
        EventBus.callEvent(EntityMetadataUpdateEvent(this, metadata))
        val javaPacket = JavaEntityMetadataPacket(id, metadata)
        watchers.forEach {
            when (it) {
                is JavaConnection -> it.sendPacket(javaPacket)
            }
        }
    }

    override fun <T> getMetadataAtIndex(index: Int): EntityMetadataObject<T>? {
        return metadata.getMetadataAtIndex(index)
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

@OptIn(ExperimentalUuidApi::class)
data class EntityMoveEvent(val entity: Entity, val oldPosition: Float3, val newPosition: Float3): Event {
    companion object: Event.Data<EntityMoveEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(EntityMoveEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class EntityRotateEvent(val entity: Entity, val oldRotation: Float2, val newRotation: Float2): Event {
    companion object: Event.Data<EntityRotateEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(EntityRotateEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class EntityVelocityChangeEvent(val entity: Entity, val oldVelocity: Float3, val velocity: Float3): Event {
    companion object: Event.Data<EntityVelocityChangeEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(EntityVelocityChangeEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class EntitySpawnEvent(val entity: Entity): Event {
    companion object: Event.Data<EntitySpawnEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(EntitySpawnEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class EntityDespawnEvent(val entity: Entity): Event {
    companion object: Event.Data<EntityDespawnEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(EntityDespawnEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class EntityPlayAnimationEvent(val entity: Entity, var animation: EntityAnimation, override var cancelled: Boolean = false): CancellableEvent {
    companion object: Event.Data<EntityPlayAnimationEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(EntityPlayAnimationEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class EntityMetadataUpdateEvent(val entity: Entity, val metadata: EntityMetadata): Event {
    companion object: Event.Data<EntityMetadataUpdateEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(EntityMetadataUpdateEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

package io.github.daylightnebula.entities

import io.github.daylightnebula.entities.packets.JavaSpawnEntityPacket
import io.github.daylightnebula.networking.java.JavaPacket
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import java.util.*

abstract class Entity(
    val uid: UUID = UUID.randomUUID(),
    val id: Int = EntityController.nextID(),
    val type: EntityType = EntityType.ARROW,
    var dimensionID: String = "overworld",
    startPosition: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    startVelocity: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    startRotation: Vector2f = Vector2f.ZERO
) {
    // TODO set function and broadcast changes
    var position = startPosition
        private set

    // TODO set function and broadcast changes
    var velocity = startVelocity
        private set

    // TODO set function and broadcast changes
    var rotation = startRotation
        private set

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
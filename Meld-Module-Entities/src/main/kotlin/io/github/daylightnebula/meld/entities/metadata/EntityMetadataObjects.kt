package io.github.daylightnebula.meld.entities.metadata

import io.github.daylightnebula.meld.entities.enums.SnifferState
import io.github.daylightnebula.meld.server.utils.Direction
import io.github.daylightnebula.meld.server.utils.GlobalPosition
import io.github.daylightnebula.meld.server.utils.Pose
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i
import org.cloudburstmc.math.vector.Vector4f
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.json.JSONObject
import java.util.*

// all metadata objects listed in https://wiki.vg/Entity_metadata#Entity_Metadata_Format

fun metaByte(index: Int, value: Byte) = metaObject(index, 0) { it.writeByte(value) }
fun metaVarInt(index: Int, value: Int) = metaObject(index, 1) { it.writeVarInt(value) }
fun metaVarLong(index: Int, value: Long) = metaObject(index, 2) { it.writeVarLong(value) }
fun metaFloat(index: Int, value: Float) = metaObject(index, 3) { it.writeFloat(value) }
fun metaString(index: Int, value: String) = metaObject(index, 4) { it.writeString(value) }
fun metaChat(index: Int, value: JSONObject) = metaObject(index, 5) { it.writeString(value.toString(0)) }
fun metaOptChat(index: Int, value: JSONObject?) = metaObject(index, 6) {
    it.writeBoolean(value != null)
    if (value != null) it.writeString(value.toString(0))
}
// TODO fun metaSlot
fun metaBoolean(index: Int, value: Boolean) = metaObject(index, 8) { it.writeBoolean(value) }
fun metaRotation(index: Int, value: Vector3f) = metaObject(index, 9) {
    it.writeFloat(value.x)
    it.writeFloat(value.y)
    it.writeFloat(value.z)
}
fun metaBlockPosition(index: Int, value: Vector3i) = metaObject(index, 10) { it.writeBlockPosition(value) }
fun metaOptBlockPosition(index: Int, value: Vector3i?) = metaObject(index, 11) {
    it.writeBoolean(value != null)
    if (value != null) it.writeBlockPosition(value)
}
fun metaDirection(index: Int, direction: Direction) = metaObject(index, 12) { it.writeVarInt(direction.ordinal) }
fun metaOptUUID(index: Int, value: UUID?) = metaObject(index, 13) {
    it.writeBoolean(value != null)
    if (value != null) {
        it.writeLong(value.mostSignificantBits)
        it.writeLong(value.leastSignificantBits)
    }
}
fun metaBlockID(index: Int, blockID: Int) = metaObject(index, 14) { it.writeVarInt(blockID) }
fun metaOptBlockID(index: Int, blockID: Int?) = metaObject(index, 15) {
    it.writeBoolean(blockID != null)
    if (blockID != null) it.writeVarInt(blockID)
}
fun metaNBT(index: Int, value: NBTCompound) = metaObject(index, 16) { it.writeNBT(value) }
// TODO fun metaParticle
fun metaVillagerData(index: Int, type: Int, profession: Int, level: Int) = metaObject(index, 18) {
    it.writeVarInt(type)
    it.writeVarInt(profession)
    it.writeVarInt(level)
}
fun metaOptVarInt(index: Int, value: Int?) = metaObject(index, 19) {
    it.writeBoolean(value != null)
    if (value != null) it.writeVarInt(value)
}
fun metaPose(index: Int, value: Pose) = metaObject(index, 20) {
    it.writeVarInt(value.ordinal)
}
fun metaCatVariant(index: Int, variant: Int) = metaObject(index, 21) { it.writeVarInt(variant) }
fun metaFrogVariant(index: Int, variant: Int) = metaObject(index, 22) { it.writeVarInt(variant) }
fun metaOptGlobalPosition(index: Int, position: GlobalPosition?) = metaObject(index, 23) {
    it.writeBoolean(position != null)
    if (position != null) {
        it.writeString(position.dimension)
        it.writeBlockPosition(position.position)
    }
}
fun metaPaintingVariant(index: Int, variant: Int) = metaObject(index, 24) { it.writeVarInt(variant) }
fun metaSnifferState(index: Int, state: SnifferState) = metaObject(index, 25) { it.writeVarInt(state.ordinal) }
fun metaVec3(index: Int, value: Vector3f) = metaObject(index, 26) {
    it.writeFloat(value.x)
    it.writeFloat(value.y)
    it.writeFloat(value.z)
}
fun metaVec4(index: Int, value: Vector4f) = metaObject(index, 27) {
    it.writeFloat(value.x)
    it.writeFloat(value.y)
    it.writeFloat(value.z)
    it.writeFloat(value.w)
}
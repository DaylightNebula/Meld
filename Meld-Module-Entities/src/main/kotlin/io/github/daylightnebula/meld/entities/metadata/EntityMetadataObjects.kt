package io.github.daylightnebula.meld.entities.metadata

import io.github.daylightnebula.meld.entities.enums.SnifferState
import io.github.daylightnebula.meld.server.utils.Direction
import io.github.daylightnebula.meld.server.utils.GlobalPosition
import io.github.daylightnebula.meld.server.utils.ItemContainer
import io.github.daylightnebula.meld.server.utils.Pose
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i
import org.cloudburstmc.math.vector.Vector4f
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.json.JSONObject
import java.util.*

// all metadata objects listed in https://wiki.vg/Entity_metadata#Entity_Metadata_Format

fun metaByte(index: Int, value: Byte) = metaObject<Byte>(index, 0, value) { it.writeByte(value) }
fun metaVarInt(index: Int, value: Int) = metaObject<Int>(index, 1, value) { it.writeVarInt(value) }
fun metaVarLong(index: Int, value: Long) = metaObject<Long>(index, 2, value) { it.writeVarLong(value) }
fun metaFloat(index: Int, value: Float) = metaObject<Float>(index, 3, value) { it.writeFloat(value) }
fun metaString(index: Int, value: String) = metaObject<String>(index, 4, value) { it.writeString(value) }
fun metaChat(index: Int, value: JSONObject) = metaObject<JSONObject>(index, 5, value) { it.writeString(value.toString(0)) }
fun metaOptChat(index: Int, value: JSONObject?) = metaObject<JSONObject?>(index, 6, value) {
    it.writeBoolean(value != null)
    if (value != null) it.writeString(value.toString(0))
}
fun metaItem(index: Int, value: ItemContainer?) = metaObject<ItemContainer?>(index, 7, value) { it.writeItem(value) }
fun metaBoolean(index: Int, value: Boolean) = metaObject<Boolean>(index, 8, value) { it.writeBoolean(value) }
fun metaRotation(index: Int, value: Vector3f) = metaObject<Vector3f>(index, 9, value) {
    it.writeFloat(value.x)
    it.writeFloat(value.y)
    it.writeFloat(value.z)
}
fun metaBlockPosition(index: Int, value: Vector3i) = metaObject<Vector3i>(index, 10, value) { it.writeBlockPosition(value) }
fun metaOptBlockPosition(index: Int, value: Vector3i?) = metaObject<Vector3i?>(index, 11, value) {
    it.writeBoolean(value != null)
    if (value != null) it.writeBlockPosition(value)
}
fun metaDirection(index: Int, direction: Direction) = metaObject<Direction>(index, 12, direction) { it.writeVarInt(direction.ordinal) }
fun metaOptUUID(index: Int, value: UUID?) = metaObject<UUID?>(index, 13, value) {
    it.writeBoolean(value != null)
    if (value != null) {
        it.writeLong(value.mostSignificantBits)
        it.writeLong(value.leastSignificantBits)
    }
}
fun metaBlockID(index: Int, blockID: Int) = metaObject<Int>(index, 14, blockID) { it.writeVarInt(blockID) }
fun metaOptBlockID(index: Int, blockID: Int?) = metaObject<Int?>(index, 15, blockID) {
    it.writeBoolean(blockID != null)
    if (blockID != null) it.writeVarInt(blockID)
}
fun metaNBT(index: Int, value: NBTCompound) = metaObject<NBTCompound>(index, 16, value) { it.writeNBT(value) }
// TODO fun metaParticle
data class VillagerData(val type: Int, val profession: Int, val level: Int)
fun metaVillagerData(index: Int, data: VillagerData) = metaObject<VillagerData>(index, 18, data) {
    it.writeVarInt(data.type)
    it.writeVarInt(data.profession)
    it.writeVarInt(data.level)
}
fun metaOptVarInt(index: Int, value: Int?) = metaObject<Int?>(index, 19, value) {
    it.writeBoolean(value != null)
    if (value != null) it.writeVarInt(value)
}
fun metaPose(index: Int, value: Pose) = metaObject<Pose>(index, 20, value) {
    it.writeVarInt(value.ordinal)
}
fun metaCatVariant(index: Int, variant: Int) = metaObject<Int>(index, 21, variant) { it.writeVarInt(variant) }
fun metaFrogVariant(index: Int, variant: Int) = metaObject<Int>(index, 22, variant) { it.writeVarInt(variant) }
fun metaOptGlobalPosition(index: Int, position: GlobalPosition?) = metaObject<GlobalPosition?>(index, 23, position) {
    it.writeBoolean(position != null)
    if (position != null) {
        it.writeString(position.dimension)
        it.writeBlockPosition(position.position)
    }
}
fun metaPaintingVariant(index: Int, variant: Int) = metaObject<Int>(index, 24, variant) { it.writeVarInt(variant) }
fun metaSnifferState(index: Int, state: SnifferState) = metaObject<SnifferState>(index, 25, state) { it.writeVarInt(state.ordinal) }
fun metaVec3(index: Int, value: Vector3f) = metaObject<Vector3f>(index, 26, value) {
    it.writeFloat(value.x)
    it.writeFloat(value.y)
    it.writeFloat(value.z)
}
fun metaVec4(index: Int, value: Vector4f) = metaObject<Vector4f>(index, 27, value) {
    it.writeFloat(value.x)
    it.writeFloat(value.y)
    it.writeFloat(value.z)
    it.writeFloat(value.w)
}
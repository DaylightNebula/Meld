package io.github.daylightnebula.meld.inventories.packets

import io.github.daylightnebula.meld.server.utils.InventoryType
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class JavaOpenInventoryPacket(
    var windowID: Int = 0,
    var windowType: InventoryType = InventoryType.GENERIC_9x1,
    var title: JsonObject = JsonObject(mapOf("text" to JsonPrimitive("")))
): JavaPacket {
    companion object: JavaPacket.Creator<JavaOpenInventoryPacket> {
        override val INCOMING_ID: Int = 0x31
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaOpenInventoryPacket()
    }

    override val OUTGOING_ID: Int = 0x31
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(windowID)
        writer.writeVarInt(windowType.id)
        writer.writeJSON(title)
    }
}
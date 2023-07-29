package io.github.daylightnebula.meld.inventories.packets

import io.github.daylightnebula.meld.inventories.utils.InventoryType
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import org.json.JSONObject

class JavaOpenInventoryPacket(
    var windowID: Int = 0,
    var windowType: InventoryType = InventoryType.GENERIC_9x1,
    var title: JSONObject = JSONObject().put("text", "")
): JavaPacket {
    override val id: Int = 0x30
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(windowID)
        writer.writeVarInt(windowType.id)
        writer.writeJSON(title)
    }
}
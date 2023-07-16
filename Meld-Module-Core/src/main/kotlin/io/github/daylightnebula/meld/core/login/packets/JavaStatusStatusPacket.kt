package io.github.daylightnebula.meld.core.login.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import org.json.JSONObject

class JavaStatusStatusPacket(var json: JSONObject = JSONObject()): JavaPacket {
    companion object {
        val ID = 0x00
        val TYPE = JavaConnectionState.STATUS
    }

    override val id: Int = ID
    override fun decode(reader: AbstractReader) {} // input status packet have no payload
    override fun encode(writer: ByteWriter) {
        writer.writeJSON(json)
    }
}
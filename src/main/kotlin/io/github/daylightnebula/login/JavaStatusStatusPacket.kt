package io.github.daylightnebula.login

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaConnectionState
import io.github.daylightnebula.networking.java.JavaPacket
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
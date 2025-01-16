package io.github.daylightnebula.meld.login.packets.login

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import kotlinx.serialization.json.JsonObject

class JavaStatusStatusPacket(var json: JsonObject = JsonObject(mapOf())): JavaPacket {
    companion object: JavaPacket.Creator<JavaStatusStatusPacket> {
        override val INCOMING_ID = 0x00
        override val STATE = JavaConnectionState.STATUS
        override fun create() = JavaStatusStatusPacket()
    }

    override val OUTGOING_ID: Int = INCOMING_ID
    override fun decode(reader: AbstractReader) {} // input status packet have no payload
    override fun encode(writer: ByteWriter) {
        writer.writeJSON(json)
    }
}
package io.github.daylightnebula.meld.server.networking.java.packets

import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.ksp.data.RegisterPacket
import io.github.daylightnebula.meld.server.AnonymousNBT
import io.github.daylightnebula.meld.server.BoolCodec
import io.github.daylightnebula.meld.server.LongCodec
import io.github.daylightnebula.meld.server.StdByteArrayCodec
import io.github.daylightnebula.meld.server.StringCodec
import io.github.daylightnebula.meld.server.UuidCodec
import io.github.daylightnebula.meld.server.VarIntCodec
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import net.benwoodworth.knbt.NbtTag
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

// todo move decoders to creator sub-interface of Action
// todo finish encode and decode functions in root

@OptIn(ExperimentalUuidApi::class)
@RegisterPacket("packet_player_info")
class JavaClientPlayPlayerInfo(
    val data: List<Data>
): JavaPacket {
    override val ID = 0x40
    override val STATE = JavaConnectionState.IN_GAME
    override fun encode() = TODO()

    companion object: JavaPacket.Creator<JavaClientPlayPlayerInfo> {
        override val ID = 0x40
        override val STATE = JavaConnectionState.IN_GAME
        override fun decode(reader: IReader) = TODO()
    }

    // Define data for each user to be modified
    class Data(
        val uuid: Uuid,
        val actions: List<Action>
    )

    // Interface for each action that may be applied to each player
    interface Action {
        val mask: Int
        fun encode(): ByteArray
        fun decode(reader: IReader): Action
    }

    // Defines an action where a player is added to the server
    class AddPlayerAction(
        val name: String,
        val properties: List<Property>
    ): Action {
        override val mask: Int = 0x01
        override fun encode() =
            StringCodec.encode(name) +
            VarIntCodec.encode(properties.size) +
            properties.map {
                StringCodec.encode(it.name) +
                StringCodec.encode(it.value) +
                if (it.signature != null) byteArrayOf(0x01) + StringCodec.encode(it.signature) else byteArrayOf(0x00)
            }.fold(byteArrayOf()) { acc, bytes -> acc + bytes }

        override fun decode(reader: IReader) = AddPlayerAction(
            name = StringCodec.decode(reader),
            properties = (0 until VarIntCodec.decode(reader)).map {
                Property(
                    name = StringCodec.decode(reader),
                    value = StringCodec.decode(reader),
                    signature = if (reader.read() > 0) StringCodec.decode(reader) else null
                )
            }
        )

        // Define a player property
        data class Property(val name: String, val value: String, val signature: String? = null)
    }

    // Defines the chat initialization action
    class InitializeChatAction(
        val sessionID: Uuid,
        val publicKey: Long,
        val encodedPublicKey: ByteArray,
        val publicKeySignature: ByteArray
    ): Action {
        override val mask: Int = 0x02

        override fun encode() = UuidCodec.encode(sessionID) + LongCodec.encode(publicKey) +
                StdByteArrayCodec.encode(encodedPublicKey) +
                StdByteArrayCodec.encode(publicKeySignature)

        override fun decode(reader: IReader) = InitializeChatAction(
            sessionID = UuidCodec.decode(reader),
            publicKey = LongCodec.decode(reader),
            encodedPublicKey = StdByteArrayCodec.decode(reader),
            publicKeySignature = StdByteArrayCodec.decode(reader)
        )
    }

    // Defines the game mode of the defined player
    class UpdateGameModeAction(val gameMode: Int): Action {
        override val mask: Int = 0x04
        override fun encode() = VarIntCodec.encode(gameMode)
        override fun decode(reader: IReader) = UpdateGameModeAction(VarIntCodec.decode(reader))
    }

    // Define whether a player is listed on the tablist
    class UpdateListedAction(val listed: Boolean): Action {
        override val mask: Int = 0x08
        override fun encode() = BoolCodec.encode(listed)
        override fun decode(reader: IReader) = UpdateListedAction(BoolCodec.decode(reader))
    }

    class PingAction(val ping: Int): Action {
        override val mask: Int = 0x10
        override fun encode() = VarIntCodec.encode(ping)
        override fun decode(reader: IReader) = PingAction(VarIntCodec.decode(reader))
    }

    class DisplayNameAction(val displayName: NbtTag): Action {
        override val mask: Int = 0x20
        override fun encode() = AnonymousNBT.encode(displayName)
        override fun decode(reader: IReader) = DisplayNameAction(AnonymousNBT.decode(reader))
    }

    class PriorityAction(val priority: Int): Action {
        override val mask: Int = 0x40
        override fun encode() = VarIntCodec.encode(priority)
        override fun decode(reader: IReader) = PriorityAction(VarIntCodec.decode(reader))
    }

    class VisibleAction(val visible: Boolean): Action {
        override val mask: Int = 0x80
        override fun encode() = BoolCodec.encode(visible)
        override fun decode(reader: IReader) = VisibleAction(BoolCodec.decode(reader))
    }
}
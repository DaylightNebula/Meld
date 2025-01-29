package io.github.daylightnebula.meld.server.networking.java.packets

import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.ksp.data.RegisterPacket
import io.github.daylightnebula.meld.server.*
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import net.benwoodworth.knbt.NbtTag
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@RegisterPacket("packet_player_info")
class JavaClientPlayPlayerInfo(
    val data: List<Data>
): JavaPacket {
    override val ID = 0x40
    override val STATE = JavaConnectionState.IN_GAME
    override fun encode(): ByteArray {
        // compile bit flags by looping through all actions in all data
        var flags: UByte = 0u
        for (data in data)
            for (action in data.actions)
                if (flags and action.mask == 0u.toUByte())
                    flags = flags or action.mask

        // compile final byte array by encoding every data
        return UByteCodec.encode(flags) +
                VarIntCodec.encode(data.size) +
                data.map(Data::encode).fold(byteArrayOf()) { a, b -> a + b }
    }

    companion object: JavaPacket.Creator<JavaClientPlayPlayerInfo> {
        override val ID = 0x40
        override val STATE = JavaConnectionState.IN_GAME
        override fun decode(reader: IReader): JavaClientPlayPlayerInfo {
            val flags = UByteCodec.decode(reader)
            val numEntries = VarIntCodec.decode(reader)
            return JavaClientPlayPlayerInfo(
                data = (0 until numEntries).map { Data.decode(reader, flags) }
            )
        }
    }

    // Define data for each user to be modified
    class Data(
        val uuid: Uuid,
        val actions: List<Action>
    ) {
        fun encode() = UuidCodec.encode(uuid) + actions.map(Action::encode).fold(byteArrayOf()) { a, b -> a + b }

        companion object {
            fun decode(reader: IReader, flags: UByte): Data {
                // setup
                val uuid = UuidCodec.decode(reader)
                val actions = mutableListOf<Action>()

                // decode actions
                AddPlayerAction.checkedDecode(actions, reader, flags)
                InitializeChatAction.checkedDecode(actions, reader, flags)
                UpdateGameModeAction.checkedDecode(actions, reader, flags)
                UpdateListedAction.checkedDecode(actions, reader, flags)
                UpdateLatencyAction.checkedDecode(actions, reader, flags)
                UpdateDisplayNameAction.checkedDecode(actions, reader, flags)
                UpdatePriorityAction.checkedDecode(actions, reader, flags)
                UpdateHatVisibilityAction.checkedDecode(actions, reader, flags)

                // build final data
                return Data(uuid, actions)
            }
        }
    }

    // Interface for each action that may be applied to each player
    interface Action {
        val mask: UByte
        fun encode(): ByteArray
    }

    interface ActionCreator {
        val mask: UByte
        fun decode(reader: IReader): Action

        fun checkedDecode(actions: MutableList<Action>, reader: IReader, flags: UByte): Boolean =
            if (flags and mask > 0u) actions.add(decode(reader))
            else false
    }

    // Defines an action where a player is added to the server
    class AddPlayerAction(
        val name: String,
        val properties: List<Property>
    ): Action {
        data class Property(val name: String, val value: String, val signature: String? = null)

        override val mask: UByte = 1u
        override fun encode() =
            StringCodec.encode(name) +
            VarIntCodec.encode(properties.size) +
            properties.map {
                StringCodec.encode(it.name) +
                StringCodec.encode(it.value) +
                if (it.signature != null) byteArrayOf(0x01) + StringCodec.encode(it.signature) else byteArrayOf(0x00)
            }.fold(byteArrayOf()) { acc, bytes -> acc + bytes }

        companion object: ActionCreator {
            override val mask: UByte = 1u
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
        }
    }

    // Defines the chat initialization action
    class InitializeChatAction(
        val sessionID: Uuid,
        val publicKey: Long,
        val encodedPublicKey: ByteArray,
        val publicKeySignature: ByteArray
    ): Action {
        override val mask: UByte = 2u

        override fun encode() = UuidCodec.encode(sessionID) + LongCodec.encode(publicKey) +
                StdByteArrayCodec.encode(encodedPublicKey) +
                StdByteArrayCodec.encode(publicKeySignature)

        companion object: ActionCreator {
            override val mask: UByte = 2u

            override fun decode(reader: IReader) = InitializeChatAction(
                sessionID = UuidCodec.decode(reader),
                publicKey = LongCodec.decode(reader),
                encodedPublicKey = StdByteArrayCodec.decode(reader),
                publicKeySignature = StdByteArrayCodec.decode(reader)
            )
        }
    }

    // Defines the game mode of the defined player
    class UpdateGameModeAction(val gameMode: Int): Action {
        override val mask: UByte = 4u
        override fun encode() = VarIntCodec.encode(gameMode)

        companion object: ActionCreator {
            override val mask: UByte = 4u
            override fun decode(reader: IReader) = UpdateGameModeAction(VarIntCodec.decode(reader))
        }
    }

    // Define whether a player is listed on the tablist
    class UpdateListedAction(val listed: Boolean): Action {
        override val mask: UByte = 8u
        override fun encode() = BoolCodec.encode(listed)

        companion object: ActionCreator {
            override val mask: UByte = 8u
            override fun decode(reader: IReader) = UpdateListedAction(BoolCodec.decode(reader))
        }
    }

    class UpdateLatencyAction(val ping: Int): Action {
        override val mask: UByte = 16u
        override fun encode() = VarIntCodec.encode(ping)

        companion object: ActionCreator {
            override val mask: UByte = 16u
            override fun decode(reader: IReader) = UpdateLatencyAction(VarIntCodec.decode(reader))
        }
    }

    class UpdateDisplayNameAction(val displayName: NbtTag): Action {
        override val mask: UByte = 32u
        override fun encode() = AnonymousNBT.encode(displayName)

        companion object: ActionCreator {
            override val mask: UByte = 32u
            override fun decode(reader: IReader) = UpdateDisplayNameAction(AnonymousNBT.decode(reader))
        }
    }

    class UpdatePriorityAction(val priority: Int): Action {
        override val mask: UByte = 64u
        override fun encode() = VarIntCodec.encode(priority)

        companion object: ActionCreator {
            override val mask: UByte = 64u
            override fun decode(reader: IReader) = UpdatePriorityAction(VarIntCodec.decode(reader))
        }
    }

    class UpdateHatVisibilityAction(val visible: Boolean): Action {
        override val mask: UByte = 128u
        override fun encode() = BoolCodec.encode(visible)

        companion object: ActionCreator {
            override val mask: UByte = 128u
            override fun decode(reader: IReader) = UpdateHatVisibilityAction(BoolCodec.decode(reader))
        }
    }
}
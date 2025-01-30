package io.github.daylightnebula.meld.server.utils

import io.github.daylightnebula.meld.ksp.data.Codec
import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.ksp.data.RegisterCodec
import io.github.daylightnebula.meld.server.VarIntCodec

interface Slot {
    class Empty: Slot
    class Populated(
        val count: Int,
        val container: ItemContainer
    ): Slot
}

data class ItemContainer(
    val id: Int
    // todo properties
)

@RegisterCodec("Slot", Slot::class)
object SlotCodec: Codec<Slot> {
    override fun encode(data: Slot) = when(data) {
        is Slot.Empty -> VarIntCodec.encode(0)
        is Slot.Populated -> VarIntCodec.encode(data.count) + VarIntCodec.encode(data.container.id) + VarIntCodec.encode(0) + VarIntCodec.encode(0)
        else -> throw IllegalStateException("Could not encode slot $data")
    }

    override fun decode(reader: IReader): Slot {
        // get length and return empty if length is 0
        val length = VarIntCodec.decode(reader)
        if (length == 0) return Slot.Empty()

        // decode container
        return Slot.Populated(
            count = length,
            container = ItemContainer(
                id = VarIntCodec.decode(reader)
            )
        )
    }
}

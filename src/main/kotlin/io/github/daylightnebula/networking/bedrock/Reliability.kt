package io.github.daylightnebula.networking.bedrock

enum class Reliability(val sendAck: Boolean) {
   UNRELIABLE(false), UNRELIABLE_SEQUENCED(false),
   RELIABLE(true), RELIABLE_ORDERED(true), RELIABLE_SEQUENCED(true),
   UNRELIABLE_WITH_ACK_RECEIPT(false), RELIABLE_WITH_ACK_RECEIPT(true), RELIABLE_ORDERED_WITH_ACK_RECEIPT(true);

   fun toRaw() = ordinal shl RAW_SHIFT_SIZE
   fun toByte() = ordinal.toByte()

   companion object {

      /**
       * This is how many bits we have to shift to convert between the raw byte & ordinal values.
       */
      private const val RAW_SHIFT_SIZE = 5

      fun from(value: Int): Reliability = values().first { it.ordinal == value }
      fun fromRaw(value: Int): Reliability = from(value ushr RAW_SHIFT_SIZE)

   }
}
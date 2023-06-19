package io.github.daylightnebula.networking.bedrock

object BedrockMagic {
    val bytes = byteArrayOf(
        0x00.toByte(),
        0xff.toByte(),
        0xff.toByte(),
        0x00.toByte(),
        0xfe.toByte(),
        0xfe.toByte(),
        0xfe.toByte(),
        0xfe.toByte(),
        0xfd.toByte(),
        0xfd.toByte(),
        0xfd.toByte(),
        0xfd.toByte(),
        0x12.toByte(),
        0x34.toByte(),
        0x56.toByte(),
        0x78.toByte()
    )

    val size get() = bytes.size
    fun verify(bytes: ByteArray): Boolean = bytes.contentEquals(bytes)
    override fun toString(): String = "Magic()"
}
package io.github.daylightnebula.meld.server.extensions

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import kotlin.math.floor

fun Float3.toChunkPosition(): Float2 =
    Float2(floor(x.dec16IfNegative() / 16), floor(z.dec16IfNegative() / 16))

fun Int.dec16IfNegative(): Int { return if (this < 0) this - 15 else this }
fun Float.dec16IfNegative(): Float { return if (this < 0) this - 15f else this }

fun Int.inc16IfNegative(): Int { return if (this < 0) this + 16 else this }
fun Float.inc16IfNegative(): Float { return if (this < 0) this + 16f else this }

fun Int.toSectionID(): Int = (this + 64) / 16
fun Float.toSectionID(): Int = floor(this).toInt().toSectionID()

fun Float.toAngleByte(): Byte = (this.coerceIn(-180f, 180f) * 256f / 360f).toInt().toByte()
fun Float.toVelocityStep(): Short = (this * 8000f).toInt().toShort()
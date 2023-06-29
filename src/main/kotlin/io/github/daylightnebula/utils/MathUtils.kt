package io.github.daylightnebula.utils


object MathUtils {
    fun square(num: Int): Int {
        return num * num
    }

    fun square(num: Float): Float {
        return num * num
    }

    fun square(num: Double): Double {
        return num * num
    }

    fun round(value: Double, places: Int): Double {
        var value = value
        require(places >= 0)
        val factor = Math.pow(10.0, places.toDouble()).toLong()
        value = value * factor
        val tmp = Math.round(value)
        return tmp.toDouble() / factor
    }

    fun round(value: Float, places: Int): Float {
        var value = value
        require(places >= 0)
        val factor = Math.pow(10.0, places.toDouble()).toLong()
        value = value * factor
        val tmp = Math.round(value).toLong()
        return tmp.toFloat() / factor
    }

    fun getHorizontalDirection(yawInDegrees: Float): Direction {
        // +45f gives a 90° angle for the direction (-1° and 1° are towards the same direction)
        var directionIndex = Math.floor(((yawInDegrees + 45f) / 90f).toDouble()).toInt()
        if (directionIndex < 0) {
            directionIndex = -directionIndex % Direction.HORIZONTAL.size
            directionIndex = Direction.HORIZONTAL.size - directionIndex
        }
        directionIndex %= Direction.HORIZONTAL.size
        return Direction.HORIZONTAL[directionIndex]
    }

    fun isBetween(number: Byte, min: Byte, max: Byte): Boolean {
        return number >= min && number <= max
    }

    fun isBetween(number: Int, min: Int, max: Int): Boolean {
        return number >= min && number <= max
    }

    fun isBetween(number: Double, min: Double, max: Double): Boolean {
        return number >= min && number <= max
    }

    fun isBetween(number: Float, min: Float, max: Float): Boolean {
        return number >= min && number <= max
    }

    fun isBetweenUnordered(number: Double, compare1: Double, compare2: Double): Boolean {
        return if (compare1 > compare2) {
            isBetween(number, compare2, compare1)
        } else {
            isBetween(number, compare1, compare2)
        }
    }

    fun isBetweenUnordered(number: Float, compare1: Float, compare2: Float): Boolean {
        return if (compare1 > compare2) {
            isBetween(number, compare2, compare1)
        } else {
            isBetween(number, compare1, compare2)
        }
    }

    fun clamp(value: Int, min: Int, max: Int): Int {
        return Math.min(Math.max(value, min), max)
    }

    fun clamp(value: Float, min: Float, max: Float): Float {
        return Math.min(Math.max(value, min), max)
    }

    fun clamp(value: Double, min: Double, max: Double): Double {
        return Math.min(Math.max(value, min), max)
    }

    fun mod(a: Double, b: Double): Double {
        return (a % b + b) % b
    }

//    fun bitsToRepresent(n: Int): Int {
//        Check.argCondition(n < 1, "n must be greater than 0")
//        return Integer.SIZE - Integer.numberOfLeadingZeros(n)
//    }
}


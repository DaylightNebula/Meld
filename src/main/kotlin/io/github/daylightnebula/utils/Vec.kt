package io.github.daylightnebula.utils

import io.github.daylightnebula.utils.Vec.Interpolation
import io.github.daylightnebula.utils.Vec.Operator
import org.jetbrains.annotations.Contract
import java.util.function.DoubleUnaryOperator


data class Vec(val x: Double, val y: Double, val z: Double) : Point {
    override fun x(): Double {
        return x
    }

    override fun y(): Double {
        return y
    }

    override fun z(): Double {
        return z
    }

    /**
     * Creates a new vec with the [x;z] coordinates set. Y is set to 0.
     *
     * @param x the X coordinate
     * @param z the Z coordinate
     */
    constructor(x: Double, z: Double) : this(x, 0.0, z)

    /**
     * Creates a vec with all 3 coordinates sharing the same value.
     *
     * @param value the coordinates
     */
    constructor(value: Double) : this(value, value, value)

    /**
     * Creates a new point with coordinated depending on `this`.
     *
     * @param operator the operator
     * @return the created point
     */
    @Contract(pure = true)
    fun apply(operator: Operator): Vec {
        return operator.apply(x, y, z)
    }

    @Contract(pure = true)
    override fun withX(operator: DoubleUnaryOperator): Vec {
        return Vec(operator.applyAsDouble(x), y, z)
    }

    @Contract(pure = true)
    override fun withX(x: Double): Vec {
        return Vec(x, y, z)
    }

    @Contract(pure = true)
    override fun withY(operator: DoubleUnaryOperator): Vec {
        return Vec(x, operator.applyAsDouble(y), z)
    }

    @Contract(pure = true)
    override fun withY(y: Double): Vec {
        return Vec(x, y, z)
    }

    @Contract(pure = true)
    override fun withZ(operator: DoubleUnaryOperator): Vec {
        return Vec(x, y, operator.applyAsDouble(z))
    }

    @Contract(pure = true)
    override fun withZ(z: Double): Vec {
        return Vec(x, y, z)
    }

    override fun add(x: Double, y: Double, z: Double): Vec {
        return Vec(this.x + x, this.y + y, this.z + z)
    }

    override fun add(point: Point): Vec {
        return add(point.x(), point.y(), point.z())
    }

    override fun add(value: Double): Vec {
        return add(value, value, value)
    }

    override fun sub(x: Double, y: Double, z: Double): Vec {
        return Vec(this.x - x, this.y - y, this.z - z)
    }

    override fun sub(point: Point): Vec {
        return sub(point.x(), point.y(), point.z())
    }

    override fun sub(value: Double): Vec {
        return sub(value, value, value)
    }

    override fun mul(x: Double, y: Double, z: Double): Vec {
        return Vec(this.x * x, this.y * y, this.z * z)
    }

    override fun mul(point: Point): Vec {
        return mul(point.x(), point.y(), point.z())
    }

    override fun mul(value: Double): Vec {
        return mul(value, value, value)
    }

    override fun div(x: Double, y: Double, z: Double): Vec {
        return Vec(this.x / x, this.y / y, this.z / z)
    }

    override fun div(point: Point): Vec {
        return div(point.x(), point.y(), point.z())
    }

    override fun div(value: Double): Vec {
        return div(value, value, value)
    }

    override fun relative(face: BlockFace): Vec {
        return super.relative(face) as Vec
    }

    @Contract(pure = true)
    fun neg(): Vec {
        return Vec(-x, -y, -z)
    }

    @Contract(pure = true)
    fun abs(): Vec {
        return Vec(Math.abs(x), Math.abs(y), Math.abs(z))
    }

    @Contract(pure = true)
    fun min(point: Point): Vec {
        return Vec(Math.min(x, point.x()), Math.min(y, point.y()), Math.min(z, point.z()))
    }

    @Contract(pure = true)
    fun min(value: Double): Vec {
        return Vec(Math.min(x, value), Math.min(y, value), Math.min(z, value))
    }

    @Contract(pure = true)
    fun max(point: Point): Vec {
        return Vec(Math.max(x, point.x()), Math.max(y, point.y()), Math.max(z, point.z()))
    }

    @Contract(pure = true)
    fun max(value: Double): Vec {
        return Vec(Math.max(x, value), Math.max(y, value), Math.max(z, value))
    }

    @Contract(pure = true)
    fun asPosition(): Pos {
        return Pos(x, y, z)
    }

    /**
     * Gets the magnitude of the vector squared.
     *
     * @return the magnitude
     */
    @Contract(pure = true)
    fun lengthSquared(): Double {
        return MathUtils.square(x) + MathUtils.square(y) + MathUtils.square(z)
    }

    /**
     * Gets the magnitude of the vector, defined as sqrt(x^2+y^2+z^2). The
     * value of this method is not cached and uses a costly square-root
     * function, so do not repeatedly call this method to get the vector's
     * magnitude. NaN will be returned if the inner result of the sqrt()
     * function overflows, which will be caused if the length is too long.
     *
     * @return the magnitude
     */
    @Contract(pure = true)
    fun length(): Double {
        return Math.sqrt(lengthSquared())
    }

    /**
     * Converts this vector to a unit vector (a vector with length of 1).
     *
     * @return the same vector
     */
    @Contract(pure = true)
    fun normalize(): Vec {
        val length = length()
        return Vec(x / length, y / length, z / length)
    }

    val isNormalized: Boolean
        /**
         * Returns if a vector is normalized
         *
         * @return whether the vector is normalised
         */
        get() = Math.abs(lengthSquared() - 1) < EPSILON

    /**
     * Gets the angle between this vector and another in radians.
     *
     * @param vec the other vector
     * @return angle in radians
     */
    @Contract(pure = true)
    fun angle(vec: Vec): Double {
        val dot = MathUtils.clamp(dot(vec) / (length() * vec.length()), -1.0, 1.0)
        return Math.acos(dot)
    }

    /**
     * Calculates the dot product of this vector with another. The dot product
     * is defined as x1*x2+y1*y2+z1*z2. The returned value is a scalar.
     *
     * @param vec the other vector
     * @return dot product
     */
    @Contract(pure = true)
    fun dot(vec: Vec): Double {
        return x * vec.x + y * vec.y + z * vec.z
    }

    /**
     * Calculates the cross product of this vector with another. The cross
     * product is defined as:
     *
     *  * x = y1 * z2 - y2 * z1
     *  * y = z1 * x2 - z2 * x1
     *  * z = x1 * y2 - x2 * y1
     *
     *
     * @param o the other vector
     * @return the same vector
     */
    @Contract(pure = true)
    fun cross(o: Vec): Vec {
        return Vec(
            y * o.z - o.y * z,
            z * o.x - o.z * x,
            x * o.y - o.x * y
        )
    }

    /**
     * Rotates the vector around the x-axis.
     *
     *
     * This piece of math is based on the standard rotation matrix for vectors
     * in three-dimensional space. This matrix can be found here:
     * [Rotation
 * Matrix](https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations).
     *
     * @param angle the angle to rotate the vector about. This angle is passed
     * in radians
     * @return a new, rotated vector
     */
    @Contract(pure = true)
    fun rotateAroundX(angle: Double): Vec {
        val angleCos = Math.cos(angle)
        val angleSin = Math.sin(angle)
        val newY = angleCos * y - angleSin * z
        val newZ = angleSin * y + angleCos * z
        return Vec(x, newY, newZ)
    }

    /**
     * Rotates the vector around the y-axis.
     *
     *
     * This piece of math is based on the standard rotation matrix for vectors
     * in three-dimensional space. This matrix can be found here:
     * [Rotation
 * Matrix](https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations).
     *
     * @param angle the angle to rotate the vector about. This angle is passed
     * in radians
     * @return a new, rotated vector
     */
    @Contract(pure = true)
    fun rotateAroundY(angle: Double): Vec {
        val angleCos = Math.cos(angle)
        val angleSin = Math.sin(angle)
        val newX = angleCos * x + angleSin * z
        val newZ = -angleSin * x + angleCos * z
        return Vec(newX, y, newZ)
    }

    /**
     * Rotates the vector around the z axis
     *
     *
     * This piece of math is based on the standard rotation matrix for vectors
     * in three-dimensional space. This matrix can be found here:
     * [Rotation
 * Matrix](https://en.wikipedia.org/wiki/Rotation_matrix#Basic_rotations).
     *
     * @param angle the angle to rotate the vector about. This angle is passed
     * in radians
     * @return a new, rotated vector
     */
    @Contract(pure = true)
    fun rotateAroundZ(angle: Double): Vec {
        val angleCos = Math.cos(angle)
        val angleSin = Math.sin(angle)
        val newX = angleCos * x - angleSin * y
        val newY = angleSin * x + angleCos * y
        return Vec(newX, newY, z)
    }

    @Contract(pure = true)
    fun rotate(angleX: Double, angleY: Double, angleZ: Double): Vec {
        return rotateAroundX(angleX).rotateAroundY(angleY).rotateAroundZ(angleZ)
    }

    @Contract(pure = true)
    fun rotateFromView(yawDegrees: Float, pitchDegrees: Float): Vec {
        val yaw = Math.toRadians((-1 * (yawDegrees + 90)).toDouble())
        val pitch = Math.toRadians(-pitchDegrees.toDouble())
        val cosYaw = Math.cos(yaw)
        val cosPitch = Math.cos(pitch)
        val sinYaw = Math.sin(yaw)
        val sinPitch = Math.sin(pitch)
        var initialX: Double
        val initialY: Double
        val initialZ: Double
        var x: Double
        val y: Double
        val z: Double

        // Z_Axis rotation (Pitch)
        initialX = this.x
        initialY = this.y
        x = initialX * cosPitch - initialY * sinPitch
        y = initialX * sinPitch + initialY * cosPitch

        // Y_Axis rotation (Yaw)
        initialZ = this.z
        initialX = x
        z = initialZ * cosYaw - initialX * sinYaw
        x = initialZ * sinYaw + initialX * cosYaw
        return Vec(x, y, z)
    }

    @Contract(pure = true)
    fun rotateFromView(pos: Pos): Vec {
        return rotateFromView(pos.yaw, pos.pitch)
    }

    /**
     * Rotates the vector around a given arbitrary axis in 3 dimensional space.
     *
     *
     *
     * Rotation will follow the general Right-Hand-Rule, which means rotation
     * will be counterclockwise when the axis is pointing towards the observer.
     *
     *
     * This method will always make sure the provided axis is a unit vector, to
     * not modify the length of the vector when rotating. If you are experienced
     * with the scaling of a non-unit axis vector, you can use
     * [Vec.rotateAroundNonUnitAxis].
     *
     * @param axis  the axis to rotate the vector around. If the passed vector is
     * not of length 1, it gets copied and normalized before using it for the
     * rotation. Please use [Vec.normalize] on the instance before
     * passing it to this method
     * @param angle the angle to rotate the vector around the axis
     * @return a new vector
     */
    @Contract(pure = true)
    @Throws(IllegalArgumentException::class)
    fun rotateAroundAxis(axis: Vec, angle: Double): Vec {
        return rotateAroundNonUnitAxis(if (axis.isNormalized) axis else axis.normalize(), angle)
    }

    /**
     * Rotates the vector around a given arbitrary axis in 3 dimensional space.
     *
     *
     *
     * Rotation will follow the general Right-Hand-Rule, which means rotation
     * will be counterclockwise when the axis is pointing towards the observer.
     *
     *
     * Note that the vector length will change accordingly to the axis vector
     * length. If the provided axis is not a unit vector, the rotated vector
     * will not have its previous length. The scaled length of the resulting
     * vector will be related to the axis vector. If you are not perfectly sure
     * about the scaling of the vector, use
     * [Vec.rotateAroundAxis]
     *
     * @param axis  the axis to rotate the vector around.
     * @param angle the angle to rotate the vector around the axis
     * @return a new vector
     */
    @Contract(pure = true)
    @Throws(IllegalArgumentException::class)
    fun rotateAroundNonUnitAxis(axis: Vec, angle: Double): Vec {
        val x = x
        val y = y
        val z = z
        val x2 = axis.x
        val y2 = axis.y
        val z2 = axis.z
        val cosTheta = Math.cos(angle)
        val sinTheta = Math.sin(angle)
        val dotProduct = dot(axis)
        val newX = x2 * dotProduct * (1.0 - cosTheta) + x * cosTheta + (-z2 * y + y2 * z) * sinTheta
        val newY = y2 * dotProduct * (1.0 - cosTheta) + y * cosTheta + (z2 * x - x2 * z) * sinTheta
        val newZ = z2 * dotProduct * (1.0 - cosTheta) + z * cosTheta + (-y2 * x + x2 * y) * sinTheta
        return Vec(newX, newY, newZ)
    }

    /**
     * Calculates a linear interpolation between this vector with another
     * vector.
     *
     * @param vec   the other vector
     * @param alpha The alpha value, must be between 0.0 and 1.0
     * @return Linear interpolated vector
     */
    @Contract(pure = true)
    fun lerp(vec: Vec, alpha: Double): Vec {
        return Vec(
            x + alpha * (vec.x - x),
            y + alpha * (vec.y - y),
            z + alpha * (vec.z - z)
        )
    }

    @Contract(pure = true)
    fun interpolate(target: Vec, alpha: Double, interpolation: Interpolation): Vec {
        return lerp(target, interpolation.apply(alpha))
    }

    fun interface Operator {
        fun apply(x: Double, y: Double, z: Double): Vec

        companion object {
            /**
             * Checks each axis' value, if it's below `Vec#EPSILON` then it gets replaced with `0`
             */
            val EPSILON = Operator { x: Double, y: Double, z: Double ->
                Vec(
                    if (Math.abs(x) < Vec.EPSILON) 0.0 else x,
                    if (Math.abs(y) < Vec.EPSILON) 0.0 else y,
                    if (Math.abs(z) < Vec.EPSILON) 0.0 else z
                )
            }
            val FLOOR = Operator { x: Double, y: Double, z: Double ->
                Vec(
                    Math.floor(x),
                    Math.floor(y),
                    Math.floor(z)
                )
            }
        }
    }

    fun interface Interpolation {
        fun apply(a: Double): Double

        companion object {
            val LINEAR = Interpolation { a: Double -> a }
            val SMOOTH = Interpolation { a: Double -> a * a * (3 - 2 * a) }
        }
    }

    companion object {
        val ZERO = Vec(0.0)
        val ONE = Vec(1.0)
        const val EPSILON = 0.000001

        /**
         * Converts a [Point] into a [Vec].
         * Will cast if possible, or instantiate a new object.
         *
         * @param point the point to convert
         * @return the converted vector
         */
        fun fromPoint(point: Point): Vec {
            return point as? Vec ?: Vec(point.x(), point.y(), point.z())
        }
    }
}


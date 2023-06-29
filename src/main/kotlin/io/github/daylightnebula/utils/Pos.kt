package io.github.daylightnebula.utils

import org.jetbrains.annotations.Contract
import java.util.function.DoubleUnaryOperator

class Pos(
    val x: Double,
    val y: Double,
    val z: Double,
    yaw: Float = 0f,
    val pitch: Float = 0f
) :
    Point {
    constructor(point: Point, yaw: Float, pitch: Float) : this(point.x(), point.y(), point.z(), yaw, pitch)
    constructor(point: Point) : this(point, 0f, 0f)

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
     * Changes the 3 coordinates of this position.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     * @return a new position
     */
    @Contract(pure = true)
    fun withCoord(x: Double, y: Double, z: Double): Pos {
        return Pos(x, y, z, yaw, pitch)
    }

    @Contract(pure = true)
    fun withCoord(point: Point): Pos {
        return withCoord(point.x(), point.y(), point.z())
    }

    @Contract(pure = true)
    fun withView(yaw: Float, pitch: Float): Pos {
        return Pos(x, y, z, yaw, pitch)
    }

    @Contract(pure = true)
    fun withView(pos: Pos): Pos {
        return withView(pos.yaw, pos.pitch)
    }

    /**
     * Sets the yaw and pitch to point
     * in the direction of the point.
     */
    @Contract(pure = true)
    fun withDirection(point: Point): Pos {
        /*
         * Sin = Opp / Hyp
         * Cos = Adj / Hyp
         * Tan = Opp / Adj
         *
         * x = -Opp
         * z = Adj
         */
        val x = point.x()
        val z = point.z()
        if (x == 0.0 && z == 0.0) {
            return withPitch(if (point.y() > 0) -90f else 90f)
        }
        val theta = Math.atan2(-x, z)
        val xz = Math.sqrt(MathUtils.square(x) + MathUtils.square(z))
        val _2PI = 2 * Math.PI
        return withView(
            Math.toDegrees((theta + _2PI) % _2PI).toFloat(),
            Math.toDegrees(Math.atan(-point.y() / xz)).toFloat()
        )
    }

    @Contract(pure = true)
    fun withYaw(yaw: Float): Pos {
        return Pos(x, y, z, yaw, pitch)
    }

    @Contract(pure = true)
    fun withYaw(operator: DoubleUnaryOperator): Pos {
        return withYaw(operator.applyAsDouble(yaw.toDouble()).toFloat())
    }

    @Contract(pure = true)
    fun withPitch(pitch: Float): Pos {
        return Pos(x, y, z, yaw, pitch)
    }

    @Contract(pure = true)
    fun withLookAt(point: Point): Pos {
        if (samePoint(point)) return this
        val (x1, y1, z1) = Vec.fromPoint(point.sub(this)).normalize()
        return withView(
            PositionUtils.getLookYaw(x1, z1),
            PositionUtils.getLookPitch(x1, y1, z1)
        )
    }

    @Contract(pure = true)
    fun withPitch(operator: DoubleUnaryOperator): Pos {
        return withPitch(operator.applyAsDouble(pitch.toDouble()).toFloat())
    }

    /**
     * Checks if two positions have a similar view (yaw/pitch).
     *
     * @param position the position to compare
     * @return true if the two positions have the same view
     */
    fun sameView(position: Pos): Boolean {
        return sameView(position.yaw, position.pitch)
    }

    fun sameView(yaw: Float, pitch: Float): Boolean {
        return java.lang.Float.compare(this.yaw, yaw) == 0 &&
                java.lang.Float.compare(this.pitch, pitch) == 0
    }

    /**
     * Gets a unit-vector pointing in the direction that this Location is
     * facing.
     *
     * @return a vector pointing the direction of this location's [ ][.pitch] and [yaw][.yaw]
     */
    fun direction(): Vec {
        val rotX = yaw
        val rotY = pitch
        val xz = Math.cos(Math.toRadians(rotY.toDouble()))
        return Vec(
            -xz * Math.sin(Math.toRadians(rotX.toDouble())),
            -Math.sin(Math.toRadians(rotY.toDouble())),
            xz * Math.cos(Math.toRadians(rotX.toDouble()))
        )
    }

    /**
     * Returns a new position based on this position fields.
     *
     * @param operator the operator deconstructing this object and providing a new position
     * @return the new position
     */
    @Contract(pure = true)
    fun apply(operator: Operator): Pos {
        return operator.apply(x, y, z, yaw, pitch)
    }

    @Contract(pure = true)
    override fun withX(operator: DoubleUnaryOperator): Pos {
        return Pos(operator.applyAsDouble(x), y, z, yaw, pitch)
    }

    @Contract(pure = true)
    override fun withX(x: Double): Pos {
        return Pos(x, y, z, yaw, pitch)
    }

    @Contract(pure = true)
    override fun withY(operator: DoubleUnaryOperator): Pos {
        return Pos(x, operator.applyAsDouble(y), z, yaw, pitch)
    }

    @Contract(pure = true)
    override fun withY(y: Double): Pos {
        return Pos(x, y, z, yaw, pitch)
    }

    @Contract(pure = true)
    override fun withZ(operator: DoubleUnaryOperator): Pos {
        return Pos(x, y, operator.applyAsDouble(z), yaw, pitch)
    }

    @Contract(pure = true)
    override fun withZ(z: Double): Pos {
        return Pos(x, y, z, yaw, pitch)
    }

    override fun add(x: Double, y: Double, z: Double): Pos {
        return Pos(this.x + x, this.y + y, this.z + z, yaw, pitch)
    }

    override fun add(point: Point): Pos {
        return add(point.x(), point.y(), point.z())
    }

    override fun add(value: Double): Pos {
        return add(value, value, value)
    }

    override fun sub(x: Double, y: Double, z: Double): Pos {
        return Pos(this.x - x, this.y - y, this.z - z, yaw, pitch)
    }

    override fun sub(point: Point): Pos {
        return sub(point.x(), point.y(), point.z())
    }

    override fun sub(value: Double): Pos {
        return sub(value, value, value)
    }

    override fun mul(x: Double, y: Double, z: Double): Pos {
        return Pos(this.x * x, this.y * y, this.z * z, yaw, pitch)
    }

    override fun mul(point: Point): Pos {
        return mul(point.x(), point.y(), point.z())
    }

    override fun mul(value: Double): Pos {
        return mul(value, value, value)
    }

    override fun div(x: Double, y: Double, z: Double): Pos {
        return Pos(this.x / x, this.y / y, this.z / z, yaw, pitch)
    }

    override fun div(point: Point): Pos {
        return div(point.x(), point.y(), point.z())
    }

    override fun div(value: Double): Pos {
        return div(value, value, value)
    }

    override fun relative(face: BlockFace): Pos {
        return super.relative(face) as Pos
    }

    @Contract(pure = true)
    fun asVec(): Vec {
        return Vec(x, y, z)
    }

    fun interface Operator {
        fun apply(x: Double, y: Double, z: Double, yaw: Float, pitch: Float): Pos
    }

    val yaw: Float

    init {
        var yaw = yaw
        yaw = fixYaw(yaw)
        this.yaw = yaw
    }

    companion object {
        val ZERO = Pos(0.0, 0.0, 0.0)

        /**
         * Converts a [Point] into a [Pos].
         * Will cast if possible, or instantiate a new object.
         *
         * @param point the point to convert
         * @return the converted position
         */
        fun fromPoint(point: Point): Pos {
            return point as? Pos ?: Pos(point.x(), point.y(), point.z())
        }

        /**
         * Fixes a yaw value that is not between -180.0F and 180.0F
         * So for example -1355.0F becomes 85.0F and 225.0F becomes -135.0F
         *
         * @param yaw The possible "wrong" yaw
         * @return a fixed yaw
         */
        private fun fixYaw(yaw: Float): Float {
            var yaw = yaw
            yaw = yaw % 360
            if (yaw < -180.0f) {
                yaw += 360.0f
            } else if (yaw > 180.0f) {
                yaw -= 360.0f
            }
            return yaw
        }
    }
}

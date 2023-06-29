package io.github.daylightnebula.utils

import org.jetbrains.annotations.Contract
import java.util.function.DoubleUnaryOperator


interface Point {
    /**
     * Gets the X coordinate.
     *
     * @return the X coordinate
     */
    @Contract(pure = true)
    fun x(): Double

    /**
     * Gets the Y coordinate.
     *
     * @return the Y coordinate
     */
    @Contract(pure = true)
    fun y(): Double

    /**
     * Gets the Z coordinate.
     *
     * @return the Z coordinate
     */
    @Contract(pure = true)
    fun z(): Double

    /**
     * Gets the floored value of the X component
     *
     * @return the block X
     */
    @Contract(pure = true)
    fun blockX(): Int {
        return Math.floor(x()).toInt()
    }

    /**
     * Gets the floored value of the X component
     *
     * @return the block X
     */
    @Contract(pure = true)
    fun blockY(): Int {
        return Math.floor(y()).toInt()
    }

    /**
     * Gets the floored value of the X component
     *
     * @return the block X
     */
    @Contract(pure = true)
    fun blockZ(): Int {
        return Math.floor(z()).toInt()
    }

//    @Contract(pure = true)
//    fun chunkX(): Int {
//        return ChunkUtils.getChunkCoordinate(x())
//    }
//
//    @Contract(pure = true)
//    @ApiStatus.Experimental
//    fun section(): Int {
//        return ChunkUtils.getChunkCoordinate(y())
//    }
//
//    @Contract(pure = true)
//    fun chunkZ(): Int {
//        return ChunkUtils.getChunkCoordinate(z())
//    }

    /**
     * Creates a point with a modified X coordinate based on its value.
     *
     * @param operator the operator providing the current X coordinate and returning the new
     * @return a new point
     */
    @Contract(pure = true)
    fun withX(operator: DoubleUnaryOperator): Point

    /**
     * Creates a point with the specified X coordinate.
     *
     * @param x the new X coordinate
     * @return a new point
     */
    @Contract(pure = true)
    fun withX(x: Double): Point

    /**
     * Creates a point with a modified Y coordinate based on its value.
     *
     * @param operator the operator providing the current Y coordinate and returning the new
     * @return a new point
     */
    @Contract(pure = true)
    fun withY(operator: DoubleUnaryOperator): Point

    /**
     * Creates a point with the specified Y coordinate.
     *
     * @param y the new Y coordinate
     * @return a new point
     */
    @Contract(pure = true)
    fun withY(y: Double): Point

    /**
     * Creates a point with a modified Z coordinate based on its value.
     *
     * @param operator the operator providing the current Z coordinate and returning the new
     * @return a new point
     */
    @Contract(pure = true)
    fun withZ(operator: DoubleUnaryOperator): Point

    /**
     * Creates a point with the specified Z coordinate.
     *
     * @param z the new Z coordinate
     * @return a new point
     */
    @Contract(pure = true)
    fun withZ(z: Double): Point

    @Contract(pure = true)
    fun add(x: Double, y: Double, z: Double): Point

    @Contract(pure = true)
    fun add(point: Point): Point

    @Contract(pure = true)
    fun add(value: Double): Point

    @Contract(pure = true)
    fun sub(x: Double, y: Double, z: Double): Point

    @Contract(pure = true)
    fun sub(point: Point): Point

    @Contract(pure = true)
    fun sub(value: Double): Point

    @Contract(pure = true)
    fun mul(x: Double, y: Double, z: Double): Point

    @Contract(pure = true)
    fun mul(point: Point): Point

    @Contract(pure = true)
    fun mul(value: Double): Point

    @Contract(pure = true)
    fun div(x: Double, y: Double, z: Double): Point

    @Contract(pure = true)
    operator fun div(point: Point): Point

    @Contract(pure = true)
    operator fun div(value: Double): Point

    @Contract(pure = true)
    fun relative(face: BlockFace): Point {
        return when (face) {
            BlockFace.BOTTOM -> sub(0.0, 1.0, 0.0)
            BlockFace.TOP -> add(0.0, 1.0, 0.0)
            BlockFace.NORTH -> sub(0.0, 0.0, 1.0)
            BlockFace.SOUTH -> add(0.0, 0.0, 1.0)
            BlockFace.WEST -> sub(1.0, 0.0, 0.0)
            BlockFace.EAST -> add(1.0, 0.0, 0.0)
        }
    }

    @Contract(pure = true)
    fun distanceSquared(x: Double, y: Double, z: Double): Double {
        return MathUtils.square(x() - x) + MathUtils.square(y() - y) + MathUtils.square(z() - z)
    }

    /**
     * Gets the squared distance between this point and another.
     *
     * @param point the other point
     * @return the squared distance
     */
    @Contract(pure = true)
    fun distanceSquared(point: Point): Double {
        return distanceSquared(point.x(), point.y(), point.z())
    }

    @Contract(pure = true)
    fun distance(x: Double, y: Double, z: Double): Double {
        return Math.sqrt(distanceSquared(x, y, z))
    }

    /**
     * Gets the distance between this point and another. The value of this
     * method is not cached and uses a costly square-root function, so do not
     * repeatedly call this method to get the vector's magnitude. NaN will be
     * returned if the inner result of the sqrt() function overflows, which
     * will be caused if the distance is too long.
     *
     * @param point the other point
     * @return the distance
     */
    @Contract(pure = true)
    fun distance(point: Point): Double {
        return distance(point.x(), point.y(), point.z())
    }

    fun samePoint(x: Double, y: Double, z: Double): Boolean {
        return java.lang.Double.compare(x, x()) == 0 && java.lang.Double.compare(
            y,
            y()
        ) == 0 && java.lang.Double.compare(z, z()) == 0
    }

    /**
     * Checks it two points have similar (x/y/z).
     *
     * @param point the point to compare
     * @return true if the two positions are similar
     */
    fun samePoint(point: Point): Boolean {
        return samePoint(point.x(), point.y(), point.z())
    }

    val isZero: Boolean
        /**
         * Checks if the three coordinates [.x], [.y] and [.z]
         * are equal to `0`.
         *
         * @return true if the three coordinates are zero
         */
        get() = x() == 0.0 && y() == 0.0 && z() == 0.0

    /**
     * Checks if two points are in the same chunk.
     *
     * @param point the point to compare to
     * @return true if 'this' is in the same chunk as `point`
     */
//    fun sameChunk(point: Point): Boolean {
//        return chunkX() == point.chunkX() && chunkZ() == point.chunkZ()
//    }

    fun sameBlock(blockX: Int, blockY: Int, blockZ: Int): Boolean {
        return blockX() == blockX && blockY() == blockY && blockZ() == blockZ
    }

    /**
     * Checks if two points are in the same block.
     *
     * @param point the point to compare to
     * @return true if 'this' is in the same block as `point`
     */
    fun sameBlock(point: Point): Boolean {
        return sameBlock(point.blockX(), point.blockY(), point.blockZ())
    }
}

data class DeathLocation(val dimension: String, val position: Point)
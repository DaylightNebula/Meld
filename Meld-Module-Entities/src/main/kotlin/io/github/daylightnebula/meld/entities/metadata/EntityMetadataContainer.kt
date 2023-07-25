package io.github.daylightnebula.meld.entities.metadata

import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.networking.common.ByteWriter

// interface for all objects or groupings of metadata objects so they can be written to output streams
interface IEntityMetadataContainer {
    fun writeJava(writer: ByteWriter)
    fun writeBedrock()
}

// interface for objects that can parent a metadata object
interface IEntityMetadataParent {
    fun replaceMetadataAtIndex(index: Int, obj: EntityMetadataObject<*>)
    fun <T> getMetadataAtIndex(index: Int): EntityMetadataObject<T>?
}

// representation of all metadata objects like bools or optchats or numbers, etc
abstract class EntityMetadataObject<T>(val index: Int, private val typeID: Int, val value: T): IEntityMetadataContainer {
    // write index and id before writing the rest of the content
    override fun writeJava(writer: ByteWriter) {
        writer.writeUByte(index.toUByte())
        writer.writeVarInt(typeID)
        writeJava0(writer)
    }
    override fun writeBedrock() = NeedsBedrock()

    // used to write the context of the object
    abstract fun writeJava0(writer: ByteWriter)
}

// function to make the creation of new objects easier
fun <T> metaObject(index: Int, typeID: Int, value: T, write: (writer: ByteWriter) -> Unit) =
    object: EntityMetadataObject<T>(index, typeID, value) {
        override fun writeJava0(writer: ByteWriter) {
            write(writer)
        }
    }

// representation of a grouping of metadata objects
class EntityMetadata(val objects: MutableList<IEntityMetadataContainer>): IEntityMetadataContainer, IEntityMetadataParent {
    // write all objects, in order, to the output writer
    override fun writeJava(writer: ByteWriter) {
        for (it in objects) it.writeJava(writer)
    }

    override fun replaceMetadataAtIndex(index: Int, obj: EntityMetadataObject<*>) {
        // find object to replace
        val index = objects.indexOfFirst { it is EntityMetadataObject<*> && it.index == index }

        // if index found, replace object
        if (index != -1) objects[index] = obj
        // otherwise, call replace index at child containers
        else objects.filterIsInstance<EntityMetadata>().forEach { it.replaceMetadataAtIndex(index, obj) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> getMetadataAtIndex(index: Int): EntityMetadataObject<T>? {
        // find result
        var result = objects.firstOrNull { it is EntityMetadataObject<*> && it.index == index } as? EntityMetadataObject<T>

        // if no result, see if containers can find it
        objects.filterIsInstance<EntityMetadata>().forEach { subData ->
            val result2 = subData.getMetadataAtIndex<T>(index)
            if (result2 != null) result = result2
        }

        return result
    }

    override fun writeBedrock() = NeedsBedrock()
}

// function to make the creation of metadata groups easier
fun metadata(vararg objects: IEntityMetadataContainer) = EntityMetadata(objects.toMutableList())
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
    fun replaceAtIndex(index: Int, obj: EntityMetadataObject)
}

// representation of all metadata objects like bools or optchats or numbers, etc
abstract class EntityMetadataObject(val index: Int, private val typeID: Int): IEntityMetadataContainer {
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
fun metaObject(index: Int, typeID: Int, write: (writer: ByteWriter) -> Unit) =
    object: EntityMetadataObject(index, typeID) {
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

    override fun replaceAtIndex(index: Int, obj: EntityMetadataObject) {
        // find object to replace
        val index = objects.indexOfFirst { it is EntityMetadataObject && it.index == index }

        // if index found, replace object
        if (index != -1) objects[index] = obj
        // otherwise, call replace index at child containers
        else objects.filterIsInstance<EntityMetadata>().forEach { it.replaceAtIndex(index, obj) }
    }

    override fun writeBedrock() = NeedsBedrock()
}

// function to make the creation of metadata groups easier
fun metadata(vararg objects: IEntityMetadataContainer) = EntityMetadata(objects.toMutableList())
package io.github.daylightnebula.meld.server.entities

import io.github.daylightnebula.meld.ksp.data.Codec
import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.ksp.data.RegisterCodec
import io.github.daylightnebula.meld.server.UByteCodec

class EntityMetadata {

}

@RegisterCodec("entityMetadata", EntityMetadata::class)
object EntityMetadataCodec: Codec<EntityMetadata> {
    override fun decode(reader: IReader) = TODO("Not yet implemented")
    override fun encode(data: EntityMetadata) = UByteCodec.encode(UByte.MAX_VALUE)
}

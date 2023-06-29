package io.github.daylightnebula.world.biomes

import io.github.daylightnebula.utils.NamespaceID
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.jglrxavpok.hephaistos.nbt.mutable.MutableNBTCompound

data class BiomeParticle(val probability: Float, val option: Option) {
    fun toNbt(): NBTCompound {
        return NBT.Compound(
            java.util.Map.of(
                "probability", NBT.Float(probability),
                "options", option.toNbt()
            )
        )
    }

    interface Option {
        fun toNbt(): NBTCompound
    }

//    class BlockOption(block: Block) : Option {
//        override fun toNbt(): NBTCompound {
//            return NBT.Compound { nbtCompound: MutableNBTCompound ->
//                nbtCompound.setString("type", type)
//                nbtCompound.setString("Name", block.name())
//                val propertiesMap: Map<String, String> = block.properties()
//                if (propertiesMap.size != 0) {
//                    nbtCompound["Properties"] = NBT.Compound { p: MutableNBTCompound ->
//                        propertiesMap.forEach { (key: String?, value: String?) ->
//                            p.setString(
//                                key, value
//                            )
//                        }
//                    }
//                }
//            }
//        }
//
//        val block: Block
//
//        init {
//            this.probability = probability
//            this.option = option
//            this.block = block
//        }
//
//        companion object {
//            //TODO also can be falling_dust
//            private const val type = "block"
//        }
//    }
//
//    class DustOption(red: Float, green: Float, blue: Float, scale: Float) :
//        Option {
//        override fun toNbt(): NBTCompound {
//            return NBT.Compound(
//                java.util.Map.of(
//                    "type", NBT.String(type),
//                    "r", NBT.Float(red),
//                    "g", NBT.Float(green),
//                    "b", NBT.Float(blue),
//                    "scale", NBT.Float(scale)
//                )
//            )
//        }
//
//        val red: Float
//        val green: Float
//        val blue: Float
//        val scale: Float
//
//        init {
//            this.probability = probability
//            this.option = option
//            this.block = block
//            this.red = red
//            this.green = green
//            this.blue = blue
//            this.scale = scale
//        }
//
//        companion object {
//            private const val type = "dust"
//        }
//    }
//
//    class ItemOption(item: ItemStack) : Option {
//        override fun toNbt(): NBTCompound {
//            //todo test count might be wrong type
//            val nbtCompound: NBTCompound = item.meta().toNBT()
//            return nbtCompound.modify { n: MutableNBTCompound ->
//                n.setString(
//                    "type",
//                    type
//                )
//            }
//        }
//
//        val item: ItemStack
//
//        init {
//            this.probability = probability
//            this.option = option
//            this.block = block
//            this.red = red
//            this.green = green
//            this.blue = blue
//            this.scale = scale
//            this.item = item
//        }
//
//        companion object {
//            private const val type = "item"
//        }
//    }
//
//    class NormalOption(type: NamespaceID) : Option {
//        override fun toNbt(): NBTCompound {
//            return NBT.Compound(java.util.Map.of("type", NBT.String(type.toString())))
//        }
//
//        val type: NamespaceID
//
//        init {
//            this.probability = probability
//            this.option = option
//            this.block = block
//            this.red = red
//            this.green = green
//            this.blue = blue
//            this.scale = scale
//            this.item = item
//            this.type = type
//        }
//    }
}

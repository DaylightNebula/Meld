package io.github.daylightnebula.meld.inventories.utils

enum class InventoryType(val id: Int, val count: Int) {
    GENERIC_9x1(0, 45),
    GENERIC_9x2(1, 54),
    GENERIC_9x3(2, 63),
    GENERIC_9x4(3, 72),
    GENERIC_9x5(4, 81),
    GENERIC_9x6(5, 90),
    GENERIC_3x3(6, 45),
    ANVIL(7, 39),
    BEACON(8, 37),
    BLAST_FURNACE(9, 39),
    BREWING_STAND(10, 41),
    CRAFTING_TABLE(11, 46),
    ENCHANTMENT_TABLE(12, 38),
    FURNACE(13, 39),
    GRINDSTONE(14, 39),
    HOPPER(15, 41),
    LECTERN(16, 1),
    LOOM(17, 40),
    MERCHANT(18, 39),
    SHULKER_BOX(19, 63),
    SMITHING_TABLE(20, 40),
    SMOKER(21, 39),
    CARTOGRAPHY(22, 39),
    STONECUTTER(23, 38)
}
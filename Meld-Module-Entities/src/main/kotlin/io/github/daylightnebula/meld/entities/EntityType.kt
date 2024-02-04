package io.github.daylightnebula.meld.entities

enum class EntityType(val mcID: Int, val identifier: String, val mcName: String) {
    ALLAY(0, "minecraft:allay", "Allay"),
    AREA_EFFECT_CLOUD(1, "minecraft:area_effect_cloud", "Area Effect Cloud"),
    ARMOR_STAND(2, "minecraft:armor_stand", "Armor Stand"),
    ARROW(3, "minecraft:arrow", "Arrow"),
    AXOLOTL(4, "minecraft:axolotl", "Axolotl"),
    BAT(5, "minecraft:bat", "Bat"),
    BEE(6, "minecraft:bee", "Bee"),
    BLAZE(7, "minecraft:blaze", "Blaze"),
    BLOCK_DISPLAY(8, "minecraft:block_display", "Block Display"),
    BOAT(9, "minecraft:boat", "Boat"),
    CAMEL(10, "minecraft:camel", "Camel"),
    CAT(11, "minecraft:cat", "Cat"),
    CAVE_SPIDER(12, "minecraft:cave_spider", "Cave Spider"),
    CHEST_BOAT(13, "minecraft:chest_boat", "Chest Boat"),
    CHEST_MINECART(14, "minecraft:chest_minecart", "Chest Minecart"),
    CHICKEN(15, "minecraft:chicken", "Chicken"),
    COD(16, "minecraft:cod", "Cod"),
    COMMAND_BLOCK_MINECART(17, "minecraft:command_block_minecart", "Command Block Minecart"),
    COW(18, "minecraft:cow", "Cow"),
    CREEPER(19, "minecraft:creeper", "Creeper"),
    DOLPHIN(20, "minecraft:dolphin", "Dolphin"),
    DONKEY(21, "minecraft:donkey", "Donkey"),
    DRAGON_FIREBALL(22, "minecraft:dragon_fireball", "Dragon Fireball"),
    DROWNED(23, "minecraft:drowned", "Drowned"),
    EGG(24, "minecraft:egg", "Egg"),
    ELDER_GUARDIAN(25, "minecraft:elder_guardian", "Elder Guardian"),
    END_CRYSTAL(26, "minecraft:end_crystal", "End Crystal"),
    ENDER_DRAGON(27, "minecraft:ender_dragon", "Ender Dragon"),
    ENDER_PEARL(28, "minecraft:ender_pearl", "Ender Pearl"),
    ENDERMAN(29, "minecraft:enderman", "Enderman"),
    ENDERMITE(30, "minecraft:endermite", "Endermite"),
    EVOKER(31, "minecraft:evoker", "Evoker"),
    EVOKER_FANGS(32, "minecraft:evoker_fangs", "Evoker Fangs"),
    EXPERIENCE_BOTTLE(33, "minecraft:experience_bottle", "Experience Bottle"),
    EYE_OF_ENDER(35, "minecraft:eye_of_ender", "Eye Of Ender"),
    FALLING_BLOCK(36, "minecraft:falling_block", "Falling Block"),
    FIREWORK_ROCKET(37, "minecraft:firework_rocket", "Firework Rocket"),
    FOX(38, "minecraft:fox", "Fox"),
    FROG(39, "minecraft:frog", "Frog"),
    FURNACE_MINECART(40, "minecraft:furnace_minecart", "Furnace Minecart"),
    GHAST(41, "minecraft:ghast", "Ghast"),
    GIANT(42, "minecraft:giant", "Giant"),
    GLOW_ITEM_FRAME(43, "minecraft:glow_item_frame", "Glow Item Frame"),
    GLOW_SQUID(44, "minecraft:glow_squid", "Glow Squid"),
    GOAT(45, "minecraft:goat", "Goat"),
    GUARDIAN(46, "minecraft:guardian", "Guardian"),
    HOGLIN(47, "minecraft:hoglin", "Hoglin"),
    HOPPER_MINECART(48, "minecraft:hopper_minecart", "Hopper Minecart"),
    HORSE(49, "minecraft:horse", "Horse"),
    HUSK(50, "minecraft:husk", "Husk"),
    ILLUSIONER(51, "minecraft:illusioner", "Illusioner"),
    INTERACTION(52, "minecraft:interaction", "Interaction"),
    IRON_GOLEM(53, "minecraft:iron_golem", "Iron Golem"),
    ITEM(54, "minecraft:item", "Item"),
    ITEM_DISPLAY(55, "minecraft:item_display", "Item Display"),
    ITEM_FRAME(56, "minecraft:item_frame", "Item Frame"),
    FIREBALL(57, "minecraft:fireball", "Fireball"),
    LEASH_KNOT(58, "minecraft:leash_knot", "Leash Knot"),
    LIGHTNING_BOLT(59, "minecraft:lightning_bolt", "Lightning Bolt"),
    LLAMA(60, "minecraft:llama", "Llama"),
    LLAMA_SPIT(61, "minecraft:llama_spit", "Llama Spit"),
    MAGMA_CUBE(62, "minecraft:magma_cube", "Magma Cube"),
    MINECART(64, "minecraft:minecart", "Minecart"),
    MOOSHROOM(65, "minecraft:mooshroom", "Mooshroom"),
    MULE(66, "minecraft:mule", "Mule"),
    OCELOT(67, "minecraft:ocelot", "Ocelot"),
    PAINTING(68, "minecraft:painting", "Painting"),
    PANDA(69, "minecraft:panda", "Panda"),
    PARROT(70, "minecraft:parrot", "Parrot"),
    PHANTOM(71, "minecraft:phantom", "Phantom"),
    PIG(72, "minecraft:pig", "Pig"),
    PIGLIN(73, "minecraft:piglin", "Piglin"),
    PIGLIN_BRUTE(74, "minecraft:piglin_brute", "Piglin Brute"),
    PILLAGER(75, "minecraft:pillager", "Pillager"),
    POLAR_BEAR(76, "minecraft:polar_bear", "Polar Bear"),
    POTION(77, "minecraft:potion", "Potion"),
    PUFFERFISH(78, "minecraft:pufferfish", "Pufferfish"),
    RABBIT(79, "minecraft:rabbit", "Rabbit"),
    RAVAGER(80, "minecraft:ravager", "Ravager"),
    SALMON(81, "minecraft:salmon", "Salmon"),
    SHEEP(82, "minecraft:sheep", "Sheep"),
    SHULKER(83, "minecraft:shulker", "Shulker"),
    SHULKER_BULLET(84, "minecraft:shulker_bullet", "Shulker Bullet"),
    SILVERFISH(85, "minecraft:silverfish", "Silverfish"),
    SKELETON(86, "minecraft:skeleton", "Skeleton"),
    SKELETON_HORSE(87, "minecraft:skeleton_horse", "Skeleton Horse"),
    SLIME(88, "minecraft:slime", "Slime"),
    SMALL_FIREBALL(89, "minecraft:small_fireball", "Small Fireball"),
    SNIFFER(90, "minecraft:sniffer", "Sniffer"),
    SNOW_GOLEM(91, "minecraft:snow_golem", "Snow Golem"),
    SNOWBALL(92, "minecraft:snowball", "Snowball"),
    SPAWNER_MINECART(93, "minecraft:spawner_minecart", "Spawner Minecart"),
    SPECTRAL_ARROW(94, "minecraft:spectral_arrow", "Spectral Arrow"),
    SPIDER(95, "minecraft:spider", "Spider"),
    SQUID(96, "minecraft:squid", "Squid"),
    STRAY(97, "minecraft:stray", "Stray"),
    STRIDER(98, "minecraft:strider", "Strider"),
    TADPOLE(99, "minecraft:tadpole", "Tadpole"),
    TEXT_DISPLAY(100, "minecraft:text_display", "Text Display"),
    TNT(101, "minecraft:tnt", "Tnt"),
    TNT_MINECART(102, "minecraft:tnt_minecart", "Tnt Minecart"),
    TRADER_LLAMA(103, "minecraft:trader_llama", "Trader Llama"),
    TRIDENT(104, "minecraft:trident", "Trident"),
    TROPICAL_FISH(105, "minecraft:tropical_fish", "Tropical Fish"),
    TURTLE(106, "minecraft:turtle", "Turtle"),
    VEX(107, "minecraft:vex", "Vex"),
    VILLAGER(108, "minecraft:villager", "Villager"),
    VINDICATOR(109, "minecraft:vindicator", "Vindicator"),
    WANDERING_TRADER(110, "minecraft:wandering_trader", "Wandering Trader"),
    WARDEN(111, "minecraft:warden", "Warden"),
    WITCH(112, "minecraft:witch", "Witch"),
    WITHER(113, "minecraft:wither", "Wither"),
    WITHER_SKELETON(114, "minecraft:wither_skeleton", "Wither Skeleton"),
    WITHER_SKULL(115, "minecraft:wither_skull", "Wither Skull"),
    WOLF(116, "minecraft:wolf", "Wolf"),
    ZOGLIN(117, "minecraft:zoglin", "Zoglin"),
    ZOMBIE(118, "minecraft:zombie", "Zombie"),
    ZOMBIE_HORSE(119, "minecraft:zombie_horse", "Zombie Horse"),
    ZOMBIE_VILLAGER(120, "minecraft:zombie_villager", "Zombie Villager"),
    ZOMBIFIED_PIGLIN(121, "minecraft:zombified_piglin", "Zombified Piglin"),
    PLAYER(122, "minecraft:player", "Player"),
    FISHING_BOBBER(123, "minecraft:fishing_bobber", "Fishing Bobber")
}
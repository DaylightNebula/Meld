package io.github.daylightnebula.meld.login

import io.github.daylightnebula.meld.server.meldJson
import kotlinx.serialization.json.JsonObject

object DefaultTags {
    val defaultTags: JsonObject = meldJson.decodeFromString("""
{
    "tags": {
        "minecraft:banner_pattern": [
            {
                "entries": [
                    26,
                    27,
                    28,
                    29,
                    31,
                    38,
                    35,
                    37,
                    32,
                    36,
                    34,
                    33,
                    25,
                    5,
                    30,
                    39,
                    40,
                    41,
                    42,
                    7,
                    10,
                    9,
                    8,
                    3,
                    23,
                    19,
                    17,
                    20,
                    18,
                    1,
                    14,
                    15
                ],
                "tag_name": {
                    "raw_string": "minecraft:no_item_required"
                }
            },
            {
                "entries": [
                    22
                ],
                "tag_name": {
                    "raw_string": "minecraft:pattern_item/piglin"
                }
            },
            {
                "entries": [
                    2
                ],
                "tag_name": {
                    "raw_string": "minecraft:pattern_item/field_masoned"
                }
            },
            {
                "entries": [
                    12
                ],
                "tag_name": {
                    "raw_string": "minecraft:pattern_item/flower"
                }
            },
            {
                "entries": [
                    11
                ],
                "tag_name": {
                    "raw_string": "minecraft:pattern_item/flow"
                }
            },
            {
                "entries": [
                    4
                ],
                "tag_name": {
                    "raw_string": "minecraft:pattern_item/creeper"
                }
            },
            {
                "entries": [
                    13
                ],
                "tag_name": {
                    "raw_string": "minecraft:pattern_item/globe"
                }
            },
            {
                "entries": [
                    16
                ],
                "tag_name": {
                    "raw_string": "minecraft:pattern_item/guster"
                }
            },
            {
                "entries": [
                    6
                ],
                "tag_name": {
                    "raw_string": "minecraft:pattern_item/bordure_indented"
                }
            },
            {
                "entries": [
                    21
                ],
                "tag_name": {
                    "raw_string": "minecraft:pattern_item/mojang"
                }
            },
            {
                "entries": [
                    24
                ],
                "tag_name": {
                    "raw_string": "minecraft:pattern_item/skull"
                }
            }
        ],
        "minecraft:block": [
            {
                "entries": [
                    204,
                    610,
                    611,
                    612,
                    613,
                    615,
                    616,
                    854,
                    855,
                    617,
                    618,
                    614,
                    1002,
                    1003,
                    1005,
                    1004,
                    1006,
                    1007,
                    1009,
                    1008
                ],
                "tag_name": {
                    "raw_string": "minecraft:mob_interactable_doors"
                }
            },
            {
                "entries": [
                    814,
                    815
                ],
                "tag_name": {
                    "raw_string": "minecraft:campfires"
                }
            },
            {
                "entries": [
                    269,
                    270
                ],
                "tag_name": {
                    "raw_string": "minecraft:soul_fire_base_blocks"
                }
            },
            {
                "entries": [
                    268,
                    635
                ],
                "tag_name": {
                    "raw_string": "minecraft:infiniburn_nether"
                }
            },
            {
                "entries": [
                    563,
                    564,
                    565,
                    566,
                    567,
                    569,
                    570,
                    840,
                    841,
                    571,
                    572,
                    568
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_slabs"
                }
            },
            {
                "entries": [
                    53,
                    51,
                    49,
                    52,
                    50,
                    55,
                    56,
                    57,
                    54,
                    1,
                    520,
                    44,
                    46,
                    964,
                    363
                ],
                "tag_name": {
                    "raw_string": "minecraft:snaps_goat_horn"
                }
            },
            {
                "entries": [
                    56,
                    20,
                    67,
                    86
                ],
                "tag_name": {
                    "raw_string": "minecraft:pale_oak_logs"
                }
            },
            {
                "entries": [
                    46,
                    47
                ],
                "tag_name": {
                    "raw_string": "minecraft:coal_ores"
                }
            },
            {
                "entries": [
                    137,
                    138,
                    139,
                    140,
                    141,
                    142,
                    143,
                    144,
                    145,
                    146,
                    147,
                    148,
                    149,
                    150,
                    151,
                    152
                ],
                "tag_name": {
                    "raw_string": "minecraft:occludes_vibration_signals"
                }
            },
            {
                "entries": [
                    154,
                    1091,
                    156,
                    157,
                    158,
                    159,
                    160,
                    161,
                    162,
                    163,
                    164,
                    165,
                    167,
                    166,
                    155,
                    1092
                ],
                "tag_name": {
                    "raw_string": "minecraft:small_flowers"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    4,
                    6,
                    937,
                    1051,
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1044,
                    1088,
                    1050,
                    59,
                    518,
                    448,
                    449,
                    450,
                    451,
                    452,
                    453,
                    454,
                    455,
                    456,
                    457,
                    458,
                    459,
                    460,
                    461,
                    462,
                    463,
                    39,
                    264,
                    40,
                    37,
                    262,
                    953
                ],
                "tag_name": {
                    "raw_string": "minecraft:azalea_root_replaceable"
                }
            },
            {
                "entries": [
                    301,
                    299,
                    303,
                    304,
                    300,
                    297,
                    298,
                    846,
                    847,
                    305,
                    306,
                    302
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_trapdoors"
                }
            },
            {
                "entries": [
                    356,
                    631
                ],
                "tag_name": {
                    "raw_string": "minecraft:invalid_spawn_inside"
                }
            },
            {
                "entries": [
                    8,
                    260,
                    262,
                    11,
                    10
                ],
                "tag_name": {
                    "raw_string": "minecraft:foxes_spawnable_on"
                }
            },
            {
                "entries": [
                    8,
                    260,
                    262,
                    10,
                    11
                ],
                "tag_name": {
                    "raw_string": "minecraft:wolves_spawnable_on"
                }
            },
            {
                "entries": [
                    137,
                    138,
                    139,
                    140,
                    141,
                    142,
                    143,
                    144,
                    145,
                    146,
                    147,
                    148,
                    149,
                    150,
                    151,
                    152
                ],
                "tag_name": {
                    "raw_string": "minecraft:wool"
                }
            },
            {
                "entries": [
                    184,
                    369,
                    370,
                    371,
                    480,
                    482,
                    483,
                    850,
                    851,
                    484,
                    485,
                    481,
                    486,
                    207,
                    362,
                    348,
                    336,
                    335,
                    624,
                    445,
                    562,
                    495,
                    494,
                    496,
                    760,
                    761,
                    762,
                    763,
                    764,
                    765,
                    766,
                    767,
                    768,
                    769,
                    770,
                    771,
                    772,
                    773,
                    878,
                    886,
                    889,
                    1053,
                    1057,
                    1061,
                    1065,
                    978,
                    979,
                    980,
                    981,
                    995,
                    996,
                    997,
                    994,
                    337,
                    939,
                    943,
                    948,
                    342
                ],
                "tag_name": {
                    "raw_string": "minecraft:stairs"
                }
            },
            {
                "entries": [
                    55,
                    77,
                    66,
                    85,
                    56,
                    20,
                    67,
                    86,
                    49,
                    71,
                    68,
                    79,
                    53,
                    75,
                    64,
                    83,
                    51,
                    73,
                    62,
                    81,
                    52,
                    74,
                    63,
                    82,
                    50,
                    72,
                    61,
                    80,
                    57,
                    78,
                    69,
                    87,
                    54,
                    76,
                    65,
                    84,
                    826,
                    827,
                    828,
                    829,
                    817,
                    818,
                    819,
                    820
                ],
                "tag_name": {
                    "raw_string": "minecraft:logs"
                }
            },
            {
                "entries": [
                    40
                ],
                "tag_name": {
                    "raw_string": "minecraft:trail_ruins_replaceable"
                }
            },
            {
                "entries": [
                    194,
                    195,
                    196,
                    197,
                    199,
                    200,
                    201,
                    856,
                    857,
                    202,
                    203,
                    198,
                    208,
                    209,
                    210,
                    211,
                    213,
                    214,
                    215,
                    858,
                    859,
                    216,
                    217,
                    212,
                    218,
                    219,
                    220,
                    221,
                    222,
                    223,
                    224,
                    225,
                    226,
                    227,
                    228,
                    229,
                    230,
                    231,
                    232,
                    233,
                    234,
                    235,
                    236,
                    237,
                    239,
                    240,
                    238,
                    241
                ],
                "tag_name": {
                    "raw_string": "minecraft:all_signs"
                }
            },
            {
                "entries": [
                    864,
                    865
                ],
                "tag_name": {
                    "raw_string": "minecraft:beehives"
                }
            },
            {
                "entries": [
                    261,
                    520,
                    752,
                    634
                ],
                "tag_name": {
                    "raw_string": "minecraft:ice"
                }
            },
            {
                "entries": [
                    174
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantment_power_provider"
                }
            },
            {
                "entries": [
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1044,
                    1088,
                    1050,
                    59,
                    37,
                    39,
                    38,
                    518,
                    448,
                    449,
                    450,
                    451,
                    452,
                    453,
                    454,
                    455,
                    456,
                    457,
                    458,
                    459,
                    460,
                    461,
                    462,
                    463,
                    262,
                    953
                ],
                "tag_name": {
                    "raw_string": "minecraft:azalea_grows_on"
                }
            },
            {
                "entries": [
                    502,
                    503,
                    504,
                    505,
                    506,
                    507,
                    508,
                    509,
                    510,
                    511,
                    512,
                    513,
                    514,
                    515,
                    516,
                    517
                ],
                "tag_name": {
                    "raw_string": "minecraft:wool_carpets"
                }
            },
            {
                "entries": [
                    629,
                    405,
                    406,
                    191,
                    330,
                    329,
                    626,
                    627
                ],
                "tag_name": {
                    "raw_string": "minecraft:crops"
                }
            },
            {
                "entries": [
                    488,
                    34,
                    356,
                    357,
                    631,
                    372,
                    632,
                    633,
                    860,
                    861,
                    153,
                    177,
                    870,
                    358,
                    322,
                    871,
                    1082
                ],
                "tag_name": {
                    "raw_string": "minecraft:dragon_immune"
                }
            },
            {
                "entries": [
                    1050,
                    59,
                    58,
                    1042,
                    331,
                    33,
                    260
                ],
                "tag_name": {
                    "raw_string": "minecraft:mangrove_roots_can_grow_through"
                }
            },
            {
                "entries": [
                    34,
                    182,
                    185,
                    357,
                    1082,
                    1085,
                    1086
                ],
                "tag_name": {
                    "raw_string": "minecraft:features_cannot_replace"
                }
            },
            {
                "entries": [
                    8,
                    11
                ],
                "tag_name": {
                    "raw_string": "minecraft:valid_spawn"
                }
            },
            {
                "entries": [
                    338,
                    11,
                    830,
                    821
                ],
                "tag_name": {
                    "raw_string": "minecraft:mushroom_grow_block"
                }
            },
            {
                "entries": [
                    204,
                    610,
                    611,
                    612,
                    613,
                    615,
                    616,
                    854,
                    855,
                    617,
                    618,
                    614
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_doors"
                }
            },
            {
                "entries": [
                    931,
                    932
                ],
                "tag_name": {
                    "raw_string": "minecraft:crystal_sound_blocks"
                }
            },
            {
                "entries": [
                    1044
                ],
                "tag_name": {
                    "raw_string": "minecraft:sniffer_egg_hatch_boost"
                }
            },
            {
                "entries": [
                    194,
                    195,
                    196,
                    197,
                    199,
                    200,
                    201,
                    856,
                    857,
                    202,
                    203,
                    198
                ],
                "tag_name": {
                    "raw_string": "minecraft:standing_signs"
                }
            },
            {
                "entries": [
                    268,
                    635,
                    34
                ],
                "tag_name": {
                    "raw_string": "minecraft:infiniburn_end"
                }
            },
            {
                "entries": [
                    817,
                    818,
                    819,
                    820
                ],
                "tag_name": {
                    "raw_string": "minecraft:warped_stems"
                }
            },
            {
                "entries": [
                    363,
                    364
                ],
                "tag_name": {
                    "raw_string": "minecraft:emerald_ores"
                }
            },
            {
                "entries": [
                    60,
                    70
                ],
                "tag_name": {
                    "raw_string": "minecraft:bamboo_blocks"
                }
            },
            {
                "entries": [
                    826,
                    827,
                    828,
                    829
                ],
                "tag_name": {
                    "raw_string": "minecraft:crimson_stems"
                }
            },
            {
                "entries": [
                    91,
                    88,
                    89,
                    95,
                    94,
                    92,
                    90,
                    97,
                    98,
                    96,
                    93,
                    154,
                    1091,
                    156,
                    157,
                    158,
                    159,
                    160,
                    161,
                    162,
                    163,
                    164,
                    165,
                    167,
                    166,
                    155,
                    1092,
                    1089,
                    130,
                    131,
                    132,
                    331,
                    332,
                    521,
                    522,
                    523,
                    524,
                    525,
                    526,
                    1048,
                    628,
                    35,
                    133,
                    134,
                    824,
                    825,
                    837
                ],
                "tag_name": {
                    "raw_string": "minecraft:replaceable_by_trees"
                }
            },
            {
                "entries": [
                    171,
                    1073,
                    44,
                    45,
                    104,
                    102,
                    103,
                    960,
                    1074,
                    964,
                    965,
                    985,
                    981,
                    969,
                    962,
                    983,
                    979,
                    967,
                    963,
                    982,
                    978,
                    966,
                    961,
                    984,
                    980,
                    968,
                    986,
                    1001,
                    997,
                    993,
                    987,
                    999,
                    995,
                    991,
                    988,
                    1000,
                    996,
                    992,
                    989,
                    998,
                    994,
                    990,
                    1034,
                    1084,
                    973,
                    972,
                    971,
                    970,
                    977,
                    976,
                    975,
                    974,
                    1018,
                    1019,
                    1020,
                    1021,
                    1022,
                    1023,
                    1024,
                    1025,
                    1026,
                    1027,
                    1028,
                    1029,
                    1030,
                    1031,
                    1032,
                    1033,
                    1010,
                    1011,
                    1013,
                    1012,
                    1014,
                    1015,
                    1017,
                    1016
                ],
                "tag_name": {
                    "raw_string": "minecraft:needs_stone_tool"
                }
            },
            {
                "entries": [
                    34,
                    182,
                    185,
                    357,
                    1082,
                    1085,
                    1086,
                    91,
                    88,
                    89,
                    95,
                    94,
                    92,
                    90,
                    97,
                    98,
                    96,
                    93,
                    55,
                    77,
                    66,
                    85,
                    56,
                    20,
                    67,
                    86,
                    49,
                    71,
                    68,
                    79,
                    53,
                    75,
                    64,
                    83,
                    51,
                    73,
                    62,
                    81,
                    52,
                    74,
                    63,
                    82,
                    50,
                    72,
                    61,
                    80,
                    57,
                    78,
                    69,
                    87,
                    54,
                    76,
                    65,
                    84,
                    826,
                    827,
                    828,
                    829,
                    817,
                    818,
                    819,
                    820
                ],
                "tag_name": {
                    "raw_string": "minecraft:lava_pool_stone_cannot_replace"
                }
            },
            {
                "entries": [
                    690,
                    691,
                    692,
                    693,
                    694,
                    695,
                    696,
                    697,
                    698,
                    699,
                    700,
                    701,
                    702,
                    703,
                    704,
                    705
                ],
                "tag_name": {
                    "raw_string": "minecraft:concrete_powder"
                }
            },
            {
                "entries": [
                    953,
                    957,
                    332,
                    339,
                    936,
                    1043
                ],
                "tag_name": {
                    "raw_string": "minecraft:inside_step_sound_blocks"
                }
            },
            {
                "entries": [
                    206,
                    126,
                    127,
                    446
                ],
                "tag_name": {
                    "raw_string": "minecraft:prevent_mob_spawning_inside"
                }
            },
            {
                "entries": [
                    636,
                    823
                ],
                "tag_name": {
                    "raw_string": "minecraft:wart_blocks"
                }
            },
            {
                "entries": [
                    518,
                    448,
                    449,
                    450,
                    451,
                    452,
                    453,
                    454,
                    455,
                    456,
                    457,
                    458,
                    459,
                    460,
                    461,
                    462,
                    463
                ],
                "tag_name": {
                    "raw_string": "minecraft:terracotta"
                }
            },
            {
                "entries": [
                    205,
                    331,
                    800,
                    833,
                    834,
                    835,
                    836,
                    1037,
                    1038
                ],
                "tag_name": {
                    "raw_string": "minecraft:climbable"
                }
            },
            {
                "entries": [
                    55,
                    77,
                    66,
                    85
                ],
                "tag_name": {
                    "raw_string": "minecraft:dark_oak_logs"
                }
            },
            {
                "entries": [
                    8,
                    0,
                    91,
                    88,
                    89,
                    95,
                    94,
                    92,
                    90,
                    97,
                    98,
                    96,
                    93,
                    55,
                    77,
                    66,
                    85,
                    56,
                    20,
                    67,
                    86,
                    49,
                    71,
                    68,
                    79,
                    53,
                    75,
                    64,
                    83,
                    51,
                    73,
                    62,
                    81,
                    52,
                    74,
                    63,
                    82,
                    50,
                    72,
                    61,
                    80,
                    57,
                    78,
                    69,
                    87,
                    54,
                    76,
                    65,
                    84,
                    826,
                    827,
                    828,
                    829,
                    817,
                    818,
                    819,
                    820
                ],
                "tag_name": {
                    "raw_string": "minecraft:parrots_spawnable_on"
                }
            },
            {
                "entries": [
                    339,
                    1045
                ],
                "tag_name": {
                    "raw_string": "minecraft:frog_prefer_jump_to"
                }
            },
            {
                "entries": [
                    726,
                    727,
                    728,
                    729,
                    730
                ],
                "tag_name": {
                    "raw_string": "minecraft:coral_plants"
                }
            },
            {
                "entries": [
                    8,
                    1,
                    260,
                    262,
                    520,
                    40
                ],
                "tag_name": {
                    "raw_string": "minecraft:goats_spawnable_on"
                }
            },
            {
                "entries": [
                    218,
                    219,
                    220,
                    221,
                    222,
                    223,
                    224,
                    225,
                    226,
                    227,
                    228,
                    229
                ],
                "tag_name": {
                    "raw_string": "minecraft:ceiling_hanging_signs"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    4,
                    6,
                    937,
                    1051,
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1044,
                    1088,
                    1050,
                    59,
                    518,
                    448,
                    449,
                    450,
                    451,
                    452,
                    453,
                    454,
                    455,
                    456,
                    457,
                    458,
                    459,
                    460,
                    461,
                    462,
                    463,
                    830,
                    821,
                    268,
                    271,
                    877,
                    37,
                    39,
                    40,
                    269,
                    270,
                    951,
                    1072,
                    264,
                    1036,
                    358,
                    559,
                    106,
                    1064,
                    1060,
                    1052,
                    1069,
                    1070,
                    1056
                ],
                "tag_name": {
                    "raw_string": "minecraft:sculk_replaceable_world_gen"
                }
            },
            {
                "entries": [
                    868,
                    368,
                    189,
                    170,
                    171
                ],
                "tag_name": {
                    "raw_string": "minecraft:beacon_base_blocks"
                }
            },
            {
                "entries": [
                    8,
                    1050,
                    58,
                    59
                ],
                "tag_name": {
                    "raw_string": "minecraft:frogs_spawnable_on"
                }
            },
            {
                "entries": [
                    641,
                    657,
                    653,
                    654,
                    651,
                    649,
                    655,
                    645,
                    650,
                    647,
                    644,
                    643,
                    648,
                    652,
                    656,
                    642,
                    646
                ],
                "tag_name": {
                    "raw_string": "minecraft:shulker_boxes"
                }
            },
            {
                "entries": [
                    488,
                    34
                ],
                "tag_name": {
                    "raw_string": "minecraft:blocks_wind_charge_explosions"
                }
            },
            {
                "entries": [
                    431,
                    432,
                    433
                ],
                "tag_name": {
                    "raw_string": "minecraft:anvil"
                }
            },
            {
                "entries": [
                    51,
                    73,
                    62,
                    81
                ],
                "tag_name": {
                    "raw_string": "minecraft:birch_logs"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    4,
                    6,
                    937,
                    1051,
                    1038,
                    1037,
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1044,
                    1088,
                    1050,
                    59
                ],
                "tag_name": {
                    "raw_string": "minecraft:moss_replaceable"
                }
            },
            {
                "entries": [
                    102,
                    103
                ],
                "tag_name": {
                    "raw_string": "minecraft:lapis_ores"
                }
            },
            {
                "entries": [
                    746,
                    747,
                    748,
                    749,
                    750
                ],
                "tag_name": {
                    "raw_string": "minecraft:wall_corals"
                }
            },
            {
                "entries": [
                    329,
                    327,
                    330,
                    328,
                    629,
                    405,
                    406,
                    626,
                    155,
                    627,
                    191
                ],
                "tag_name": {
                    "raw_string": "minecraft:maintains_farmland"
                }
            },
            {
                "entries": [
                    9,
                    10,
                    1049
                ],
                "tag_name": {
                    "raw_string": "minecraft:convertable_to_mud"
                }
            },
            {
                "entries": [
                    0,
                    757,
                    758
                ],
                "tag_name": {
                    "raw_string": "minecraft:air"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    4,
                    6,
                    937,
                    1051,
                    1038,
                    1037,
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1044,
                    1088,
                    1050,
                    59,
                    264,
                    40,
                    37
                ],
                "tag_name": {
                    "raw_string": "minecraft:lush_ground_replaceable"
                }
            },
            {
                "entries": [
                    9,
                    8,
                    11,
                    10,
                    1049,
                    1044,
                    1088,
                    1050,
                    59
                ],
                "tag_name": {
                    "raw_string": "minecraft:sniffer_diggable_block"
                }
            },
            {
                "entries": [
                    154,
                    1091,
                    156,
                    157,
                    158,
                    159,
                    160,
                    161,
                    162,
                    163,
                    164,
                    165,
                    167,
                    166,
                    155,
                    521,
                    522,
                    524,
                    523,
                    628,
                    98,
                    1041,
                    33,
                    93,
                    1043,
                    621,
                    1039
                ],
                "tag_name": {
                    "raw_string": "minecraft:bee_attractive"
                }
            },
            {
                "entries": [
                    267,
                    604,
                    606,
                    607,
                    601,
                    602,
                    603,
                    844,
                    845,
                    608,
                    609,
                    605,
                    347
                ],
                "tag_name": {
                    "raw_string": "minecraft:fences"
                }
            },
            {
                "entries": [
                    25,
                    26,
                    27,
                    28,
                    29,
                    31,
                    32,
                    1040,
                    1041,
                    33,
                    30
                ],
                "tag_name": {
                    "raw_string": "minecraft:saplings"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    12,
                    42,
                    43,
                    44,
                    45,
                    46,
                    47,
                    48,
                    102,
                    103,
                    104,
                    105,
                    106,
                    107,
                    108,
                    170,
                    171,
                    172,
                    176,
                    177,
                    182,
                    187,
                    188,
                    189,
                    193,
                    207,
                    243,
                    244,
                    255,
                    256,
                    268,
                    271,
                    272,
                    307,
                    308,
                    309,
                    310,
                    322,
                    323,
                    335,
                    336,
                    346,
                    347,
                    348,
                    350,
                    351,
                    358,
                    362,
                    363,
                    364,
                    365,
                    368,
                    435,
                    436,
                    439,
                    440,
                    441,
                    442,
                    443,
                    444,
                    445,
                    447,
                    448,
                    449,
                    450,
                    451,
                    452,
                    453,
                    454,
                    455,
                    456,
                    457,
                    458,
                    459,
                    460,
                    461,
                    462,
                    463,
                    490,
                    491,
                    492,
                    493,
                    494,
                    495,
                    496,
                    497,
                    498,
                    499,
                    518,
                    519,
                    559,
                    560,
                    561,
                    562,
                    574,
                    575,
                    576,
                    577,
                    578,
                    579,
                    580,
                    581,
                    583,
                    584,
                    585,
                    586,
                    587,
                    588,
                    589,
                    590,
                    591,
                    622,
                    623,
                    624,
                    625,
                    635,
                    637,
                    638,
                    640,
                    658,
                    659,
                    660,
                    661,
                    662,
                    663,
                    664,
                    665,
                    666,
                    667,
                    668,
                    669,
                    670,
                    671,
                    672,
                    673,
                    674,
                    675,
                    676,
                    677,
                    678,
                    679,
                    680,
                    681,
                    682,
                    683,
                    684,
                    685,
                    686,
                    687,
                    688,
                    689,
                    711,
                    712,
                    713,
                    714,
                    715,
                    716,
                    717,
                    718,
                    719,
                    720,
                    721,
                    722,
                    723,
                    724,
                    725,
                    731,
                    732,
                    733,
                    734,
                    735,
                    741,
                    742,
                    743,
                    744,
                    745,
                    760,
                    761,
                    762,
                    763,
                    764,
                    765,
                    766,
                    767,
                    768,
                    769,
                    770,
                    771,
                    772,
                    773,
                    774,
                    775,
                    776,
                    777,
                    778,
                    779,
                    780,
                    781,
                    782,
                    783,
                    784,
                    785,
                    786,
                    803,
                    804,
                    807,
                    810,
                    811,
                    812,
                    813,
                    821,
                    830,
                    868,
                    869,
                    870,
                    871,
                    876,
                    877,
                    878,
                    880,
                    881,
                    882,
                    883,
                    884,
                    885,
                    886,
                    888,
                    889,
                    890,
                    891,
                    894,
                    895,
                    896,
                    937,
                    951,
                    963,
                    962,
                    961,
                    960,
                    964,
                    965,
                    966,
                    967,
                    968,
                    969,
                    978,
                    979,
                    980,
                    981,
                    982,
                    983,
                    984,
                    985,
                    986,
                    987,
                    988,
                    989,
                    990,
                    991,
                    992,
                    993,
                    994,
                    995,
                    996,
                    997,
                    998,
                    999,
                    1000,
                    1001,
                    1034,
                    1035,
                    1036,
                    1051,
                    1052,
                    1053,
                    1054,
                    1056,
                    1057,
                    1058,
                    1060,
                    1061,
                    1062,
                    1064,
                    1065,
                    1066,
                    1068,
                    1069,
                    1070,
                    1072,
                    1073,
                    1074,
                    1075,
                    261,
                    520,
                    752,
                    135,
                    128,
                    136,
                    933,
                    936,
                    935,
                    934,
                    931,
                    932,
                    314,
                    318,
                    317,
                    1071,
                    313,
                    316,
                    315,
                    259,
                    892,
                    374,
                    375,
                    787,
                    788,
                    789,
                    790,
                    791,
                    792,
                    794,
                    795,
                    796,
                    797,
                    798,
                    799,
                    879,
                    887,
                    893,
                    1055,
                    1059,
                    1063,
                    1067,
                    793,
                    940,
                    944,
                    949,
                    344,
                    641,
                    657,
                    653,
                    654,
                    651,
                    649,
                    655,
                    645,
                    650,
                    647,
                    644,
                    643,
                    648,
                    652,
                    656,
                    642,
                    646,
                    431,
                    432,
                    433,
                    352,
                    353,
                    354,
                    355,
                    206,
                    126,
                    127,
                    446,
                    753,
                    312,
                    337,
                    582,
                    311,
                    1084,
                    938,
                    939,
                    945,
                    941,
                    942,
                    943,
                    946,
                    947,
                    948,
                    950,
                    973,
                    972,
                    971,
                    970,
                    977,
                    976,
                    975,
                    974,
                    1018,
                    1019,
                    1020,
                    1021,
                    1022,
                    1023,
                    1024,
                    1025,
                    1026,
                    1027,
                    1028,
                    1029,
                    1030,
                    1031,
                    1032,
                    1033,
                    1002,
                    1003,
                    1005,
                    1004,
                    1006,
                    1007,
                    1009,
                    1008,
                    1010,
                    1011,
                    1013,
                    1012,
                    1014,
                    1015,
                    1017,
                    1016,
                    1087,
                    341,
                    343,
                    342,
                    345
                ],
                "tag_name": {
                    "raw_string": "minecraft:mineable/pickaxe"
                }
            },
            {
                "entries": [
                    124,
                    125,
                    121,
                    122,
                    119,
                    117,
                    123,
                    113,
                    118,
                    115,
                    112,
                    111,
                    116,
                    120,
                    110,
                    114
                ],
                "tag_name": {
                    "raw_string": "minecraft:beds"
                }
            },
            {
                "entries": [
                    44,
                    45
                ],
                "tag_name": {
                    "raw_string": "minecraft:iron_ores"
                }
            },
            {
                "entries": [
                    49,
                    71,
                    68,
                    79
                ],
                "tag_name": {
                    "raw_string": "minecraft:oak_logs"
                }
            },
            {
                "entries": [
                    595,
                    593,
                    597,
                    598,
                    594,
                    334,
                    592,
                    848,
                    849,
                    599,
                    600,
                    596
                ],
                "tag_name": {
                    "raw_string": "minecraft:unstable_bottom_center"
                }
            },
            {
                "entries": [
                    204,
                    610,
                    611,
                    612,
                    613,
                    615,
                    616,
                    854,
                    855,
                    617,
                    618,
                    614,
                    1002,
                    1003,
                    1005,
                    1004,
                    1006,
                    1007,
                    1009,
                    1008,
                    244
                ],
                "tag_name": {
                    "raw_string": "minecraft:doors"
                }
            },
            {
                "entries": [
                    154,
                    1091,
                    156,
                    157,
                    158,
                    159,
                    160,
                    161,
                    162,
                    163,
                    164,
                    165,
                    167,
                    166,
                    155,
                    1092,
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1044,
                    1088,
                    1050,
                    59,
                    37,
                    39,
                    40,
                    168,
                    169,
                    173,
                    263,
                    264,
                    325,
                    277,
                    326,
                    831,
                    830,
                    837,
                    822,
                    821,
                    824
                ],
                "tag_name": {
                    "raw_string": "minecraft:enderman_holdable"
                }
            },
            {
                "entries": [
                    527,
                    528,
                    529,
                    530,
                    531,
                    532,
                    533,
                    534,
                    535,
                    536,
                    537,
                    538,
                    539,
                    540,
                    541,
                    542,
                    543,
                    544,
                    545,
                    546,
                    547,
                    548,
                    549,
                    550,
                    551,
                    552,
                    553,
                    554,
                    555,
                    556,
                    557,
                    558
                ],
                "tag_name": {
                    "raw_string": "minecraft:banners"
                }
            },
            {
                "entries": [
                    268,
                    635
                ],
                "tag_name": {
                    "raw_string": "minecraft:infiniburn_overworld"
                }
            },
            {
                "entries": [
                    376,
                    1093,
                    1094,
                    389,
                    390,
                    391,
                    392,
                    393,
                    394,
                    395,
                    396,
                    397,
                    388,
                    378,
                    379,
                    380,
                    381,
                    382,
                    384,
                    385,
                    401,
                    402,
                    403,
                    387,
                    404,
                    398,
                    399,
                    400,
                    756,
                    872,
                    873,
                    874,
                    875,
                    1076,
                    1077,
                    386,
                    383,
                    377
                ],
                "tag_name": {
                    "raw_string": "minecraft:flower_pots"
                }
            },
            {
                "entries": [
                    37,
                    39
                ],
                "tag_name": {
                    "raw_string": "minecraft:smelts_to_glass"
                }
            },
            {
                "entries": [
                    181,
                    273,
                    813,
                    274,
                    815
                ],
                "tag_name": {
                    "raw_string": "minecraft:piglin_repellents"
                }
            },
            {
                "entries": [
                    267,
                    604,
                    606,
                    607,
                    601,
                    602,
                    603,
                    844,
                    845,
                    608,
                    609,
                    605
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_fences"
                }
            },
            {
                "entries": [
                    177,
                    870,
                    868,
                    871,
                    869,
                    189,
                    187,
                    188,
                    363,
                    364,
                    368,
                    170,
                    1075,
                    42,
                    43,
                    255,
                    256,
                    171,
                    1073,
                    44,
                    45,
                    104,
                    102,
                    103,
                    960,
                    1074,
                    964,
                    965,
                    985,
                    981,
                    969,
                    962,
                    983,
                    979,
                    967,
                    963,
                    982,
                    978,
                    966,
                    961,
                    984,
                    980,
                    968,
                    986,
                    1001,
                    997,
                    993,
                    987,
                    999,
                    995,
                    991,
                    988,
                    1000,
                    996,
                    992,
                    989,
                    998,
                    994,
                    990,
                    1034,
                    1084,
                    973,
                    972,
                    971,
                    970,
                    977,
                    976,
                    975,
                    974,
                    1018,
                    1019,
                    1020,
                    1021,
                    1022,
                    1023,
                    1024,
                    1025,
                    1026,
                    1027,
                    1028,
                    1029,
                    1030,
                    1031,
                    1032,
                    1033,
                    1010,
                    1011,
                    1013,
                    1012,
                    1014,
                    1015,
                    1017,
                    1016
                ],
                "tag_name": {
                    "raw_string": "minecraft:incorrect_for_wooden_tool"
                }
            },
            {
                "entries": [
                    177,
                    870,
                    868,
                    871,
                    869
                ],
                "tag_name": {
                    "raw_string": "minecraft:incorrect_for_iron_tool"
                }
            },
            {
                "entries": [
                    338
                ],
                "tag_name": {
                    "raw_string": "minecraft:mooshrooms_spawnable_on"
                }
            },
            {
                "entries": [
                    178,
                    273,
                    257,
                    367,
                    194,
                    195,
                    196,
                    197,
                    199,
                    200,
                    201,
                    856,
                    857,
                    202,
                    203,
                    198,
                    208,
                    209,
                    210,
                    211,
                    213,
                    214,
                    215,
                    858,
                    859,
                    216,
                    217,
                    212,
                    527,
                    528,
                    529,
                    530,
                    531,
                    532,
                    533,
                    534,
                    535,
                    536,
                    537,
                    538,
                    539,
                    540,
                    541,
                    542,
                    543,
                    544,
                    545,
                    546,
                    547,
                    548,
                    549,
                    550,
                    551,
                    552,
                    553,
                    554,
                    555,
                    556,
                    557,
                    558,
                    435,
                    436,
                    245,
                    246,
                    247,
                    248,
                    249,
                    251,
                    252,
                    842,
                    843,
                    253,
                    254,
                    250,
                    243,
                    891
                ],
                "tag_name": {
                    "raw_string": "minecraft:wall_post_override"
                }
            },
            {
                "entries": [
                    0,
                    35,
                    36,
                    130,
                    131,
                    132,
                    133,
                    134,
                    180,
                    181,
                    260,
                    331,
                    332,
                    333,
                    489,
                    525,
                    526,
                    639,
                    757,
                    758,
                    759,
                    824,
                    825,
                    837,
                    1048
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantment_power_transmitter"
                }
            },
            {
                "entries": [
                    276,
                    356,
                    631
                ],
                "tag_name": {
                    "raw_string": "minecraft:portals"
                }
            },
            {
                "entries": [
                    37,
                    39,
                    38,
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1044,
                    1088,
                    1050,
                    59,
                    755,
                    754,
                    40,
                    41
                ],
                "tag_name": {
                    "raw_string": "minecraft:bamboo_plantable_on"
                }
            },
            {
                "entries": [
                    261
                ],
                "tag_name": {
                    "raw_string": "minecraft:polar_bears_spawnable_on_alternate"
                }
            },
            {
                "entries": [
                    352,
                    353,
                    354,
                    355
                ],
                "tag_name": {
                    "raw_string": "minecraft:cauldrons"
                }
            },
            {
                "entries": [
                    264,
                    1044,
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1088,
                    1050,
                    59,
                    192
                ],
                "tag_name": {
                    "raw_string": "minecraft:big_dripleaf_placeable"
                }
            },
            {
                "entries": [
                    91,
                    88,
                    89,
                    95,
                    94,
                    92,
                    90,
                    97,
                    98,
                    96,
                    93,
                    25,
                    26,
                    27,
                    28,
                    29,
                    31,
                    32,
                    1040,
                    1041,
                    33,
                    30,
                    629,
                    405,
                    406,
                    191,
                    330,
                    329,
                    626,
                    627,
                    130,
                    131,
                    132,
                    331,
                    332,
                    521,
                    522,
                    523,
                    524,
                    525,
                    526,
                    1048,
                    628,
                    168,
                    169,
                    265,
                    325,
                    277,
                    278,
                    326,
                    327,
                    328,
                    339,
                    361,
                    816,
                    1037,
                    1038,
                    1039,
                    1043,
                    1045,
                    1046,
                    1047,
                    349,
                    822,
                    824,
                    825,
                    831,
                    833,
                    834,
                    835,
                    836,
                    837,
                    620,
                    621
                ],
                "tag_name": {
                    "raw_string": "minecraft:sword_efficient"
                }
            },
            {
                "entries": [
                    435,
                    436,
                    245,
                    246,
                    247,
                    248,
                    249,
                    251,
                    252,
                    842,
                    843,
                    253,
                    254,
                    250,
                    243,
                    891
                ],
                "tag_name": {
                    "raw_string": "minecraft:pressure_plates"
                }
            },
            {
                "entries": [
                    137,
                    138,
                    139,
                    140,
                    141,
                    142,
                    143,
                    144,
                    145,
                    146,
                    147,
                    148,
                    149,
                    150,
                    151,
                    152,
                    502,
                    503,
                    504,
                    505,
                    506,
                    507,
                    508,
                    509,
                    510,
                    511,
                    512,
                    513,
                    514,
                    515,
                    516,
                    517
                ],
                "tag_name": {
                    "raw_string": "minecraft:dampens_vibrations"
                }
            },
            {
                "entries": [
                    57,
                    78,
                    69,
                    87
                ],
                "tag_name": {
                    "raw_string": "minecraft:mangrove_logs"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    4,
                    6,
                    937,
                    1051,
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1044,
                    1088,
                    1050,
                    59,
                    37,
                    39,
                    38,
                    518,
                    448,
                    449,
                    450,
                    451,
                    452,
                    453,
                    454,
                    455,
                    456,
                    457,
                    458,
                    459,
                    460,
                    461,
                    462,
                    463,
                    44,
                    45,
                    964,
                    965,
                    260,
                    262,
                    953,
                    35,
                    40,
                    41,
                    106,
                    559,
                    951,
                    520,
                    1073,
                    1074
                ],
                "tag_name": {
                    "raw_string": "minecraft:overworld_carver_replaceables"
                }
            },
            {
                "entries": [
                    866,
                    269,
                    1050
                ],
                "tag_name": {
                    "raw_string": "minecraft:snow_layer_can_survive_on"
                }
            },
            {
                "entries": [
                    52,
                    74,
                    63,
                    82
                ],
                "tag_name": {
                    "raw_string": "minecraft:jungle_logs"
                }
            },
            {
                "entries": [
                    37,
                    39,
                    38,
                    518,
                    448,
                    449,
                    450,
                    451,
                    452,
                    453,
                    454,
                    455,
                    456,
                    457,
                    458,
                    459,
                    460,
                    461,
                    462,
                    463,
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1044,
                    1088,
                    1050,
                    59
                ],
                "tag_name": {
                    "raw_string": "minecraft:dead_bush_may_place_on"
                }
            },
            {
                "entries": [
                    931
                ],
                "tag_name": {
                    "raw_string": "minecraft:vibration_resonators"
                }
            },
            {
                "entries": [
                    230,
                    231,
                    232,
                    233,
                    234,
                    235,
                    236,
                    237,
                    239,
                    240,
                    238,
                    241
                ],
                "tag_name": {
                    "raw_string": "minecraft:wall_hanging_signs"
                }
            },
            {
                "entries": [
                    261,
                    520,
                    488
                ],
                "tag_name": {
                    "raw_string": "minecraft:snow_layer_cannot_survive_on"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    4,
                    6,
                    937,
                    1051,
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1044,
                    1088,
                    1050,
                    59,
                    518,
                    448,
                    449,
                    450,
                    451,
                    452,
                    453,
                    454,
                    455,
                    456,
                    457,
                    458,
                    459,
                    460,
                    461,
                    462,
                    463,
                    830,
                    821,
                    268,
                    271,
                    877,
                    37,
                    39,
                    40,
                    269,
                    270,
                    951,
                    1072,
                    264,
                    1036,
                    358,
                    559,
                    106
                ],
                "tag_name": {
                    "raw_string": "minecraft:sculk_replaceable"
                }
            },
            {
                "entries": [
                    184,
                    369,
                    370,
                    371,
                    480,
                    482,
                    483,
                    850,
                    851,
                    484,
                    485,
                    481
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_stairs"
                }
            },
            {
                "entries": [
                    50,
                    72,
                    61,
                    80
                ],
                "tag_name": {
                    "raw_string": "minecraft:spruce_logs"
                }
            },
            {
                "entries": [
                    1051,
                    1064,
                    1060,
                    1066,
                    1062,
                    1065,
                    1063,
                    1067,
                    1052,
                    1069,
                    1070,
                    144
                ],
                "tag_name": {
                    "raw_string": "minecraft:ancient_city_replaceable"
                }
            },
            {
                "entries": [
                    194,
                    195,
                    196,
                    197,
                    199,
                    200,
                    201,
                    856,
                    857,
                    202,
                    203,
                    198,
                    208,
                    209,
                    210,
                    211,
                    213,
                    214,
                    215,
                    858,
                    859,
                    216,
                    217,
                    212
                ],
                "tag_name": {
                    "raw_string": "minecraft:signs"
                }
            },
            {
                "entries": [
                    1050,
                    59,
                    58,
                    96,
                    57,
                    33,
                    1042,
                    331
                ],
                "tag_name": {
                    "raw_string": "minecraft:mangrove_logs_can_grow_through"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    4,
                    6,
                    937,
                    1051
                ],
                "tag_name": {
                    "raw_string": "minecraft:base_stone_overworld"
                }
            },
            {
                "entries": [
                    407,
                    408,
                    409,
                    410,
                    411,
                    413,
                    414,
                    852,
                    853,
                    415,
                    416,
                    412
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_buttons"
                }
            },
            {
                "entries": [
                    264
                ],
                "tag_name": {
                    "raw_string": "minecraft:axolotls_spawnable_on"
                }
            },
            {
                "entries": [
                    269,
                    270
                ],
                "tag_name": {
                    "raw_string": "minecraft:wither_summon_base_blocks"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    4,
                    6,
                    937,
                    1051
                ],
                "tag_name": {
                    "raw_string": "minecraft:dripstone_replaceable_blocks"
                }
            },
            {
                "entries": [
                    307,
                    308,
                    309,
                    310
                ],
                "tag_name": {
                    "raw_string": "minecraft:stone_bricks"
                }
            },
            {
                "entries": [
                    822,
                    873,
                    276,
                    871
                ],
                "tag_name": {
                    "raw_string": "minecraft:hoglin_repellents"
                }
            },
            {
                "entries": [
                    180,
                    181
                ],
                "tag_name": {
                    "raw_string": "minecraft:fire"
                }
            },
            {
                "entries": [
                    109,
                    328,
                    327,
                    1040,
                    755,
                    802,
                    864,
                    865,
                    629,
                    1046,
                    1045,
                    174,
                    319,
                    168,
                    814,
                    405,
                    805,
                    277,
                    1038,
                    1037,
                    185,
                    621,
                    620,
                    361,
                    862,
                    190,
                    831,
                    438,
                    132,
                    131,
                    806,
                    332,
                    130,
                    1048,
                    278,
                    266,
                    205,
                    526,
                    808,
                    339,
                    801,
                    330,
                    326,
                    321,
                    349,
                    406,
                    329,
                    325,
                    320,
                    169,
                    800,
                    1047,
                    809,
                    815,
                    1039,
                    265,
                    816,
                    525,
                    434,
                    836,
                    835,
                    331,
                    822,
                    834,
                    833,
                    191,
                    527,
                    528,
                    529,
                    530,
                    531,
                    532,
                    533,
                    534,
                    535,
                    536,
                    537,
                    538,
                    539,
                    540,
                    541,
                    542,
                    543,
                    544,
                    545,
                    546,
                    547,
                    548,
                    549,
                    550,
                    551,
                    552,
                    553,
                    554,
                    555,
                    556,
                    557,
                    558,
                    595,
                    593,
                    597,
                    598,
                    594,
                    334,
                    592,
                    848,
                    849,
                    599,
                    600,
                    596,
                    55,
                    77,
                    66,
                    85,
                    56,
                    20,
                    67,
                    86,
                    49,
                    71,
                    68,
                    79,
                    53,
                    75,
                    64,
                    83,
                    51,
                    73,
                    62,
                    81,
                    52,
                    74,
                    63,
                    82,
                    50,
                    72,
                    61,
                    80,
                    57,
                    78,
                    69,
                    87,
                    54,
                    76,
                    65,
                    84,
                    826,
                    827,
                    828,
                    829,
                    817,
                    818,
                    819,
                    820,
                    13,
                    14,
                    15,
                    16,
                    17,
                    19,
                    21,
                    838,
                    839,
                    22,
                    23,
                    18,
                    25,
                    26,
                    27,
                    28,
                    29,
                    31,
                    32,
                    1041,
                    33,
                    30,
                    194,
                    195,
                    196,
                    197,
                    199,
                    200,
                    201,
                    856,
                    857,
                    202,
                    203,
                    198,
                    208,
                    209,
                    210,
                    211,
                    213,
                    214,
                    215,
                    858,
                    859,
                    216,
                    217,
                    212,
                    407,
                    408,
                    409,
                    410,
                    411,
                    413,
                    414,
                    852,
                    853,
                    415,
                    416,
                    412,
                    204,
                    610,
                    611,
                    612,
                    613,
                    615,
                    616,
                    854,
                    855,
                    617,
                    618,
                    614,
                    267,
                    604,
                    606,
                    607,
                    601,
                    602,
                    603,
                    844,
                    845,
                    608,
                    609,
                    605,
                    245,
                    246,
                    247,
                    248,
                    249,
                    251,
                    252,
                    842,
                    843,
                    253,
                    254,
                    250,
                    563,
                    564,
                    565,
                    566,
                    567,
                    569,
                    570,
                    840,
                    841,
                    571,
                    572,
                    568,
                    184,
                    369,
                    370,
                    371,
                    480,
                    482,
                    483,
                    850,
                    851,
                    484,
                    485,
                    481,
                    301,
                    299,
                    303,
                    304,
                    300,
                    297,
                    298,
                    846,
                    847,
                    305,
                    306,
                    302,
                    58,
                    218,
                    219,
                    220,
                    221,
                    222,
                    223,
                    224,
                    225,
                    226,
                    227,
                    228,
                    229,
                    230,
                    231,
                    232,
                    233,
                    234,
                    235,
                    236,
                    237,
                    239,
                    240,
                    238,
                    241,
                    24,
                    573,
                    486,
                    60,
                    70,
                    175,
                    183
                ],
                "tag_name": {
                    "raw_string": "minecraft:mineable/axe"
                }
            },
            {
                "entries": [
                    268,
                    271,
                    877
                ],
                "tag_name": {
                    "raw_string": "minecraft:base_stone_nether"
                }
            },
            {
                "entries": [
                    177,
                    870,
                    868,
                    871,
                    869
                ],
                "tag_name": {
                    "raw_string": "minecraft:needs_diamond_tool"
                }
            },
            {
                "entries": [
                    208,
                    209,
                    210,
                    211,
                    213,
                    214,
                    215,
                    858,
                    859,
                    216,
                    217,
                    212
                ],
                "tag_name": {
                    "raw_string": "minecraft:wall_signs"
                }
            },
            {
                "entries": [
                    177,
                    870,
                    868,
                    871,
                    869,
                    189,
                    187,
                    188,
                    363,
                    364,
                    368,
                    170,
                    1075,
                    42,
                    43,
                    255,
                    256
                ],
                "tag_name": {
                    "raw_string": "minecraft:incorrect_for_stone_tool"
                }
            },
            {
                "entries": [
                    563,
                    564,
                    565,
                    566,
                    567,
                    569,
                    570,
                    840,
                    841,
                    571,
                    572,
                    568,
                    573,
                    574,
                    575,
                    581,
                    576,
                    587,
                    584,
                    585,
                    580,
                    579,
                    583,
                    578,
                    497,
                    498,
                    499,
                    774,
                    775,
                    776,
                    777,
                    778,
                    779,
                    780,
                    781,
                    782,
                    783,
                    784,
                    785,
                    786,
                    577,
                    586,
                    880,
                    885,
                    890,
                    1054,
                    1058,
                    1062,
                    1066,
                    999,
                    1000,
                    1001,
                    982,
                    983,
                    984,
                    985,
                    998,
                    582,
                    938,
                    942,
                    947,
                    343
                ],
                "tag_name": {
                    "raw_string": "minecraft:slabs"
                }
            },
            {
                "entries": [
                    8
                ],
                "tag_name": {
                    "raw_string": "minecraft:animals_spawnable_on"
                }
            },
            {
                "entries": [
                    170,
                    802,
                    185,
                    365,
                    888,
                    434,
                    1075,
                    641,
                    657,
                    653,
                    654,
                    651,
                    649,
                    655,
                    645,
                    650,
                    647,
                    644,
                    643,
                    648,
                    652,
                    656,
                    642,
                    646,
                    42,
                    48,
                    43
                ],
                "tag_name": {
                    "raw_string": "minecraft:guarded_by_piglins"
                }
            },
            {
                "entries": [
                    264,
                    9,
                    10,
                    11,
                    192,
                    8,
                    40,
                    338,
                    37,
                    39,
                    262,
                    260,
                    269,
                    630,
                    270,
                    1049,
                    59,
                    1050,
                    38,
                    41,
                    690,
                    691,
                    692,
                    693,
                    694,
                    695,
                    696,
                    697,
                    698,
                    699,
                    700,
                    701,
                    702,
                    703,
                    704,
                    705
                ],
                "tag_name": {
                    "raw_string": "minecraft:mineable/shovel"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    4,
                    6,
                    937,
                    1051,
                    268,
                    271,
                    877,
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1044,
                    1088,
                    1050,
                    59,
                    830,
                    821,
                    636,
                    823,
                    269,
                    270
                ],
                "tag_name": {
                    "raw_string": "minecraft:nether_carver_replaceables"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    4,
                    6
                ],
                "tag_name": {
                    "raw_string": "minecraft:stone_ore_replaceables"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    4,
                    6,
                    937,
                    1051
                ],
                "tag_name": {
                    "raw_string": "minecraft:bats_spawnable_on"
                }
            },
            {
                "entries": [
                    301,
                    299,
                    303,
                    304,
                    300,
                    297,
                    298,
                    846,
                    847,
                    305,
                    306,
                    302,
                    490,
                    1010,
                    1011,
                    1013,
                    1012,
                    1014,
                    1015,
                    1017,
                    1016
                ],
                "tag_name": {
                    "raw_string": "minecraft:trapdoors"
                }
            },
            {
                "entries": [
                    255,
                    256
                ],
                "tag_name": {
                    "raw_string": "minecraft:redstone_ores"
                }
            },
            {
                "entries": [
                    54,
                    76,
                    65,
                    84
                ],
                "tag_name": {
                    "raw_string": "minecraft:cherry_logs"
                }
            },
            {
                "entries": [
                    205,
                    331,
                    800,
                    833,
                    834,
                    835,
                    836,
                    1037,
                    1038,
                    816,
                    129
                ],
                "tag_name": {
                    "raw_string": "minecraft:fall_damage_resetting"
                }
            },
            {
                "entries": [
                    407,
                    408,
                    409,
                    410,
                    411,
                    413,
                    414,
                    852,
                    853,
                    415,
                    416,
                    412,
                    259,
                    892
                ],
                "tag_name": {
                    "raw_string": "minecraft:buttons"
                }
            },
            {
                "entries": [
                    726,
                    727,
                    728,
                    729,
                    730,
                    736,
                    737,
                    738,
                    739,
                    740
                ],
                "tag_name": {
                    "raw_string": "minecraft:corals"
                }
            },
            {
                "entries": [
                    154,
                    1091,
                    156,
                    157,
                    158,
                    159,
                    160,
                    161,
                    162,
                    163,
                    164,
                    165,
                    167,
                    166,
                    155,
                    1092,
                    521,
                    522,
                    524,
                    523,
                    628,
                    98,
                    1041,
                    33,
                    93,
                    1043,
                    621,
                    1039
                ],
                "tag_name": {
                    "raw_string": "minecraft:flowers"
                }
            },
            {
                "entries": [
                    502,
                    503,
                    504,
                    505,
                    506,
                    507,
                    508,
                    509,
                    510,
                    511,
                    512,
                    513,
                    514,
                    515,
                    516,
                    517,
                    1042,
                    1089,
                    260,
                    825,
                    824,
                    837,
                    333
                ],
                "tag_name": {
                    "raw_string": "minecraft:combination_step_sound_blocks"
                }
            },
            {
                "entries": [
                    8,
                    260,
                    262,
                    37
                ],
                "tag_name": {
                    "raw_string": "minecraft:rabbits_spawnable_on"
                }
            },
            {
                "entries": [
                    13,
                    14,
                    15,
                    16,
                    17,
                    19,
                    21,
                    838,
                    839,
                    22,
                    23,
                    18
                ],
                "tag_name": {
                    "raw_string": "minecraft:planks"
                }
            },
            {
                "entries": [
                    864,
                    865
                ],
                "tag_name": {
                    "raw_string": "minecraft:does_not_block_hoppers"
                }
            },
            {
                "entries": [
                    259,
                    892
                ],
                "tag_name": {
                    "raw_string": "minecraft:stone_buttons"
                }
            },
            {
                "entries": [
                    269,
                    270
                ],
                "tag_name": {
                    "raw_string": "minecraft:soul_speed_blocks"
                }
            },
            {
                "entries": [
                    206,
                    126,
                    127,
                    446
                ],
                "tag_name": {
                    "raw_string": "minecraft:rails"
                }
            },
            {
                "entries": [
                    187,
                    188
                ],
                "tag_name": {
                    "raw_string": "minecraft:diamond_ores"
                }
            },
            {
                "entries": [
                    34,
                    35,
                    36,
                    261,
                    520,
                    752
                ],
                "tag_name": {
                    "raw_string": "minecraft:geode_invalid_blocks"
                }
            },
            {
                "entries": [
                    518,
                    448,
                    452,
                    449,
                    462,
                    460,
                    456
                ],
                "tag_name": {
                    "raw_string": "minecraft:badlands_terracotta"
                }
            },
            {
                "entries": [
                    53,
                    51,
                    49,
                    52,
                    50,
                    55,
                    56,
                    57,
                    54
                ],
                "tag_name": {
                    "raw_string": "minecraft:overworld_natural_logs"
                }
            },
            {
                "entries": [
                    218,
                    219,
                    220,
                    221,
                    222,
                    223,
                    224,
                    225,
                    226,
                    227,
                    228,
                    229,
                    230,
                    231,
                    232,
                    233,
                    234,
                    235,
                    236,
                    237,
                    239,
                    240,
                    238,
                    241
                ],
                "tag_name": {
                    "raw_string": "minecraft:all_hanging_signs"
                }
            },
            {
                "entries": [
                    91,
                    88,
                    89,
                    95,
                    94,
                    92,
                    90,
                    97,
                    98,
                    96,
                    93
                ],
                "tag_name": {
                    "raw_string": "minecraft:leaves"
                }
            },
            {
                "entries": [
                    1051,
                    937
                ],
                "tag_name": {
                    "raw_string": "minecraft:deepslate_ore_replaceables"
                }
            },
            {
                "entries": [
                    374,
                    375,
                    787,
                    788,
                    789,
                    790,
                    791,
                    792,
                    794,
                    795,
                    796,
                    797,
                    798,
                    799,
                    879,
                    887,
                    893,
                    1055,
                    1059,
                    1063,
                    1067,
                    793,
                    940,
                    944,
                    949,
                    344
                ],
                "tag_name": {
                    "raw_string": "minecraft:walls"
                }
            },
            {
                "entries": [
                    1038,
                    1037
                ],
                "tag_name": {
                    "raw_string": "minecraft:cave_vines"
                }
            },
            {
                "entries": [
                    716,
                    717,
                    718,
                    719,
                    720
                ],
                "tag_name": {
                    "raw_string": "minecraft:coral_blocks"
                }
            },
            {
                "entries": [
                    36
                ],
                "tag_name": {
                    "raw_string": "minecraft:strider_warm_blocks"
                }
            },
            {
                "entries": [
                    595,
                    593,
                    597,
                    598,
                    594,
                    334,
                    592,
                    848,
                    849,
                    599,
                    600,
                    596
                ],
                "tag_name": {
                    "raw_string": "minecraft:fence_gates"
                }
            },
            {
                "entries": [
                    629,
                    405,
                    406,
                    191,
                    330,
                    329,
                    626,
                    627,
                    816,
                    1037,
                    1038
                ],
                "tag_name": {
                    "raw_string": "minecraft:bee_growables"
                }
            },
            {
                "entries": [
                    177,
                    870,
                    868,
                    871,
                    869,
                    189,
                    187,
                    188,
                    363,
                    364,
                    368,
                    170,
                    1075,
                    42,
                    43,
                    255,
                    256,
                    171,
                    1073,
                    44,
                    45,
                    104,
                    102,
                    103,
                    960,
                    1074,
                    964,
                    965,
                    985,
                    981,
                    969,
                    962,
                    983,
                    979,
                    967,
                    963,
                    982,
                    978,
                    966,
                    961,
                    984,
                    980,
                    968,
                    986,
                    1001,
                    997,
                    993,
                    987,
                    999,
                    995,
                    991,
                    988,
                    1000,
                    996,
                    992,
                    989,
                    998,
                    994,
                    990,
                    1034,
                    1084,
                    973,
                    972,
                    971,
                    970,
                    977,
                    976,
                    975,
                    974,
                    1018,
                    1019,
                    1020,
                    1021,
                    1022,
                    1023,
                    1024,
                    1025,
                    1026,
                    1027,
                    1028,
                    1029,
                    1030,
                    1031,
                    1032,
                    1033,
                    1010,
                    1011,
                    1013,
                    1012,
                    1014,
                    1015,
                    1017,
                    1016
                ],
                "tag_name": {
                    "raw_string": "minecraft:incorrect_for_gold_tool"
                }
            },
            {
                "entries": [
                    488,
                    34,
                    356,
                    357,
                    631,
                    372,
                    632,
                    633,
                    860,
                    861,
                    153,
                    489,
                    1082
                ],
                "tag_name": {
                    "raw_string": "minecraft:wither_immune"
                }
            },
            {
                "entries": [
                    245,
                    246,
                    247,
                    248,
                    249,
                    251,
                    252,
                    842,
                    843,
                    253,
                    254,
                    250
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_pressure_plates"
                }
            },
            {
                "entries": [
                    8,
                    518,
                    448,
                    452,
                    449,
                    462,
                    460,
                    456,
                    39,
                    10
                ],
                "tag_name": {
                    "raw_string": "minecraft:armadillo_spawnable_on"
                }
            },
            {
                "entries": [
                    53,
                    75,
                    64,
                    83
                ],
                "tag_name": {
                    "raw_string": "minecraft:acacia_logs"
                }
            },
            {
                "entries": [],
                "tag_name": {
                    "raw_string": "minecraft:incorrect_for_netherite_tool"
                }
            },
            {
                "entries": [
                    897,
                    898,
                    899,
                    900,
                    901,
                    902,
                    903,
                    904,
                    905,
                    906,
                    907,
                    908,
                    909,
                    910,
                    911,
                    912,
                    913
                ],
                "tag_name": {
                    "raw_string": "minecraft:candles"
                }
            },
            {
                "entries": [
                    489,
                    180,
                    181
                ],
                "tag_name": {
                    "raw_string": "minecraft:dragon_transparent"
                }
            },
            {
                "entries": [
                    133,
                    726,
                    727,
                    728,
                    729,
                    730,
                    736,
                    737,
                    738,
                    739,
                    740,
                    746,
                    747,
                    748,
                    749,
                    750
                ],
                "tag_name": {
                    "raw_string": "minecraft:underwater_bonemeals"
                }
            },
            {
                "entries": [
                    243,
                    891
                ],
                "tag_name": {
                    "raw_string": "minecraft:stone_pressure_plates"
                }
            },
            {
                "entries": [
                    101,
                    281,
                    282,
                    283,
                    284,
                    285,
                    286,
                    287,
                    288,
                    289,
                    290,
                    291,
                    292,
                    293,
                    294,
                    295,
                    296,
                    952
                ],
                "tag_name": {
                    "raw_string": "minecraft:impermeable"
                }
            },
            {
                "entries": [
                    964,
                    965
                ],
                "tag_name": {
                    "raw_string": "minecraft:copper_ores"
                }
            },
            {
                "entries": [
                    830,
                    821
                ],
                "tag_name": {
                    "raw_string": "minecraft:nylium"
                }
            },
            {
                "entries": [
                    260,
                    262,
                    953
                ],
                "tag_name": {
                    "raw_string": "minecraft:snow"
                }
            },
            {
                "entries": [
                    37,
                    39,
                    38
                ],
                "tag_name": {
                    "raw_string": "minecraft:sand"
                }
            },
            {
                "entries": [
                    42,
                    48,
                    43
                ],
                "tag_name": {
                    "raw_string": "minecraft:gold_ores"
                }
            },
            {
                "entries": [
                    264,
                    1044
                ],
                "tag_name": {
                    "raw_string": "minecraft:small_dripleaf_placeable"
                }
            },
            {
                "entries": [],
                "tag_name": {
                    "raw_string": "minecraft:incorrect_for_diamond_tool"
                }
            },
            {
                "entries": [
                    55,
                    77,
                    66,
                    85,
                    56,
                    20,
                    67,
                    86,
                    49,
                    71,
                    68,
                    79,
                    53,
                    75,
                    64,
                    83,
                    51,
                    73,
                    62,
                    81,
                    52,
                    74,
                    63,
                    82,
                    50,
                    72,
                    61,
                    80,
                    57,
                    78,
                    69,
                    87,
                    54,
                    76,
                    65,
                    84
                ],
                "tag_name": {
                    "raw_string": "minecraft:logs_that_burn"
                }
            },
            {
                "entries": [
                    55,
                    77,
                    66,
                    85,
                    56,
                    20,
                    67,
                    86,
                    49,
                    71,
                    68,
                    79,
                    53,
                    75,
                    64,
                    83,
                    51,
                    73,
                    62,
                    81,
                    52,
                    74,
                    63,
                    82,
                    50,
                    72,
                    61,
                    80,
                    57,
                    78,
                    69,
                    87,
                    54,
                    76,
                    65,
                    84,
                    826,
                    827,
                    828,
                    829,
                    817,
                    818,
                    819,
                    820,
                    91,
                    88,
                    89,
                    95,
                    94,
                    92,
                    90,
                    97,
                    98,
                    96,
                    93,
                    636,
                    823
                ],
                "tag_name": {
                    "raw_string": "minecraft:completes_find_tree_tutorial"
                }
            },
            {
                "entries": [
                    37,
                    39,
                    38,
                    690,
                    691,
                    692,
                    693,
                    694,
                    695,
                    696,
                    697,
                    698,
                    699,
                    700,
                    701,
                    702,
                    703,
                    704,
                    705
                ],
                "tag_name": {
                    "raw_string": "minecraft:camel_sand_step_sound_blocks"
                }
            },
            {
                "entries": [
                    636,
                    823,
                    501,
                    708,
                    863,
                    832,
                    99,
                    100,
                    91,
                    88,
                    89,
                    95,
                    94,
                    92,
                    90,
                    97,
                    98,
                    96,
                    954,
                    955,
                    1044,
                    1042,
                    1088,
                    1089,
                    956,
                    958,
                    957,
                    959,
                    1043,
                    93
                ],
                "tag_name": {
                    "raw_string": "minecraft:mineable/hoe"
                }
            },
            {
                "entries": [
                    0,
                    35,
                    36,
                    130,
                    131,
                    132,
                    133,
                    134,
                    180,
                    181,
                    260,
                    331,
                    332,
                    333,
                    489,
                    525,
                    526,
                    639,
                    757,
                    758,
                    759,
                    824,
                    825,
                    837,
                    1048
                ],
                "tag_name": {
                    "raw_string": "minecraft:replaceable"
                }
            },
            {
                "entries": [
                    9,
                    8,
                    11,
                    10,
                    338,
                    1049,
                    1044,
                    1088,
                    1050,
                    59
                ],
                "tag_name": {
                    "raw_string": "minecraft:dirt"
                }
            },
            {
                "entries": [
                    914,
                    915,
                    916,
                    917,
                    918,
                    919,
                    920,
                    921,
                    922,
                    923,
                    924,
                    925,
                    926,
                    927,
                    928,
                    929,
                    930
                ],
                "tag_name": {
                    "raw_string": "minecraft:candle_cakes"
                }
            },
            {
                "entries": [
                    189,
                    187,
                    188,
                    363,
                    364,
                    368,
                    170,
                    1075,
                    42,
                    43,
                    255,
                    256
                ],
                "tag_name": {
                    "raw_string": "minecraft:needs_iron_tool"
                }
            }
        ],
        "minecraft:cat_variant": [
            {
                "entries": [
                    0,
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8,
                    9
                ],
                "tag_name": {
                    "raw_string": "minecraft:default_spawns"
                }
            },
            {
                "entries": [
                    0,
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8,
                    9,
                    10
                ],
                "tag_name": {
                    "raw_string": "minecraft:full_moon_spawns"
                }
            }
        ],
        "minecraft:damage_type": [
            {
                "entries": [
                    39
                ],
                "tag_name": {
                    "raw_string": "minecraft:bypasses_effects"
                }
            },
            {
                "entries": [
                    10,
                    8,
                    38
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_fall"
                }
            },
            {
                "entries": [
                    21,
                    3
                ],
                "tag_name": {
                    "raw_string": "minecraft:ignites_armor_stands"
                }
            },
            {
                "entries": [
                    27,
                    23,
                    36,
                    42
                ],
                "tag_name": {
                    "raw_string": "minecraft:witch_resistant_to"
                }
            },
            {
                "entries": [
                    27,
                    42,
                    15,
                    9,
                    35,
                    1
                ],
                "tag_name": {
                    "raw_string": "minecraft:avoids_guardian_thorns"
                }
            },
            {
                "entries": [
                    32,
                    19
                ],
                "tag_name": {
                    "raw_string": "minecraft:bypasses_resistance"
                }
            },
            {
                "entries": [
                    31
                ],
                "tag_name": {
                    "raw_string": "minecraft:burns_armor_stands"
                }
            },
            {
                "entries": [
                    2,
                    17,
                    20,
                    21,
                    24,
                    25,
                    31
                ],
                "tag_name": {
                    "raw_string": "minecraft:panic_environmental_causes"
                }
            },
            {
                "entries": [
                    26
                ],
                "tag_name": {
                    "raw_string": "minecraft:mace_smash"
                }
            },
            {
                "entries": [
                    11,
                    12,
                    13
                ],
                "tag_name": {
                    "raw_string": "minecraft:damages_helmet"
                }
            },
            {
                "entries": [
                    32,
                    19,
                    4,
                    6,
                    7,
                    17,
                    22,
                    23,
                    27,
                    33,
                    39,
                    42,
                    47
                ],
                "tag_name": {
                    "raw_string": "minecraft:bypasses_wolf_armor"
                }
            },
            {
                "entries": [
                    31,
                    22,
                    4,
                    6,
                    16,
                    18,
                    47,
                    5,
                    39,
                    10,
                    8,
                    17,
                    38,
                    27,
                    23,
                    32,
                    19,
                    36,
                    33,
                    11,
                    13
                ],
                "tag_name": {
                    "raw_string": "minecraft:bypasses_shield"
                }
            },
            {
                "entries": [
                    3,
                    20
                ],
                "tag_name": {
                    "raw_string": "minecraft:burn_from_stepping"
                }
            },
            {
                "entries": [
                    6
                ],
                "tag_name": {
                    "raw_string": "minecraft:no_impact"
                }
            },
            {
                "entries": [
                    17
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_freezing"
                }
            },
            {
                "entries": [
                    0,
                    44,
                    30,
                    45,
                    14,
                    48,
                    43,
                    46
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_projectile"
                }
            },
            {
                "entries": [
                    36
                ],
                "tag_name": {
                    "raw_string": "minecraft:bypasses_enchantments"
                }
            },
            {
                "entries": [
                    32
                ],
                "tag_name": {
                    "raw_string": "minecraft:always_most_significant_fall"
                }
            },
            {
                "entries": [
                    35,
                    34,
                    26
                ],
                "tag_name": {
                    "raw_string": "minecraft:can_break_armor_stand"
                }
            },
            {
                "entries": [
                    31,
                    22,
                    4,
                    6,
                    16,
                    18,
                    47,
                    5,
                    39,
                    10,
                    8,
                    17,
                    38,
                    27,
                    23,
                    32,
                    19,
                    36,
                    33
                ],
                "tag_name": {
                    "raw_string": "minecraft:bypasses_armor"
                }
            },
            {
                "entries": [
                    32,
                    19
                ],
                "tag_name": {
                    "raw_string": "minecraft:bypasses_invulnerability"
                }
            },
            {
                "entries": [
                    9,
                    35,
                    1,
                    21,
                    25,
                    31,
                    24,
                    20,
                    22,
                    4,
                    6,
                    39,
                    2,
                    10,
                    8,
                    16,
                    32,
                    18,
                    27,
                    47,
                    5,
                    7,
                    41,
                    17,
                    38,
                    33,
                    19,
                    3
                ],
                "tag_name": {
                    "raw_string": "minecraft:no_knockback"
                }
            },
            {
                "entries": [
                    29
                ],
                "tag_name": {
                    "raw_string": "minecraft:no_anger"
                }
            },
            {
                "entries": [
                    6
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_drowning"
                }
            },
            {
                "entries": [
                    27
                ],
                "tag_name": {
                    "raw_string": "minecraft:always_triggers_silverfish"
                }
            },
            {
                "entries": [
                    2,
                    17,
                    20,
                    21,
                    24,
                    25,
                    31,
                    0,
                    5,
                    9,
                    14,
                    15,
                    23,
                    27,
                    28,
                    30,
                    35,
                    36,
                    40,
                    43,
                    44,
                    45,
                    46,
                    47,
                    48,
                    34,
                    26
                ],
                "tag_name": {
                    "raw_string": "minecraft:panic_causes"
                }
            },
            {
                "entries": [
                    15,
                    9,
                    35,
                    1
                ],
                "tag_name": {
                    "raw_string": "minecraft:always_hurts_ender_dragons"
                }
            },
            {
                "entries": [
                    21,
                    3,
                    31,
                    24,
                    20,
                    45,
                    14
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_fire"
                }
            },
            {
                "entries": [
                    6
                ],
                "tag_name": {
                    "raw_string": "minecraft:wither_immune_to"
                }
            },
            {
                "entries": [
                    15,
                    9,
                    35,
                    1
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_explosion"
                }
            },
            {
                "entries": [
                    25
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_lightning"
                }
            },
            {
                "entries": [
                    0,
                    44,
                    14,
                    48,
                    46
                ],
                "tag_name": {
                    "raw_string": "minecraft:always_kills_armor_stands"
                }
            },
            {
                "entries": [
                    34,
                    26
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_player_attack"
                }
            }
        ],
        "minecraft:enchantment": [
            {
                "entries": [
                    2,
                    40,
                    37,
                    35,
                    14,
                    22,
                    41
                ],
                "tag_name": {
                    "raw_string": "minecraft:double_trade_price"
                }
            },
            {
                "entries": [
                    2,
                    40,
                    37,
                    35,
                    14,
                    22,
                    41
                ],
                "tag_name": {
                    "raw_string": "minecraft:treasure"
                }
            },
            {
                "entries": [
                    33
                ],
                "tag_name": {
                    "raw_string": "minecraft:prevents_decorated_pot_shattering"
                }
            },
            {
                "entries": [
                    2,
                    40,
                    31,
                    5,
                    41,
                    14,
                    32,
                    34,
                    1,
                    15,
                    25,
                    6,
                    4,
                    24,
                    36,
                    23,
                    10,
                    12,
                    17,
                    28,
                    27,
                    3,
                    11,
                    26,
                    9,
                    13,
                    18,
                    33,
                    20,
                    8,
                    29,
                    21,
                    30,
                    0,
                    35,
                    37,
                    7,
                    38,
                    19,
                    39,
                    16,
                    22
                ],
                "tag_name": {
                    "raw_string": "minecraft:tooltip_order"
                }
            },
            {
                "entries": [
                    27,
                    11,
                    9,
                    3,
                    26,
                    30,
                    0,
                    38,
                    7,
                    32,
                    34,
                    1,
                    17,
                    10,
                    18,
                    36,
                    8,
                    33,
                    39,
                    13,
                    25,
                    28,
                    12,
                    16,
                    20,
                    21,
                    19,
                    15,
                    31,
                    5,
                    23,
                    29,
                    24,
                    6,
                    4
                ],
                "tag_name": {
                    "raw_string": "minecraft:on_traded_equipment"
                }
            },
            {
                "entries": [
                    27,
                    11,
                    9,
                    3,
                    26,
                    30,
                    0,
                    38,
                    7,
                    32,
                    34,
                    1,
                    17,
                    10,
                    18,
                    36,
                    8,
                    33,
                    39,
                    13,
                    25,
                    28,
                    12,
                    16,
                    20,
                    21,
                    19,
                    15,
                    31,
                    5,
                    23,
                    29,
                    24,
                    6,
                    4,
                    2,
                    40,
                    14,
                    22
                ],
                "tag_name": {
                    "raw_string": "minecraft:tradeable"
                }
            },
            {
                "entries": [
                    16,
                    22
                ],
                "tag_name": {
                    "raw_string": "minecraft:exclusive_set/bow"
                }
            },
            {
                "entries": [
                    27,
                    11,
                    9,
                    3,
                    26,
                    30,
                    0,
                    38,
                    7,
                    32,
                    34,
                    1,
                    17,
                    10,
                    18,
                    36,
                    8,
                    33,
                    39,
                    13,
                    25,
                    28,
                    12,
                    16,
                    20,
                    21,
                    19,
                    15,
                    31,
                    5,
                    23,
                    29,
                    24,
                    6,
                    4,
                    2,
                    40,
                    14,
                    22
                ],
                "tag_name": {
                    "raw_string": "minecraft:on_random_loot"
                }
            },
            {
                "entries": [
                    33
                ],
                "tag_name": {
                    "raw_string": "minecraft:prevents_bee_spawns_when_mining"
                }
            },
            {
                "entries": [
                    33
                ],
                "tag_name": {
                    "raw_string": "minecraft:prevents_infested_spawns"
                }
            },
            {
                "entries": [
                    27,
                    11,
                    9,
                    3,
                    26,
                    30,
                    0,
                    38,
                    7,
                    32,
                    34,
                    1,
                    17,
                    10,
                    18,
                    36,
                    8,
                    33,
                    39,
                    13,
                    25,
                    28,
                    12,
                    16,
                    20,
                    21,
                    19,
                    15,
                    31,
                    5,
                    23,
                    29,
                    24,
                    6,
                    4
                ],
                "tag_name": {
                    "raw_string": "minecraft:non_treasure"
                }
            },
            {
                "entries": [
                    19,
                    5
                ],
                "tag_name": {
                    "raw_string": "minecraft:exclusive_set/riptide"
                }
            },
            {
                "entries": [
                    32,
                    34,
                    1,
                    15,
                    6,
                    4
                ],
                "tag_name": {
                    "raw_string": "minecraft:exclusive_set/damage"
                }
            },
            {
                "entries": [
                    27,
                    11,
                    9,
                    3,
                    26,
                    30,
                    0,
                    38,
                    7,
                    32,
                    34,
                    1,
                    17,
                    10,
                    18,
                    36,
                    8,
                    33,
                    39,
                    13,
                    25,
                    28,
                    12,
                    16,
                    20,
                    21,
                    19,
                    15,
                    31,
                    5,
                    23,
                    29,
                    24,
                    6,
                    4
                ],
                "tag_name": {
                    "raw_string": "minecraft:on_mob_spawn_equipment"
                }
            },
            {
                "entries": [
                    10
                ],
                "tag_name": {
                    "raw_string": "minecraft:smelts_loot"
                }
            },
            {
                "entries": [
                    23,
                    24
                ],
                "tag_name": {
                    "raw_string": "minecraft:exclusive_set/crossbow"
                }
            },
            {
                "entries": [
                    27,
                    3,
                    11,
                    26
                ],
                "tag_name": {
                    "raw_string": "minecraft:exclusive_set/armor"
                }
            },
            {
                "entries": [
                    14,
                    7
                ],
                "tag_name": {
                    "raw_string": "minecraft:exclusive_set/boots"
                }
            },
            {
                "entries": [
                    27,
                    11,
                    9,
                    3,
                    26,
                    30,
                    0,
                    38,
                    7,
                    32,
                    34,
                    1,
                    17,
                    10,
                    18,
                    36,
                    8,
                    33,
                    39,
                    13,
                    25,
                    28,
                    12,
                    16,
                    20,
                    21,
                    19,
                    15,
                    31,
                    5,
                    23,
                    29,
                    24,
                    6,
                    4
                ],
                "tag_name": {
                    "raw_string": "minecraft:in_enchanting_table"
                }
            },
            {
                "entries": [
                    33
                ],
                "tag_name": {
                    "raw_string": "minecraft:prevents_ice_melting"
                }
            },
            {
                "entries": [
                    2,
                    40
                ],
                "tag_name": {
                    "raw_string": "minecraft:curse"
                }
            },
            {
                "entries": [
                    13,
                    33
                ],
                "tag_name": {
                    "raw_string": "minecraft:exclusive_set/mining"
                }
            }
        ],
        "minecraft:entity_type": [
            {
                "entries": [
                    36,
                    60,
                    38
                ],
                "tag_name": {
                    "raw_string": "minecraft:axolotl_always_hostiles"
                }
            },
            {
                "entries": [
                    121,
                    98,
                    114,
                    138
                ],
                "tag_name": {
                    "raw_string": "minecraft:freeze_immune_entity_types"
                }
            },
            {
                "entries": [
                    44,
                    65,
                    97,
                    133
                ],
                "tag_name": {
                    "raw_string": "minecraft:illager"
                }
            },
            {
                "entries": [
                    110
                ],
                "tag_name": {
                    "raw_string": "minecraft:immune_to_oozing"
                }
            },
            {
                "entries": [
                    108,
                    121,
                    139,
                    109,
                    16,
                    144,
                    143,
                    145,
                    146,
                    142,
                    36,
                    64,
                    138,
                    93
                ],
                "tag_name": {
                    "raw_string": "minecraft:undead"
                }
            },
            {
                "entries": [
                    11,
                    40,
                    107,
                    117,
                    21
                ],
                "tag_name": {
                    "raw_string": "minecraft:sensitive_to_bane_of_arthropods"
                }
            },
            {
                "entries": [
                    84,
                    118,
                    12,
                    71,
                    0,
                    22,
                    31,
                    89,
                    78,
                    9
                ],
                "tag_name": {
                    "raw_string": "minecraft:boat"
                }
            },
            {
                "entries": [
                    110,
                    77
                ],
                "tag_name": {
                    "raw_string": "minecraft:frog_food"
                }
            },
            {
                "entries": [
                    50,
                    136,
                    18
                ],
                "tag_name": {
                    "raw_string": "minecraft:redirectable_projectile"
                }
            },
            {
                "entries": [
                    67,
                    114,
                    105,
                    2,
                    10,
                    11,
                    14,
                    20,
                    25,
                    55,
                    93,
                    77,
                    86,
                    92,
                    138,
                    17
                ],
                "tag_name": {
                    "raw_string": "minecraft:fall_damage_immune"
                }
            },
            {
                "entries": [
                    44,
                    97,
                    102,
                    133,
                    65,
                    137
                ],
                "tag_name": {
                    "raw_string": "minecraft:raiders"
                }
            },
            {
                "entries": [
                    11,
                    40,
                    107,
                    117,
                    21
                ],
                "tag_name": {
                    "raw_string": "minecraft:arthropod"
                }
            },
            {
                "entries": [
                    101,
                    40,
                    107,
                    52
                ],
                "tag_name": {
                    "raw_string": "minecraft:powder_snow_walkable_mobs"
                }
            },
            {
                "entries": [
                    108,
                    121,
                    139,
                    109,
                    16,
                    144,
                    143,
                    145,
                    146,
                    142,
                    36,
                    64,
                    138,
                    93
                ],
                "tag_name": {
                    "raw_string": "minecraft:inverted_healing_and_harm"
                }
            },
            {
                "entries": [
                    129,
                    100,
                    103,
                    26,
                    120,
                    58,
                    123
                ],
                "tag_name": {
                    "raw_string": "minecraft:axolotl_hunt_targets"
                }
            },
            {
                "entries": [
                    107
                ],
                "tag_name": {
                    "raw_string": "minecraft:immune_to_infested"
                }
            },
            {
                "entries": [
                    108,
                    121,
                    139,
                    109,
                    16,
                    144,
                    143,
                    145,
                    146,
                    142,
                    36,
                    64,
                    138,
                    93
                ],
                "tag_name": {
                    "raw_string": "minecraft:sensitive_to_smite"
                }
            },
            {
                "entries": [
                    144,
                    143,
                    145,
                    146,
                    142,
                    36,
                    64
                ],
                "tag_name": {
                    "raw_string": "minecraft:zombies"
                }
            },
            {
                "entries": [
                    19,
                    25,
                    34,
                    63,
                    75,
                    83,
                    94,
                    102,
                    117,
                    122,
                    127,
                    144
                ],
                "tag_name": {
                    "raw_string": "minecraft:dismounts_underwater"
                }
            },
            {
                "entries": [
                    6,
                    116,
                    51,
                    113,
                    50,
                    111,
                    37,
                    128,
                    35,
                    140,
                    136,
                    18
                ],
                "tag_name": {
                    "raw_string": "minecraft:impact_projectiles"
                }
            },
            {
                "entries": [
                    11
                ],
                "tag_name": {
                    "raw_string": "minecraft:beehive_inhabitors"
                }
            },
            {
                "entries": [
                    130,
                    60,
                    38,
                    26,
                    100,
                    103,
                    129,
                    33,
                    120,
                    58,
                    123
                ],
                "tag_name": {
                    "raw_string": "minecraft:not_scary_for_pufferfish"
                }
            },
            {
                "entries": [
                    110,
                    77
                ],
                "tag_name": {
                    "raw_string": "minecraft:non_controlling_rider"
                }
            },
            {
                "entries": [
                    17
                ],
                "tag_name": {
                    "raw_string": "minecraft:can_turn_in_boats"
                }
            },
            {
                "entries": [
                    108,
                    121,
                    139,
                    109,
                    16
                ],
                "tag_name": {
                    "raw_string": "minecraft:skeletons"
                }
            },
            {
                "entries": [
                    108,
                    121,
                    139,
                    109,
                    16,
                    144,
                    143,
                    145,
                    146,
                    142,
                    36,
                    64,
                    138,
                    93,
                    7,
                    53,
                    60,
                    38,
                    130,
                    58,
                    26,
                    100,
                    103,
                    120,
                    129,
                    123,
                    5
                ],
                "tag_name": {
                    "raw_string": "minecraft:can_breathe_under_water"
                }
            },
            {
                "entries": [
                    6,
                    116
                ],
                "tag_name": {
                    "raw_string": "minecraft:arrows"
                }
            },
            {
                "entries": [
                    108,
                    121,
                    139,
                    109,
                    16,
                    144,
                    143,
                    145,
                    146,
                    142,
                    36,
                    64,
                    138,
                    93
                ],
                "tag_name": {
                    "raw_string": "minecraft:wither_friends"
                }
            },
            {
                "entries": [
                    122,
                    14,
                    77
                ],
                "tag_name": {
                    "raw_string": "minecraft:freeze_hurts_extra_types"
                }
            },
            {
                "entries": [
                    17,
                    108,
                    16,
                    121,
                    143,
                    64,
                    117,
                    21,
                    110
                ],
                "tag_name": {
                    "raw_string": "minecraft:no_anger_from_wind_charge"
                }
            },
            {
                "entries": [
                    130,
                    7,
                    60,
                    38,
                    26,
                    100,
                    103,
                    129,
                    33,
                    120,
                    58,
                    123
                ],
                "tag_name": {
                    "raw_string": "minecraft:aquatic"
                }
            },
            {
                "entries": [
                    44,
                    65,
                    97,
                    133
                ],
                "tag_name": {
                    "raw_string": "minecraft:illager_friends"
                }
            },
            {
                "entries": [
                    108,
                    121,
                    139,
                    109,
                    16,
                    144,
                    143,
                    145,
                    146,
                    142,
                    36,
                    64,
                    138,
                    93
                ],
                "tag_name": {
                    "raw_string": "minecraft:ignores_poison_and_regen"
                }
            },
            {
                "entries": [
                    17
                ],
                "tag_name": {
                    "raw_string": "minecraft:deflects_projectiles"
                }
            },
            {
                "entries": [
                    130,
                    7,
                    60,
                    38,
                    26,
                    100,
                    103,
                    129,
                    33,
                    120,
                    58,
                    123
                ],
                "tag_name": {
                    "raw_string": "minecraft:sensitive_to_impaling"
                }
            }
        ],
        "minecraft:fluid": [
            {
                "entries": [
                    4,
                    3
                ],
                "tag_name": {
                    "raw_string": "minecraft:lava"
                }
            },
            {
                "entries": [
                    2,
                    1
                ],
                "tag_name": {
                    "raw_string": "minecraft:water"
                }
            }
        ],
        "minecraft:game_event": [
            {
                "entries": [
                    1,
                    2,
                    3,
                    5,
                    6,
                    7,
                    8,
                    0,
                    4,
                    9,
                    10,
                    11,
                    12,
                    13,
                    14,
                    15,
                    16,
                    17,
                    18,
                    19,
                    20,
                    21,
                    22,
                    24,
                    25,
                    26,
                    27,
                    28,
                    32,
                    33,
                    34,
                    35,
                    36,
                    38,
                    40,
                    41,
                    42,
                    43,
                    44,
                    45,
                    46,
                    47,
                    48,
                    49,
                    50,
                    51,
                    52,
                    53,
                    54,
                    55,
                    56,
                    57,
                    58,
                    59,
                    23
                ],
                "tag_name": {
                    "raw_string": "minecraft:vibrations"
                }
            },
            {
                "entries": [
                    26,
                    36,
                    41,
                    42,
                    29,
                    28
                ],
                "tag_name": {
                    "raw_string": "minecraft:ignore_vibrations_sneaking"
                }
            },
            {
                "entries": [
                    1,
                    2,
                    3,
                    5,
                    6,
                    7,
                    8,
                    0,
                    4,
                    9,
                    10,
                    11,
                    12,
                    13,
                    14,
                    15,
                    16,
                    17,
                    18,
                    19,
                    20,
                    21,
                    22,
                    24,
                    25,
                    26,
                    27,
                    28,
                    32,
                    33,
                    34,
                    35,
                    36,
                    38,
                    40,
                    41,
                    42,
                    43,
                    44,
                    45,
                    46,
                    47,
                    48,
                    49,
                    50,
                    51,
                    52,
                    53,
                    54,
                    55,
                    56,
                    57,
                    58,
                    59,
                    39,
                    37
                ],
                "tag_name": {
                    "raw_string": "minecraft:warden_can_listen"
                }
            },
            {
                "entries": [
                    33
                ],
                "tag_name": {
                    "raw_string": "minecraft:allay_can_listen"
                }
            },
            {
                "entries": [
                    37
                ],
                "tag_name": {
                    "raw_string": "minecraft:shrieker_can_listen"
                }
            }
        ],
        "minecraft:instrument": [
            {
                "entries": [
                    0,
                    1,
                    7,
                    2
                ],
                "tag_name": {
                    "raw_string": "minecraft:screaming_goat_horns"
                }
            },
            {
                "entries": [
                    4,
                    6,
                    5,
                    3,
                    0,
                    1,
                    7,
                    2
                ],
                "tag_name": {
                    "raw_string": "minecraft:goat_horns"
                }
            },
            {
                "entries": [
                    4,
                    6,
                    5,
                    3
                ],
                "tag_name": {
                    "raw_string": "minecraft:regular_goat_horns"
                }
            }
        ],
        "minecraft:item": [
            {
                "entries": [
                    1156,
                    1158,
                    1157,
                    1154,
                    1155,
                    1159,
                    1160
                ],
                "tag_name": {
                    "raw_string": "minecraft:skulls"
                }
            },
            {
                "entries": [
                    341,
                    342
                ],
                "tag_name": {
                    "raw_string": "minecraft:soul_fire_base_blocks"
                }
            },
            {
                "entries": [
                    840,
                    844,
                    836,
                    837,
                    846,
                    842,
                    838,
                    847,
                    839,
                    680,
                    1167
                ],
                "tag_name": {
                    "raw_string": "minecraft:trim_materials"
                }
            },
            {
                "entries": [
                    887,
                    891,
                    903,
                    895,
                    899,
                    907,
                    825
                ],
                "tag_name": {
                    "raw_string": "minecraft:head_armor"
                }
            },
            {
                "entries": [
                    847,
                    837,
                    836,
                    846,
                    842
                ],
                "tag_name": {
                    "raw_string": "minecraft:beacon_payment_items"
                }
            },
            {
                "entries": [
                    264,
                    265,
                    266,
                    267,
                    268,
                    270,
                    271,
                    275,
                    276,
                    272,
                    273,
                    269
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_slabs"
                }
            },
            {
                "entries": [
                    140,
                    177,
                    155,
                    166
                ],
                "tag_name": {
                    "raw_string": "minecraft:pale_oak_logs"
                }
            },
            {
                "entries": [
                    64,
                    65
                ],
                "tag_name": {
                    "raw_string": "minecraft:coal_ores"
                }
            },
            {
                "entries": [
                    884,
                    1036,
                    1035,
                    1207,
                    1204,
                    1205
                ],
                "tag_name": {
                    "raw_string": "minecraft:chicken_food"
                }
            },
            {
                "entries": [
                    225,
                    226,
                    228,
                    229,
                    230,
                    231,
                    232,
                    233,
                    234,
                    235,
                    236,
                    237,
                    238,
                    239,
                    240,
                    227
                ],
                "tag_name": {
                    "raw_string": "minecraft:small_flowers"
                }
            },
            {
                "entries": [
                    1029
                ],
                "tag_name": {
                    "raw_string": "minecraft:parrot_poisonous_food"
                }
            },
            {
                "entries": [
                    761,
                    759,
                    763,
                    764,
                    760,
                    757,
                    758,
                    767,
                    768,
                    765,
                    766,
                    762
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_trapdoors"
                }
            },
            {
                "entries": [
                    1148,
                    1149,
                    1206
                ],
                "tag_name": {
                    "raw_string": "minecraft:pig_food"
                }
            },
            {
                "entries": [
                    946
                ],
                "tag_name": {
                    "raw_string": "minecraft:repairs_leather_armor"
                }
            },
            {
                "entries": [
                    836
                ],
                "tag_name": {
                    "raw_string": "minecraft:repairs_diamond_armor"
                }
            },
            {
                "entries": [
                    842
                ],
                "tag_name": {
                    "raw_string": "minecraft:iron_tool_materials"
                }
            },
            {
                "entries": [
                    890,
                    894,
                    906,
                    898,
                    902,
                    910,
                    889,
                    893,
                    905,
                    897,
                    901,
                    909,
                    888,
                    892,
                    904,
                    896,
                    900,
                    908,
                    887,
                    891,
                    903,
                    895,
                    899,
                    907,
                    825
                ],
                "tag_name": {
                    "raw_string": "minecraft:trimmable_armor"
                }
            },
            {
                "entries": [
                    903,
                    904,
                    905,
                    906
                ],
                "tag_name": {
                    "raw_string": "minecraft:piglin_safe_armor"
                }
            },
            {
                "entries": [
                    1144
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/mace"
                }
            },
            {
                "entries": [
                    209,
                    210,
                    211,
                    212,
                    213,
                    214,
                    215,
                    216,
                    217,
                    218,
                    219,
                    220,
                    221,
                    222,
                    223,
                    224
                ],
                "tag_name": {
                    "raw_string": "minecraft:wool"
                }
            },
            {
                "entries": [
                    405,
                    406,
                    407,
                    408,
                    409,
                    411,
                    412,
                    416,
                    417,
                    413,
                    414,
                    410,
                    415,
                    318,
                    402,
                    392,
                    384,
                    383,
                    310,
                    449,
                    536,
                    530,
                    529,
                    531,
                    644,
                    645,
                    646,
                    647,
                    648,
                    649,
                    650,
                    651,
                    652,
                    653,
                    654,
                    655,
                    656,
                    657,
                    1283,
                    1291,
                    1287,
                    658,
                    659,
                    661,
                    660,
                    109,
                    108,
                    107,
                    106,
                    128,
                    127,
                    126,
                    129,
                    385,
                    14,
                    19,
                    23,
                    379
                ],
                "tag_name": {
                    "raw_string": "minecraft:stairs"
                }
            },
            {
                "entries": [
                    141,
                    178,
                    154,
                    165,
                    140,
                    177,
                    155,
                    166,
                    134,
                    171,
                    148,
                    159,
                    138,
                    175,
                    152,
                    163,
                    136,
                    173,
                    150,
                    161,
                    137,
                    174,
                    151,
                    162,
                    135,
                    172,
                    149,
                    160,
                    142,
                    179,
                    156,
                    167,
                    139,
                    176,
                    153,
                    164,
                    145,
                    157,
                    180,
                    168,
                    146,
                    158,
                    181,
                    169
                ],
                "tag_name": {
                    "raw_string": "minecraft:logs"
                }
            },
            {
                "entries": [
                    1220,
                    1221,
                    1222,
                    1223,
                    1226,
                    1227,
                    1228,
                    1229,
                    1230,
                    1231,
                    1232,
                    1233
                ],
                "tag_name": {
                    "raw_string": "minecraft:creeper_drop_music_discs"
                }
            },
            {
                "entries": [
                    322
                ],
                "tag_name": {
                    "raw_string": "minecraft:camel_food"
                }
            },
            {
                "entries": [
                    846
                ],
                "tag_name": {
                    "raw_string": "minecraft:gold_tool_materials"
                }
            },
            {
                "entries": [
                    469,
                    470,
                    471,
                    472,
                    473,
                    474,
                    475,
                    476,
                    477,
                    478,
                    479,
                    480,
                    481,
                    482,
                    483,
                    484
                ],
                "tag_name": {
                    "raw_string": "minecraft:wool_carpets"
                }
            },
            {
                "entries": [
                    827
                ],
                "tag_name": {
                    "raw_string": "minecraft:repairs_wolf_armor"
                }
            },
            {
                "entries": [
                    961,
                    962
                ],
                "tag_name": {
                    "raw_string": "minecraft:compasses"
                }
            },
            {
                "entries": [
                    833,
                    1212,
                    1211
                ],
                "tag_name": {
                    "raw_string": "minecraft:arrows"
                }
            },
            {
                "entries": [
                    834,
                    835
                ],
                "tag_name": {
                    "raw_string": "minecraft:furnace_minecart_fuel"
                }
            },
            {
                "entries": [
                    885
                ],
                "tag_name": {
                    "raw_string": "minecraft:cow_food"
                }
            },
            {
                "entries": [
                    958,
                    1142,
                    1165,
                    1141,
                    1218
                ],
                "tag_name": {
                    "raw_string": "minecraft:bookshelf_books"
                }
            },
            {
                "entries": [
                    980
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/fishing"
                }
            },
            {
                "entries": [
                    954,
                    1341,
                    1342,
                    1343,
                    1344,
                    1345,
                    1346,
                    1347,
                    1348,
                    1350,
                    1352,
                    1353,
                    1354,
                    1355,
                    1356,
                    1357,
                    1358,
                    1360,
                    1361,
                    1362,
                    1363,
                    1349,
                    1351,
                    1359
                ],
                "tag_name": {
                    "raw_string": "minecraft:decorated_pot_ingredients"
                }
            },
            {
                "entries": [
                    736,
                    737,
                    738,
                    739,
                    740,
                    742,
                    743,
                    746,
                    747,
                    744,
                    745,
                    741
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_doors"
                }
            },
            {
                "entries": [
                    885,
                    1011,
                    468,
                    831,
                    1153,
                    915,
                    916
                ],
                "tag_name": {
                    "raw_string": "minecraft:horse_food"
                }
            },
            {
                "entries": [
                    869,
                    854,
                    859,
                    874,
                    849,
                    864
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/sword"
                }
            },
            {
                "entries": [
                    1037,
                    1039,
                    1038,
                    1040,
                    1184,
                    913,
                    1171,
                    1183,
                    912,
                    1170,
                    1041
                ],
                "tag_name": {
                    "raw_string": "minecraft:meat"
                }
            },
            {
                "entries": [
                    146,
                    158,
                    181,
                    169
                ],
                "tag_name": {
                    "raw_string": "minecraft:warped_stems"
                }
            },
            {
                "entries": [
                    74,
                    75
                ],
                "tag_name": {
                    "raw_string": "minecraft:emerald_ores"
                }
            },
            {
                "entries": [
                    869,
                    854,
                    859,
                    874,
                    849,
                    864,
                    1144
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/fire_aspect"
                }
            },
            {
                "entries": [
                    145,
                    157,
                    180,
                    168
                ],
                "tag_name": {
                    "raw_string": "minecraft:crimson_stems"
                }
            },
            {
                "entries": [
                    147,
                    170
                ],
                "tag_name": {
                    "raw_string": "minecraft:bamboo_blocks"
                }
            },
            {
                "entries": [
                    1037,
                    1039,
                    1038,
                    1040,
                    1184,
                    913,
                    1171,
                    1183,
                    912,
                    1170,
                    1041,
                    984,
                    988,
                    985,
                    989,
                    986,
                    987,
                    1172
                ],
                "tag_name": {
                    "raw_string": "minecraft:wolf_food"
                }
            },
            {
                "entries": [
                    1153,
                    915,
                    916
                ],
                "tag_name": {
                    "raw_string": "minecraft:horse_tempt_items"
                }
            },
            {
                "entries": [
                    946
                ],
                "tag_name": {
                    "raw_string": "minecraft:ignored_by_piglin_babies"
                }
            },
            {
                "entries": [
                    832
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/bow"
                }
            },
            {
                "entries": [
                    869,
                    854,
                    859,
                    874,
                    849,
                    864
                ],
                "tag_name": {
                    "raw_string": "minecraft:swords"
                }
            },
            {
                "entries": [
                    889,
                    893,
                    905,
                    897,
                    901,
                    909
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/leg_armor"
                }
            },
            {
                "entries": [
                    35,
                    1281,
                    9
                ],
                "tag_name": {
                    "raw_string": "minecraft:stone_tool_materials"
                }
            },
            {
                "entries": [
                    485,
                    450,
                    451,
                    452,
                    453,
                    454,
                    455,
                    456,
                    457,
                    458,
                    459,
                    460,
                    461,
                    462,
                    463,
                    464,
                    465
                ],
                "tag_name": {
                    "raw_string": "minecraft:terracotta"
                }
            },
            {
                "entries": [
                    540,
                    541
                ],
                "tag_name": {
                    "raw_string": "minecraft:wart_blocks"
                }
            },
            {
                "entries": [
                    141,
                    178,
                    154,
                    165
                ],
                "tag_name": {
                    "raw_string": "minecraft:dark_oak_logs"
                }
            },
            {
                "entries": [
                    890,
                    894,
                    906,
                    898,
                    902,
                    910,
                    889,
                    893,
                    905,
                    897,
                    901,
                    909,
                    888,
                    892,
                    904,
                    896,
                    900,
                    908,
                    887,
                    891,
                    903,
                    895,
                    899,
                    907,
                    825,
                    802,
                    1214,
                    869,
                    854,
                    859,
                    874,
                    849,
                    864,
                    872,
                    857,
                    862,
                    877,
                    852,
                    867,
                    871,
                    856,
                    861,
                    876,
                    851,
                    866,
                    870,
                    855,
                    860,
                    875,
                    850,
                    865,
                    873,
                    858,
                    863,
                    878,
                    853,
                    868,
                    832,
                    1243,
                    1240,
                    829,
                    1032,
                    1321,
                    980,
                    799,
                    800,
                    1144
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/durability"
                }
            },
            {
                "entries": [
                    246
                ],
                "tag_name": {
                    "raw_string": "minecraft:strider_food"
                }
            },
            {
                "entries": [
                    146,
                    158,
                    181,
                    169,
                    145,
                    157,
                    180,
                    168,
                    46,
                    47,
                    275,
                    276,
                    733,
                    734,
                    335,
                    336,
                    767,
                    768,
                    787,
                    788,
                    416,
                    417,
                    717,
                    718,
                    746,
                    747,
                    927,
                    928,
                    940,
                    939
                ],
                "tag_name": {
                    "raw_string": "minecraft:non_flammable_wood"
                }
            },
            {
                "entries": [
                    884,
                    1149,
                    1148,
                    1207,
                    1204,
                    1205,
                    886,
                    885,
                    1206
                ],
                "tag_name": {
                    "raw_string": "minecraft:villager_picks_up"
                }
            },
            {
                "entries": [
                    834,
                    835
                ],
                "tag_name": {
                    "raw_string": "minecraft:coals"
                }
            },
            {
                "entries": [
                    245
                ],
                "tag_name": {
                    "raw_string": "minecraft:hoglin_food"
                }
            },
            {
                "entries": [
                    912,
                    913
                ],
                "tag_name": {
                    "raw_string": "minecraft:piglin_food"
                }
            },
            {
                "entries": [
                    842
                ],
                "tag_name": {
                    "raw_string": "minecraft:repairs_iron_armor"
                }
            },
            {
                "entries": [
                    545,
                    561,
                    557,
                    558,
                    555,
                    553,
                    559,
                    549,
                    554,
                    551,
                    548,
                    547,
                    552,
                    556,
                    560,
                    546,
                    550
                ],
                "tag_name": {
                    "raw_string": "minecraft:shulker_boxes"
                }
            },
            {
                "entries": [
                    1243
                ],
                "tag_name": {
                    "raw_string": "minecraft:piglin_preferred_weapons"
                }
            },
            {
                "entries": [
                    869,
                    854,
                    859,
                    874,
                    849,
                    864,
                    872,
                    857,
                    862,
                    877,
                    852,
                    867,
                    871,
                    856,
                    861,
                    876,
                    851,
                    866,
                    870,
                    855,
                    860,
                    875,
                    850,
                    865,
                    873,
                    858,
                    863,
                    878,
                    853,
                    868,
                    1240,
                    1144
                ],
                "tag_name": {
                    "raw_string": "minecraft:breaks_decorated_pots"
                }
            },
            {
                "entries": [
                    442,
                    443,
                    444
                ],
                "tag_name": {
                    "raw_string": "minecraft:anvil"
                }
            },
            {
                "entries": [
                    263,
                    1012
                ],
                "tag_name": {
                    "raw_string": "minecraft:panda_eats_from_ground"
                }
            },
            {
                "entries": [
                    136,
                    173,
                    150,
                    161
                ],
                "tag_name": {
                    "raw_string": "minecraft:birch_logs"
                }
            },
            {
                "entries": [
                    76,
                    77
                ],
                "tag_name": {
                    "raw_string": "minecraft:lapis_ores"
                }
            },
            {
                "entries": [
                    872,
                    857,
                    862,
                    877,
                    852,
                    867,
                    871,
                    856,
                    861,
                    876,
                    851,
                    866,
                    870,
                    855,
                    860,
                    875,
                    850,
                    865,
                    873,
                    858,
                    863,
                    878,
                    853,
                    868,
                    1032
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/mining"
                }
            },
            {
                "entries": [
                    872,
                    857,
                    862,
                    877,
                    852,
                    867
                ],
                "tag_name": {
                    "raw_string": "minecraft:axes"
                }
            },
            {
                "entries": [
                    873,
                    858,
                    863,
                    878,
                    853,
                    868
                ],
                "tag_name": {
                    "raw_string": "minecraft:hoes"
                }
            },
            {
                "entries": [
                    826
                ],
                "tag_name": {
                    "raw_string": "minecraft:repairs_turtle_helmet"
                }
            },
            {
                "entries": [
                    885,
                    468
                ],
                "tag_name": {
                    "raw_string": "minecraft:llama_food"
                }
            },
            {
                "entries": [
                    1204
                ],
                "tag_name": {
                    "raw_string": "minecraft:sniffer_food"
                }
            },
            {
                "entries": [
                    887,
                    891,
                    903,
                    895,
                    899,
                    907,
                    825
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/head_armor"
                }
            },
            {
                "entries": [
                    325,
                    329,
                    331,
                    332,
                    326,
                    327,
                    328,
                    335,
                    336,
                    333,
                    334,
                    330,
                    391
                ],
                "tag_name": {
                    "raw_string": "minecraft:fences"
                }
            },
            {
                "entries": [
                    49,
                    50,
                    51,
                    52,
                    53,
                    55,
                    56,
                    204,
                    205,
                    57,
                    54
                ],
                "tag_name": {
                    "raw_string": "minecraft:saplings"
                }
            },
            {
                "entries": [
                    884,
                    1036,
                    1035,
                    1207,
                    1204,
                    1205
                ],
                "tag_name": {
                    "raw_string": "minecraft:parrot_food"
                }
            },
            {
                "entries": [
                    1027,
                    1028,
                    1024,
                    1025,
                    1022,
                    1020,
                    1026,
                    1016,
                    1021,
                    1018,
                    1015,
                    1014,
                    1019,
                    1023,
                    1013,
                    1017
                ],
                "tag_name": {
                    "raw_string": "minecraft:beds"
                }
            },
            {
                "entries": [
                    66,
                    67
                ],
                "tag_name": {
                    "raw_string": "minecraft:iron_ores"
                }
            },
            {
                "entries": [
                    1148,
                    1153,
                    225
                ],
                "tag_name": {
                    "raw_string": "minecraft:rabbit_food"
                }
            },
            {
                "entries": [
                    890,
                    894,
                    906,
                    898,
                    902,
                    910,
                    889,
                    893,
                    905,
                    897,
                    901,
                    909,
                    888,
                    892,
                    904,
                    896,
                    900,
                    908,
                    887,
                    891,
                    903,
                    895,
                    899,
                    907,
                    825
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/armor"
                }
            },
            {
                "entries": [
                    1243
                ],
                "tag_name": {
                    "raw_string": "minecraft:pillager_preferred_weapons"
                }
            },
            {
                "entries": [
                    963,
                    979,
                    975,
                    976,
                    973,
                    971,
                    977,
                    967,
                    972,
                    969,
                    966,
                    965,
                    970,
                    974,
                    978,
                    968,
                    964
                ],
                "tag_name": {
                    "raw_string": "minecraft:bundles"
                }
            },
            {
                "entries": [
                    134,
                    171,
                    148,
                    159
                ],
                "tag_name": {
                    "raw_string": "minecraft:oak_logs"
                }
            },
            {
                "entries": [
                    1240
                ],
                "tag_name": {
                    "raw_string": "minecraft:drowned_preferred_weapons"
                }
            },
            {
                "entries": [
                    736,
                    737,
                    738,
                    739,
                    740,
                    742,
                    743,
                    746,
                    747,
                    744,
                    745,
                    741,
                    748,
                    749,
                    750,
                    751,
                    752,
                    753,
                    754,
                    755,
                    735
                ],
                "tag_name": {
                    "raw_string": "minecraft:doors"
                }
            },
            {
                "entries": [
                    984,
                    985
                ],
                "tag_name": {
                    "raw_string": "minecraft:ocelot_food"
                }
            },
            {
                "entries": [
                    1157,
                    1154,
                    1158,
                    1159,
                    1155,
                    1160,
                    1156
                ],
                "tag_name": {
                    "raw_string": "minecraft:noteblock_top_instruments"
                }
            },
            {
                "entries": [
                    1185,
                    1186,
                    1187,
                    1188,
                    1189,
                    1190,
                    1191,
                    1192,
                    1193,
                    1194,
                    1195,
                    1196,
                    1197,
                    1198,
                    1199,
                    1200
                ],
                "tag_name": {
                    "raw_string": "minecraft:banners"
                }
            },
            {
                "entries": [
                    1051
                ],
                "tag_name": {
                    "raw_string": "minecraft:brewing_fuel"
                }
            },
            {
                "entries": [
                    59,
                    62
                ],
                "tag_name": {
                    "raw_string": "minecraft:smelts_to_glass"
                }
            },
            {
                "entries": [
                    35,
                    1281,
                    9
                ],
                "tag_name": {
                    "raw_string": "minecraft:stone_crafting_materials"
                }
            },
            {
                "entries": [
                    346,
                    1268,
                    1272
                ],
                "tag_name": {
                    "raw_string": "minecraft:piglin_repellents"
                }
            },
            {
                "entries": [
                    325,
                    329,
                    331,
                    332,
                    326,
                    327,
                    328,
                    335,
                    336,
                    333,
                    334,
                    330
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_fences"
                }
            },
            {
                "entries": [
                    951
                ],
                "tag_name": {
                    "raw_string": "minecraft:axolotl_food"
                }
            },
            {
                "entries": [
                    884,
                    1149,
                    1148,
                    1207,
                    1204,
                    1205
                ],
                "tag_name": {
                    "raw_string": "minecraft:villager_plantable_seeds"
                }
            },
            {
                "entries": [
                    889,
                    893,
                    905,
                    897,
                    901,
                    909
                ],
                "tag_name": {
                    "raw_string": "minecraft:leg_armor"
                }
            },
            {
                "entries": [
                    832,
                    1243
                ],
                "tag_name": {
                    "raw_string": "minecraft:wither_skeleton_disliked_weapons"
                }
            },
            {
                "entries": [
                    263
                ],
                "tag_name": {
                    "raw_string": "minecraft:panda_food"
                }
            },
            {
                "entries": [
                    890,
                    894,
                    906,
                    898,
                    902,
                    910
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/foot_armor"
                }
            },
            {
                "entries": [
                    209,
                    210,
                    211,
                    212,
                    213,
                    214,
                    215,
                    216,
                    217,
                    218,
                    219,
                    220,
                    221,
                    222,
                    223,
                    224,
                    469,
                    470,
                    471,
                    472,
                    473,
                    474,
                    475,
                    476,
                    477,
                    478,
                    479,
                    480,
                    481,
                    482,
                    483,
                    484
                ],
                "tag_name": {
                    "raw_string": "minecraft:dampens_vibrations"
                }
            },
            {
                "entries": [
                    142,
                    179,
                    156,
                    167
                ],
                "tag_name": {
                    "raw_string": "minecraft:mangrove_logs"
                }
            },
            {
                "entries": [
                    137,
                    174,
                    151,
                    162
                ],
                "tag_name": {
                    "raw_string": "minecraft:jungle_logs"
                }
            },
            {
                "entries": [
                    1142,
                    1141
                ],
                "tag_name": {
                    "raw_string": "minecraft:lectern_books"
                }
            },
            {
                "entries": [
                    888,
                    892,
                    904,
                    896,
                    900,
                    908
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/chest_armor"
                }
            },
            {
                "entries": [
                    207
                ],
                "tag_name": {
                    "raw_string": "minecraft:turtle_food"
                }
            },
            {
                "entries": [
                    468
                ],
                "tag_name": {
                    "raw_string": "minecraft:llama_tempt_items"
                }
            },
            {
                "entries": [
                    135,
                    172,
                    149,
                    160
                ],
                "tag_name": {
                    "raw_string": "minecraft:spruce_logs"
                }
            },
            {
                "entries": [
                    405,
                    406,
                    407,
                    408,
                    409,
                    411,
                    412,
                    416,
                    417,
                    413,
                    414,
                    410
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_stairs"
                }
            },
            {
                "entries": [
                    917,
                    918,
                    919,
                    921,
                    920,
                    923,
                    924,
                    927,
                    928,
                    925,
                    926,
                    922
                ],
                "tag_name": {
                    "raw_string": "minecraft:signs"
                }
            },
            {
                "entries": [
                    869,
                    854,
                    859,
                    874,
                    849,
                    864,
                    872,
                    857,
                    862,
                    877,
                    852,
                    867,
                    1144
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/weapon"
                }
            },
            {
                "entries": [
                    707,
                    708,
                    709,
                    710,
                    711,
                    713,
                    714,
                    717,
                    718,
                    715,
                    716,
                    712
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_buttons"
                }
            },
            {
                "entries": [
                    847
                ],
                "tag_name": {
                    "raw_string": "minecraft:netherite_tool_materials"
                }
            },
            {
                "entries": [
                    984,
                    988,
                    985,
                    989,
                    987,
                    986
                ],
                "tag_name": {
                    "raw_string": "minecraft:fishes"
                }
            },
            {
                "entries": [
                    355,
                    356,
                    357,
                    358
                ],
                "tag_name": {
                    "raw_string": "minecraft:stone_bricks"
                }
            },
            {
                "entries": [
                    870,
                    855,
                    860,
                    875,
                    850,
                    865
                ],
                "tag_name": {
                    "raw_string": "minecraft:shovels"
                }
            },
            {
                "entries": [
                    804,
                    806,
                    808,
                    810,
                    812,
                    816,
                    818,
                    820,
                    822,
                    814
                ],
                "tag_name": {
                    "raw_string": "minecraft:chest_boats"
                }
            },
            {
                "entries": [
                    829,
                    1139
                ],
                "tag_name": {
                    "raw_string": "minecraft:creeper_igniters"
                }
            },
            {
                "entries": [
                    890,
                    894,
                    906,
                    898,
                    902,
                    910,
                    889,
                    893,
                    905,
                    897,
                    901,
                    909,
                    888,
                    892,
                    904,
                    896,
                    900,
                    908,
                    887,
                    891,
                    903,
                    895,
                    899,
                    907,
                    825,
                    802,
                    1156,
                    1158,
                    1157,
                    1154,
                    1155,
                    1159,
                    1160,
                    338
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/equippable"
                }
            },
            {
                "entries": [
                    1240
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/trident"
                }
            },
            {
                "entries": [
                    869,
                    854,
                    859,
                    874,
                    849,
                    864,
                    872,
                    857,
                    862,
                    877,
                    852,
                    867
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/sharp_weapon"
                }
            },
            {
                "entries": [
                    264,
                    265,
                    266,
                    267,
                    268,
                    270,
                    271,
                    275,
                    276,
                    272,
                    273,
                    269,
                    274,
                    277,
                    278,
                    284,
                    279,
                    290,
                    287,
                    288,
                    283,
                    282,
                    286,
                    281,
                    291,
                    292,
                    293,
                    662,
                    663,
                    664,
                    665,
                    666,
                    667,
                    668,
                    669,
                    670,
                    671,
                    672,
                    673,
                    674,
                    280,
                    289,
                    1282,
                    1290,
                    1286,
                    675,
                    676,
                    678,
                    677,
                    132,
                    131,
                    130,
                    113,
                    112,
                    111,
                    110,
                    133,
                    285,
                    13,
                    18,
                    22,
                    380
                ],
                "tag_name": {
                    "raw_string": "minecraft:slabs"
                }
            },
            {
                "entries": [
                    872,
                    857,
                    862,
                    877,
                    852,
                    867,
                    871,
                    856,
                    861,
                    876,
                    851,
                    866,
                    870,
                    855,
                    860,
                    875,
                    850,
                    865,
                    873,
                    858,
                    863,
                    878,
                    853,
                    868
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/mining_loot"
                }
            },
            {
                "entries": [
                    847
                ],
                "tag_name": {
                    "raw_string": "minecraft:repairs_netherite_armor"
                }
            },
            {
                "entries": [
                    890,
                    894,
                    906,
                    898,
                    902,
                    910,
                    889,
                    893,
                    905,
                    897,
                    901,
                    909,
                    888,
                    892,
                    904,
                    896,
                    900,
                    908,
                    887,
                    891,
                    903,
                    895,
                    899,
                    907,
                    825,
                    802,
                    1214,
                    869,
                    854,
                    859,
                    874,
                    849,
                    864,
                    872,
                    857,
                    862,
                    877,
                    852,
                    867,
                    871,
                    856,
                    861,
                    876,
                    851,
                    866,
                    870,
                    855,
                    860,
                    875,
                    850,
                    865,
                    873,
                    858,
                    863,
                    878,
                    853,
                    868,
                    832,
                    1243,
                    1240,
                    829,
                    1032,
                    1321,
                    980,
                    799,
                    800,
                    1144,
                    961,
                    338,
                    1156,
                    1158,
                    1157,
                    1154,
                    1155,
                    1159,
                    1160
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/vanishing"
                }
            },
            {
                "entries": [
                    929,
                    930,
                    931,
                    933,
                    934,
                    932,
                    935,
                    936,
                    939,
                    940,
                    937,
                    938
                ],
                "tag_name": {
                    "raw_string": "minecraft:hanging_signs"
                }
            },
            {
                "entries": [
                    36,
                    37,
                    38,
                    39,
                    40,
                    42,
                    43,
                    46,
                    47,
                    44,
                    45,
                    41
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_tool_materials"
                }
            },
            {
                "entries": [
                    72,
                    73
                ],
                "tag_name": {
                    "raw_string": "minecraft:redstone_ores"
                }
            },
            {
                "entries": [
                    761,
                    759,
                    763,
                    764,
                    760,
                    757,
                    758,
                    767,
                    768,
                    765,
                    766,
                    762,
                    756,
                    769,
                    770,
                    771,
                    772,
                    773,
                    774,
                    775,
                    776
                ],
                "tag_name": {
                    "raw_string": "minecraft:trapdoors"
                }
            },
            {
                "entries": [
                    840
                ],
                "tag_name": {
                    "raw_string": "minecraft:duplicates_allays"
                }
            },
            {
                "entries": [
                    139,
                    176,
                    153,
                    164
                ],
                "tag_name": {
                    "raw_string": "minecraft:cherry_logs"
                }
            },
            {
                "entries": [
                    707,
                    708,
                    709,
                    710,
                    711,
                    713,
                    714,
                    717,
                    718,
                    715,
                    716,
                    712,
                    705,
                    706
                ],
                "tag_name": {
                    "raw_string": "minecraft:buttons"
                }
            },
            {
                "entries": [
                    887,
                    888,
                    889,
                    890,
                    1179,
                    828
                ],
                "tag_name": {
                    "raw_string": "minecraft:dyeable"
                }
            },
            {
                "entries": [
                    36,
                    37,
                    38,
                    39,
                    40,
                    42,
                    43,
                    46,
                    47,
                    44,
                    45,
                    41
                ],
                "tag_name": {
                    "raw_string": "minecraft:planks"
                }
            },
            {
                "entries": [
                    705,
                    706
                ],
                "tag_name": {
                    "raw_string": "minecraft:stone_buttons"
                }
            },
            {
                "entries": [
                    1269,
                    1270
                ],
                "tag_name": {
                    "raw_string": "minecraft:fox_food"
                }
            },
            {
                "entries": [
                    803,
                    805,
                    807,
                    809,
                    811,
                    815,
                    817,
                    819,
                    821,
                    813,
                    804,
                    806,
                    808,
                    810,
                    812,
                    816,
                    818,
                    820,
                    822,
                    814
                ],
                "tag_name": {
                    "raw_string": "minecraft:boats"
                }
            },
            {
                "entries": [
                    1243
                ],
                "tag_name": {
                    "raw_string": "minecraft:enchantable/crossbow"
                }
            },
            {
                "entries": [
                    888,
                    892,
                    904,
                    896,
                    900,
                    908
                ],
                "tag_name": {
                    "raw_string": "minecraft:chest_armor"
                }
            },
            {
                "entries": [
                    791,
                    789,
                    790,
                    792
                ],
                "tag_name": {
                    "raw_string": "minecraft:rails"
                }
            },
            {
                "entries": [
                    832
                ],
                "tag_name": {
                    "raw_string": "minecraft:skeleton_preferred_weapons"
                }
            },
            {
                "entries": [
                    959
                ],
                "tag_name": {
                    "raw_string": "minecraft:frog_food"
                }
            },
            {
                "entries": [
                    78,
                    79
                ],
                "tag_name": {
                    "raw_string": "minecraft:diamond_ores"
                }
            },
            {
                "entries": [
                    185,
                    182,
                    183,
                    189,
                    188,
                    186,
                    184,
                    191,
                    192,
                    190,
                    187
                ],
                "tag_name": {
                    "raw_string": "minecraft:leaves"
                }
            },
            {
                "entries": [
                    246,
                    800
                ],
                "tag_name": {
                    "raw_string": "minecraft:strider_tempt_items"
                }
            },
            {
                "entries": [
                    420,
                    421,
                    422,
                    423,
                    424,
                    425,
                    426,
                    427,
                    429,
                    430,
                    431,
                    432,
                    433,
                    434,
                    435,
                    437,
                    436,
                    438,
                    439,
                    441,
                    440,
                    428,
                    15,
                    20,
                    24,
                    381
                ],
                "tag_name": {
                    "raw_string": "minecraft:walls"
                }
            },
            {
                "entries": [
                    338
                ],
                "tag_name": {
                    "raw_string": "minecraft:gaze_disguise_equipment"
                }
            },
            {
                "entries": [
                    338
                ],
                "tag_name": {
                    "raw_string": "minecraft:map_invisibility_equipment"
                }
            },
            {
                "entries": [
                    781,
                    779,
                    783,
                    784,
                    780,
                    777,
                    778,
                    787,
                    788,
                    785,
                    786,
                    782
                ],
                "tag_name": {
                    "raw_string": "minecraft:fence_gates"
                }
            },
            {
                "entries": [
                    1049
                ],
                "tag_name": {
                    "raw_string": "minecraft:armadillo_food"
                }
            },
            {
                "entries": [
                    846
                ],
                "tag_name": {
                    "raw_string": "minecraft:repairs_gold_armor"
                }
            },
            {
                "entries": [
                    723,
                    724,
                    725,
                    726,
                    727,
                    729,
                    730,
                    733,
                    734,
                    731,
                    732,
                    728
                ],
                "tag_name": {
                    "raw_string": "minecraft:wooden_pressure_plates"
                }
            },
            {
                "entries": [
                    885
                ],
                "tag_name": {
                    "raw_string": "minecraft:goat_food"
                }
            },
            {
                "entries": [
                    138,
                    175,
                    152,
                    163
                ],
                "tag_name": {
                    "raw_string": "minecraft:acacia_logs"
                }
            },
            {
                "entries": [
                    1294,
                    1295,
                    1296,
                    1297,
                    1298,
                    1299,
                    1300,
                    1301,
                    1302,
                    1303,
                    1304,
                    1305,
                    1306,
                    1307,
                    1308,
                    1309,
                    1310
                ],
                "tag_name": {
                    "raw_string": "minecraft:candles"
                }
            },
            {
                "entries": [
                    885
                ],
                "tag_name": {
                    "raw_string": "minecraft:sheep_food"
                }
            },
            {
                "entries": [
                    984,
                    985
                ],
                "tag_name": {
                    "raw_string": "minecraft:cat_food"
                }
            },
            {
                "entries": [
                    70,
                    80,
                    71,
                    92,
                    1284,
                    721,
                    846,
                    1266,
                    981,
                    1153,
                    1056,
                    915,
                    916,
                    903,
                    904,
                    905,
                    906,
                    1177,
                    859,
                    861,
                    860,
                    862,
                    863,
                    845,
                    86
                ],
                "tag_name": {
                    "raw_string": "minecraft:piglin_loved"
                }
            },
            {
                "entries": [
                    225,
                    226,
                    228,
                    229,
                    230,
                    231,
                    232,
                    233,
                    234,
                    235,
                    236,
                    237,
                    238,
                    239,
                    240,
                    488,
                    489,
                    491,
                    490,
                    241,
                    192,
                    205,
                    57,
                    187,
                    254,
                    307,
                    242
                ],
                "tag_name": {
                    "raw_string": "minecraft:bee_food"
                }
            },
            {
                "entries": [
                    59,
                    62,
                    60
                ],
                "tag_name": {
                    "raw_string": "minecraft:sand"
                }
            },
            {
                "entries": [
                    68,
                    69
                ],
                "tag_name": {
                    "raw_string": "minecraft:copper_ores"
                }
            },
            {
                "entries": [
                    70,
                    80,
                    71
                ],
                "tag_name": {
                    "raw_string": "minecraft:gold_ores"
                }
            },
            {
                "entries": [
                    890,
                    889,
                    888,
                    887,
                    1179
                ],
                "tag_name": {
                    "raw_string": "minecraft:freeze_immune_wearables"
                }
            },
            {
                "entries": [
                    141,
                    178,
                    154,
                    165,
                    140,
                    177,
                    155,
                    166,
                    134,
                    171,
                    148,
                    159,
                    138,
                    175,
                    152,
                    163,
                    136,
                    173,
                    150,
                    161,
                    137,
                    174,
                    151,
                    162,
                    135,
                    172,
                    149,
                    160,
                    142,
                    179,
                    156,
                    167,
                    139,
                    176,
                    153,
                    164,
                    145,
                    157,
                    180,
                    168,
                    146,
                    158,
                    181,
                    169,
                    185,
                    182,
                    183,
                    189,
                    188,
                    186,
                    184,
                    191,
                    192,
                    190,
                    187,
                    540,
                    541
                ],
                "tag_name": {
                    "raw_string": "minecraft:completes_find_tree_tutorial"
                }
            },
            {
                "entries": [
                    141,
                    178,
                    154,
                    165,
                    140,
                    177,
                    155,
                    166,
                    134,
                    171,
                    148,
                    159,
                    138,
                    175,
                    152,
                    163,
                    136,
                    173,
                    150,
                    161,
                    137,
                    174,
                    151,
                    162,
                    135,
                    172,
                    149,
                    160,
                    142,
                    179,
                    156,
                    167,
                    139,
                    176,
                    153,
                    164
                ],
                "tag_name": {
                    "raw_string": "minecraft:logs_that_burn"
                }
            },
            {
                "entries": [
                    28,
                    27,
                    30,
                    29,
                    386,
                    31,
                    256,
                    259,
                    32,
                    144
                ],
                "tag_name": {
                    "raw_string": "minecraft:dirt"
                }
            },
            {
                "entries": [
                    836
                ],
                "tag_name": {
                    "raw_string": "minecraft:diamond_tool_materials"
                }
            },
            {
                "entries": [
                    842
                ],
                "tag_name": {
                    "raw_string": "minecraft:repairs_chain_armor"
                }
            },
            {
                "entries": [
                    1341,
                    1342,
                    1343,
                    1344,
                    1345,
                    1346,
                    1347,
                    1348,
                    1350,
                    1352,
                    1353,
                    1354,
                    1355,
                    1356,
                    1357,
                    1358,
                    1360,
                    1361,
                    1362,
                    1363,
                    1349,
                    1351,
                    1359
                ],
                "tag_name": {
                    "raw_string": "minecraft:decorated_pot_sherds"
                }
            },
            {
                "entries": [
                    871,
                    856,
                    861,
                    876,
                    851,
                    866
                ],
                "tag_name": {
                    "raw_string": "minecraft:pickaxes"
                }
            },
            {
                "entries": [
                    871,
                    861,
                    866,
                    876,
                    856,
                    851
                ],
                "tag_name": {
                    "raw_string": "minecraft:cluster_max_harvestables"
                }
            },
            {
                "entries": [
                    890,
                    894,
                    906,
                    898,
                    902,
                    910
                ],
                "tag_name": {
                    "raw_string": "minecraft:foot_armor"
                }
            }
        ],
        "minecraft:painting_variant": [
            {
                "entries": [
                    23,
                    1,
                    0,
                    2,
                    5,
                    31,
                    46,
                    34,
                    12,
                    36,
                    41,
                    13,
                    45,
                    21,
                    25,
                    8,
                    39,
                    44,
                    38,
                    49,
                    18,
                    32,
                    30,
                    7,
                    37,
                    14,
                    4,
                    22,
                    26,
                    35,
                    43,
                    3,
                    6,
                    9,
                    10,
                    11,
                    16,
                    17,
                    19,
                    24,
                    27,
                    28,
                    29,
                    33,
                    40,
                    42
                ],
                "tag_name": {
                    "raw_string": "minecraft:placeable"
                }
            }
        ],
        "minecraft:point_of_interest_type": [
            {
                "entries": [
                    15,
                    16
                ],
                "tag_name": {
                    "raw_string": "minecraft:bee_home"
                }
            },
            {
                "entries": [
                    0,
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8,
                    9,
                    10,
                    11,
                    12
                ],
                "tag_name": {
                    "raw_string": "minecraft:acquirable_job_site"
                }
            },
            {
                "entries": [
                    0,
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    7,
                    8,
                    9,
                    10,
                    11,
                    12,
                    13,
                    14
                ],
                "tag_name": {
                    "raw_string": "minecraft:village"
                }
            }
        ],
        "minecraft:worldgen/biome": [
            {
                "entries": [
                    34,
                    49,
                    7,
                    59,
                    2
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/nether_fortress"
                }
            },
            {
                "entries": [
                    29,
                    58,
                    12
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/ocean_ruin_warm"
                }
            },
            {
                "entries": [
                    41,
                    24
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_river"
                }
            },
            {
                "entries": [
                    11,
                    9,
                    13,
                    12,
                    22,
                    35,
                    6,
                    29,
                    58,
                    41,
                    24,
                    54,
                    31
                ],
                "tag_name": {
                    "raw_string": "minecraft:water_on_map_outlines"
                }
            },
            {
                "entries": [
                    11,
                    9,
                    13,
                    12,
                    22,
                    35,
                    6,
                    29,
                    58,
                    41,
                    24,
                    3,
                    45,
                    32,
                    23,
                    27,
                    51,
                    47,
                    5,
                    62,
                    60,
                    61,
                    55,
                    48,
                    37,
                    38,
                    1,
                    28,
                    50,
                    21,
                    20,
                    4,
                    36,
                    8,
                    39,
                    25,
                    52,
                    33,
                    26,
                    63,
                    14,
                    42,
                    46,
                    40,
                    53,
                    54,
                    31,
                    43,
                    15,
                    30
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/mineshaft"
                }
            },
            {
                "entries": [
                    1,
                    28
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/jungle_temple"
                }
            },
            {
                "entries": [
                    33,
                    11,
                    22,
                    9,
                    6,
                    13,
                    35,
                    12,
                    29,
                    58,
                    52,
                    54,
                    31,
                    47,
                    46,
                    45,
                    61,
                    25,
                    62,
                    48,
                    60,
                    55,
                    40,
                    32,
                    3,
                    21,
                    38,
                    20,
                    4,
                    8,
                    43,
                    42,
                    28,
                    0,
                    14,
                    64,
                    27,
                    51,
                    24,
                    41,
                    26,
                    37,
                    53,
                    36,
                    50,
                    1,
                    19,
                    63,
                    5,
                    39,
                    23,
                    15,
                    30
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/trial_chambers"
                }
            },
            {
                "entries": [
                    14
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/village_desert"
                }
            },
            {
                "entries": [
                    41,
                    24
                ],
                "tag_name": {
                    "raw_string": "minecraft:more_frequent_drowned_spawns"
                }
            },
            {
                "entries": [
                    1,
                    28,
                    50
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_jungle"
                }
            },
            {
                "entries": [
                    46,
                    26,
                    22,
                    48,
                    24,
                    45,
                    23,
                    27,
                    47,
                    25
                ],
                "tag_name": {
                    "raw_string": "minecraft:spawns_snow_foxes"
                }
            },
            {
                "entries": [
                    21,
                    20,
                    4,
                    36,
                    8,
                    39,
                    25
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_forest"
                }
            },
            {
                "entries": [
                    11,
                    9,
                    13,
                    12,
                    22,
                    35,
                    6,
                    29,
                    58
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_ocean"
                }
            },
            {
                "entries": [
                    42
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/village_savanna"
                }
            },
            {
                "entries": [
                    33,
                    11,
                    22,
                    9,
                    6,
                    13,
                    35,
                    12,
                    29,
                    58,
                    52,
                    54,
                    31,
                    47,
                    46,
                    45,
                    61,
                    25,
                    62,
                    48,
                    60,
                    55,
                    40,
                    32,
                    3,
                    21,
                    38,
                    20,
                    4,
                    8,
                    43,
                    42,
                    28,
                    0,
                    14,
                    64,
                    27,
                    51,
                    24,
                    41,
                    26,
                    37,
                    53,
                    36,
                    50,
                    1,
                    19,
                    63,
                    5,
                    39,
                    23,
                    15,
                    30,
                    10
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_overworld"
                }
            },
            {
                "entries": [
                    33
                ],
                "tag_name": {
                    "raw_string": "minecraft:without_patrol_spawns"
                }
            },
            {
                "entries": [
                    55
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/village_taiga"
                }
            },
            {
                "entries": [
                    30
                ],
                "tag_name": {
                    "raw_string": "minecraft:allows_tropical_fish_spawns_at_any_height"
                }
            },
            {
                "entries": [
                    22,
                    11
                ],
                "tag_name": {
                    "raw_string": "minecraft:polar_bears_spawn_on_alternate_blocks"
                }
            },
            {
                "entries": [
                    54,
                    31
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_closer_water_fog"
                }
            },
            {
                "entries": [
                    40,
                    32
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/village_plains"
                }
            },
            {
                "entries": [
                    3,
                    45
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/buried_treasure"
                }
            },
            {
                "entries": [
                    22,
                    6,
                    35,
                    11,
                    9,
                    13
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/ocean_ruin_cold"
                }
            },
            {
                "entries": [
                    58
                ],
                "tag_name": {
                    "raw_string": "minecraft:produces_corals_from_bonemeal"
                }
            },
            {
                "entries": [
                    33,
                    11,
                    22,
                    9,
                    6,
                    13,
                    35,
                    12,
                    29,
                    58,
                    52,
                    54,
                    31,
                    47,
                    46,
                    45,
                    61,
                    25,
                    62,
                    48,
                    60,
                    55,
                    40,
                    32,
                    3,
                    21,
                    38,
                    20,
                    4,
                    8,
                    43,
                    42,
                    28,
                    0,
                    14,
                    64,
                    27,
                    51,
                    24,
                    41,
                    26,
                    37,
                    53,
                    36,
                    50,
                    1,
                    19,
                    63,
                    5,
                    39,
                    23,
                    15,
                    30,
                    10
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/stronghold"
                }
            },
            {
                "entries": [
                    0,
                    2,
                    7,
                    14,
                    19,
                    34,
                    42,
                    43,
                    49,
                    59,
                    63,
                    64
                ],
                "tag_name": {
                    "raw_string": "minecraft:snow_golem_melts"
                }
            },
            {
                "entries": [
                    14
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/ruined_portal_desert"
                }
            },
            {
                "entries": [
                    57
                ],
                "tag_name": {
                    "raw_string": "minecraft:without_wandering_trader_spawns"
                }
            },
            {
                "entries": [
                    0,
                    19,
                    64
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/mineshaft_mesa"
                }
            },
            {
                "entries": [
                    10
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/ancient_city"
                }
            },
            {
                "entries": [
                    54,
                    31
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/ruined_portal_swamp"
                }
            },
            {
                "entries": [
                    11,
                    9,
                    13,
                    12,
                    22,
                    35,
                    6,
                    29,
                    58
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/shipwreck"
                }
            },
            {
                "entries": [
                    42,
                    43,
                    63
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_savanna"
                }
            },
            {
                "entries": [
                    54
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/swamp_hut"
                }
            },
            {
                "entries": [
                    46,
                    26,
                    23,
                    27,
                    47,
                    22,
                    11,
                    25,
                    10,
                    24,
                    48,
                    45,
                    56,
                    17,
                    18,
                    44,
                    16
                ],
                "tag_name": {
                    "raw_string": "minecraft:spawns_cold_variant_frogs"
                }
            },
            {
                "entries": [
                    48,
                    46,
                    47
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/igloo"
                }
            },
            {
                "entries": [
                    3,
                    45
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/shipwreck_beached"
                }
            },
            {
                "entries": [
                    55,
                    48,
                    37,
                    38,
                    36,
                    28
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/trail_ruins"
                }
            },
            {
                "entries": [
                    62,
                    60,
                    61
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_hill"
                }
            },
            {
                "entries": [
                    34,
                    49,
                    7,
                    59,
                    2
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/ruined_portal_nether"
                }
            },
            {
                "entries": [
                    56,
                    17,
                    18,
                    44,
                    16
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_end"
                }
            },
            {
                "entries": [
                    40,
                    53,
                    46,
                    26,
                    14,
                    21,
                    20,
                    4,
                    8,
                    39,
                    36,
                    37,
                    38,
                    55,
                    48,
                    42,
                    43,
                    62,
                    61,
                    60,
                    63,
                    28,
                    50,
                    1,
                    0,
                    19,
                    64,
                    32,
                    25,
                    47,
                    23,
                    27,
                    51,
                    33,
                    15,
                    30
                ],
                "tag_name": {
                    "raw_string": "minecraft:stronghold_biased_to"
                }
            },
            {
                "entries": [
                    33
                ],
                "tag_name": {
                    "raw_string": "minecraft:without_zombie_sieges"
                }
            },
            {
                "entries": [
                    3,
                    45
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_beach"
                }
            },
            {
                "entries": [
                    3,
                    45,
                    41,
                    24,
                    55,
                    48,
                    37,
                    38,
                    21,
                    20,
                    4,
                    36,
                    8,
                    39,
                    25,
                    33,
                    26,
                    15,
                    30,
                    42,
                    46,
                    40,
                    53
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/ruined_portal_standard"
                }
            },
            {
                "entries": [
                    14,
                    40,
                    42,
                    46,
                    55,
                    32,
                    23,
                    27,
                    51,
                    47,
                    5,
                    25
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/pillager_outpost"
                }
            },
            {
                "entries": [
                    54,
                    31
                ],
                "tag_name": {
                    "raw_string": "minecraft:allows_surface_slime_spawns"
                }
            },
            {
                "entries": [
                    55,
                    48,
                    37,
                    38
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_taiga"
                }
            },
            {
                "entries": [
                    14,
                    58,
                    1,
                    28,
                    50,
                    42,
                    43,
                    63,
                    34,
                    49,
                    7,
                    59,
                    2,
                    0,
                    19,
                    64,
                    31
                ],
                "tag_name": {
                    "raw_string": "minecraft:spawns_warm_variant_frogs"
                }
            },
            {
                "entries": [
                    11,
                    9,
                    13,
                    12,
                    22,
                    35,
                    6,
                    29,
                    58,
                    41,
                    24
                ],
                "tag_name": {
                    "raw_string": "minecraft:required_ocean_monument_surrounding"
                }
            },
            {
                "entries": [
                    10
                ],
                "tag_name": {
                    "raw_string": "minecraft:mineshaft_blocking"
                }
            },
            {
                "entries": [
                    32,
                    23,
                    27,
                    51,
                    47,
                    5
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_mountain"
                }
            },
            {
                "entries": [
                    46
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/village_snowy"
                }
            },
            {
                "entries": [
                    11,
                    9,
                    13,
                    12
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/ocean_monument"
                }
            },
            {
                "entries": [
                    0,
                    19,
                    64
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_badlands"
                }
            },
            {
                "entries": [
                    14
                ],
                "tag_name": {
                    "raw_string": "minecraft:spawns_gold_rabbits"
                }
            },
            {
                "entries": [
                    46,
                    26,
                    22,
                    48,
                    24,
                    45,
                    23,
                    27,
                    47,
                    25
                ],
                "tag_name": {
                    "raw_string": "minecraft:spawns_white_rabbits"
                }
            },
            {
                "entries": [
                    1,
                    28,
                    50
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/ruined_portal_jungle"
                }
            },
            {
                "entries": [
                    11,
                    9,
                    13,
                    12,
                    22,
                    35,
                    6,
                    29,
                    58,
                    41,
                    24
                ],
                "tag_name": {
                    "raw_string": "minecraft:plays_underwater_music"
                }
            },
            {
                "entries": [
                    14
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/desert_pyramid"
                }
            },
            {
                "entries": [
                    49
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/nether_fossil"
                }
            },
            {
                "entries": [
                    11,
                    9,
                    13,
                    12,
                    22,
                    35,
                    6,
                    29,
                    58
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/ruined_portal_ocean"
                }
            },
            {
                "entries": [
                    17,
                    18
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/end_city"
                }
            },
            {
                "entries": [
                    11,
                    9,
                    13,
                    12
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_deep_ocean"
                }
            },
            {
                "entries": [
                    34,
                    49,
                    7,
                    59,
                    2
                ],
                "tag_name": {
                    "raw_string": "minecraft:is_nether"
                }
            },
            {
                "entries": [
                    41,
                    24
                ],
                "tag_name": {
                    "raw_string": "minecraft:reduce_water_ambient_spawns"
                }
            },
            {
                "entries": [
                    7,
                    34,
                    49,
                    59
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/bastion_remnant"
                }
            },
            {
                "entries": [
                    1,
                    33,
                    31,
                    47,
                    23,
                    27,
                    54,
                    28
                ],
                "tag_name": {
                    "raw_string": "minecraft:increased_fire_burnout"
                }
            },
            {
                "entries": [
                    8
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/woodland_mansion"
                }
            },
            {
                "entries": [
                    0,
                    19,
                    64,
                    62,
                    60,
                    61,
                    43,
                    63,
                    52,
                    32,
                    23,
                    27,
                    51,
                    47,
                    5
                ],
                "tag_name": {
                    "raw_string": "minecraft:has_structure/ruined_portal_mountain"
                }
            }
        ]
    }
}
    """)
}
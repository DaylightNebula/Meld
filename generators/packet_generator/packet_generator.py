import processor
import os
import re


code_base_path = "./src/commonMain/kotlin/io/github/daylightnebula/meld/server/networking/java/generated/"
kt_header = """package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import kotlinx.serialization.json.JsonObject
import net.benwoodworth.knbt.NbtCompound
import kotlin.uuid.Uuid
"""


def to_camel_case(snake_str: str) -> str:
    return "".join(x.capitalize() for x in re.split("[-_ ]", snake_str.lower()))


def to_lower_camel_case(snake_str) -> str:
    upper_camel = to_camel_case(snake_str)
    return upper_camel[0].lower() + upper_camel[1:]


def get_kt_type(type: str, mc_pref: bool, name: str) -> str:
    if type == "Byte Array":
        type = "ByteArray"
    if type == "ID or Sound Event":
        type = "VarInt"
    if type == "Text Component":
        type = "TextComponent"
    if type == "Fixed BitSet":
        type = "BitSet"
    if type.startswith("of "):
        type = type[3:]
    if type.startswith("Prefixed Optional"):
        type = "Optional" + type[len("Prefixed Optional"):]
    if type.startswith("Prefixed Array"):
        type = "Array" + type[len("Prefixed Array"):]
    if type.startswith("Optionalof"):
        tokens = type.split("of")
    else:
        tokens = type.split(" ")
    print(name, "Tokens", type, tokens)
    match tokens[0]:
        case "":
            return ""
        case "Boolean":
            return "Boolean"
        case "String" | "Identifier":
            return "String"
        case "Long":
            return "Long"
        case "Int":
            return "Int"
        case "Short":
            return "Short"
        case "Byte":
            return "Byte"
        case "Float":
            return "Float"
        case "Double":
            return "Double"
        case "NBT":
            if mc_pref: return "NBT"
            else: return "NbtCompound"
        case "Position":
            if mc_pref: return "Position"
            else: return "Float3"
        case "Slot":
            return "Slot"
        case "ExplosionData":
            return "ExplosionData"
        case "Angle":
            if mc_pref: return "Angle"
            else: return "Float"
        case "ByteArray":
            return "ByteArray"
        case "PlayerInfoUpdateData":
            return "PlayerInfoUpdateData"
        case "Unsigned":
            return f"U{get_kt_type(' '.join(tokens[1:]), mc_pref, name)}"
        case "UUID":
            return "Uuid"
        case "Chunk":
            return "Chunk"
        case "Light":
            return "LightData"
        case "ParticleData":
            return "ParticleData"
        case "Recipe":
            return "Recipe"
        case "BitSet":
            return "BitSet"
        case "EnumSet":
            return "BitSet"
        case "EntityMetadata":
            return "EntityMetadata"
        case "Advancement":
            return "Advancement"
        case "VarInt":
            if mc_pref: return "VarInt"
            else: return "Int"
        case "VarLong":
            if mc_pref: return "VarLong"
            else: return "Long"
        case "JSON Text Component" | "JSON" | "TextComponent":
            return "JsonObject"
        case "LoginEntry":
            return "LoginEntry"
        case "Optional":
            next = get_kt_type(' '.join(tokens[1:]), mc_pref, name)
            if mc_pref: return next
            else: return f"{next}?"
        case "Array":
            return f"Array<{get_kt_type(' '.join(tokens[1:]), mc_pref, name)}>"
        case _:
            raise Exception(f"Could not convert \"{tokens[0]}\" to kotlin type")


def build_script_file(entry: processor.PacketTableEntry):
    # create class name and open file
    class_name = entry.bound_to + entry.state + to_camel_case(entry.name)
    file = open(code_base_path + class_name + ".kt", "w")

    # translate state
    match entry.state:
        case "Configuration":
            state = "CONFIG"
        case "Handshaking":
            state = "HANDSHAKE"
        case "Status":
            state = "STATUS"
        case "Login":
            state = "LOGIN"
        case "Play":
            state = "IN_GAME"
        case _:
            raise Exception(f"No state {entry.state} registered!")

    # todo packet parameters
    # todo encode packet
    # todo decode packet

    # define params
    params = []
    for element in entry.content:
        # convert type
        name = to_lower_camel_case(element.name)
        type = get_kt_type(element.type, False, entry.name)
        params.append(f"{name}: {type}")
    if len(params) > 0:
        rendered_params = "\n\t" + ",\n\t".join(params) + "\n"
    else: rendered_params = ""

    # render constructor
    construct = []
    for element in entry.content:
        name = to_lower_camel_case(element.name)
        type = get_kt_type(element.type, True, entry.name)
        construct.append(f"{name} = reader.read{type}()")
    if len(construct) > 0:
        rendered_construct = "\n\t\t\t" + ",\n\t\t\t".join(construct) + "\n\t\t"
    else:
        rendered_construct = ""

    # build and write final file
    file.write(f"""{kt_header}
@OptIn(ExperimentalUuidApi::class)
class {class_name}({rendered_params}): JavaPacket {{
    companion object: JavaPacket.Creator<{class_name}> {{
        override val ID: Int = {entry.id}
        override val STATE: JavaConnectionState = JavaConnectionState.{state}
        override fun decode(reader: AbstractReader): {class_name} = {class_name}({rendered_construct})
    }}
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {{
    }}
}}
""")


# create code base path and make sure it exists
if not os.path.exists(code_base_path):
    os.mkdir(code_base_path)


# empty code base path
for root, dirs, files in os.walk(code_base_path):
    for file in files:
        os.remove(code_base_path + file)


# build all entries
for entry in processor.table_entries:
    build_script_file(entry)

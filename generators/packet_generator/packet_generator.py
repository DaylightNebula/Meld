from dataclasses import dataclass

import processor
import os
import re


code_base_path = "./src/commonMain/kotlin/io/github/daylightnebula/meld/server/networking/java/generated/"
kt_header = """package io.github.daylightnebula.meld.server.networking.java.generated

import dev.romainguy.kotlin.math.*
import io.github.daylightnebula.meld.server.networking.*
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import kotlinx.serialization.json.JsonObject
import net.benwoodworth.knbt.NbtCompound
import kotlin.uuid.Uuid
import kotlin.uuid.ExperimentalUuidApi
"""


def to_camel_case(snake_str: str) -> str:
    return "".join(x.capitalize() for x in re.split("[-_ ]", snake_str.lower()))


def to_lower_camel_case(snake_str) -> str:
    upper_camel = to_camel_case(snake_str)
    return upper_camel[0].lower() + upper_camel[1:]


@dataclass
class KtType:
    type: str
    read_func: str
    write_func: str


def get_kt_type(name: str, type: list[str]) -> KtType:
    if type[0].startswith("Unsigned"):
        raw_type = type[0][len("Unsigned "):]
        type[0] = f"U{raw_type}"

    match type[0]:
        case "String" | "Identifier":
            return KtType(
                type="String",
                read_func="reader.readString()",
                write_func="writer.writeString(<var>)"
            )
        case "NBT":
            return KtType(
                type="NbtCompound",
                read_func="reader.readNBT()",
                write_func="writer.writeNBT(<var>)"
            )
        case "Position":
            return KtType(
                type="Float3",
                read_func="reader.readFloat3()",
                write_func="writer.writeFloat3(<var>)"
            )
        case "Angle":
            return KtType(
                type="Float",
                read_func="reader.readAngle()",
                write_func="writer.writeAngle(<var>)"
            )
        case "UUID" | "Uuid":
            return KtType(
                type="Uuid",
                read_func="reader.readUuid()",
                write_func="writer.writeUuid(<var>)"
            )
        case "VarInt":
            return KtType(
                type="Int",
                read_func="reader.readVarInt()",
                write_func="writer.writeVarInt(<var>)"
            )
        case "VarLong":
            return KtType(
                type="Long",
                read_func="reader.readVarLong()",
                write_func="writer.writeVarLong(<var>)"
            )
        case "Byte Array" | "ByteArray":
            return KtType(
                type="ByteArray",
                read_func="reader.readByteArray()",
                write_func="writer.writeByteArray(<var>)"
            )
        case "Text Component" | "TextComponent":
            return KtType(
                type="JsonObject",
                read_func="reader.readJsonObject()",
                write_func="writer.writeJsonObject(<var>)"
            )
        case "JSON Text Component" | "JSON" | "TextComponent":
            return KtType(
                type="JsonObject",
                read_func="reader.readJsonObject()",
                write_func="writer.writeJsonObject(<var>)"
            )
        case "Advancement progress":
            return KtType(
                type="AdvancementProgress",
                read_func="reader.readAdvancementProgress()",
                write_func="writer.writeAdvancementProgress()"
            )
        case "Optional" | "Prefixed Optional":
            next = get_kt_type(name, type[1:])
            return KtType(
                type=f"{next.type}?",
                read_func=f"reader.readOptional {{ {next.read_func} }}",
                write_func=f"writer.writeOptional(<var>) {{ <var> -> {next.write_func} }}"
            )
        case "Array" | "Prefixed Array":
            print(name, "Typing", type)
            next = get_kt_type(name, type[1:])
            return KtType(
                type=f"Array<{next.type}>",
                read_func=f"reader.readArray {{ {next.read_func} }}",
                write_func=f"writer.writeArray(<var>) {{ <var> -> {next.write_func} }}"
            )
        case _:
            return KtType(
                type=type[0],
                read_func=f"reader.read{type[0]}()",
                write_func=f"writer.write{type[0]}(<var>)"
            )


def build_script_file(idx: int, entry: processor.PacketTableEntry):
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

    # define params
    params = []
    for element in entry.content:
        # convert type
        name = to_lower_camel_case(element.name)
        type = get_kt_type(entry.name, element.type)
        params.append(f"val {name}: {type.type}")
    if len(params) > 0:
        rendered_params = "\n\t" + ",\n\t".join(params) + "\n"
    else: rendered_params = ""

    # render constructor
    construct = []
    for element in entry.content:
        name = to_lower_camel_case(element.name)
        type = get_kt_type(entry.name, element.type)
        reader = type.read_func.replace("<var>", name)
        construct.append(f"{name} = {reader}")
    if len(construct) > 0:
        rendered_construct = "\n\t\t\t" + ",\n\t\t\t".join(construct) + "\n\t\t"
    else:
        rendered_construct = ""

    # render encoder
    encoder = []
    for element in entry.content:
        name = to_lower_camel_case(element.name)
        type = get_kt_type(entry.name, element.type)
        encoder.append(type.write_func.replace("<var>", name))
    if len(encoder) > 0:
        rendered_encoder = "\n\t\t" + "\n\t\t".join(encoder) + "\n\t"
    else:
        rendered_encoder = ""

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
    
    override fun encode(writer: ByteWriter) {{{rendered_encoder}}}
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
for idx, entry in enumerate(processor.table_entries):
    build_script_file(idx, entry)

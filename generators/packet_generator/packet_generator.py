import processor
import os


code_base_path = "./src/commonMain/kotlin/io/github/daylightnebula/meld/server/networking/java/generated/"
kt_header = """package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
"""


def to_camel_case(snake_str: str) -> str:
    return "".join(x.capitalize() for x in snake_str.lower().split("_"))


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
            state = "PLAY"
        case _:
            raise Exception(f"No state {entry.state} registered!")

    # todo packet parameters
    # todo encode packet
    # todo decode packet

    # build and write final file
    file.write(f"""{kt_header}
class {class_name}: JavaPacket {{
    companion object: JavaPacket.Creator<{class_name}> {{
        override val ID: Int = {entry.id}
        override val STATE: JavaConnectionState = JavaConnectionState.{state}
        override fun create(): {class_name} = {class_name}()
    }}
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    override fun encode(writer: ByteWriter) {{}}
    override fun decode(writer: AbstractReader) {{}}
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

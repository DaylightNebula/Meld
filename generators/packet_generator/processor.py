import itertools
import os
import re
from dataclasses import dataclass

# make sure packet description exists
path = "../generators/packet_generator/packet_description.txt"
if not os.path.exists(path):
    raise Exception("Packet description not created!")

# load base text
base_text = open(path, "r", errors="ignore").readlines()

# get wiki tables
tables: list[list[str]] = []
builder: list[str] = []
reading = False
for line in base_text:
    if not reading:
        if line.startswith("{| "):
            reading = True
    else: # reading
        if "|}" in line:
            reading = False
            if builder[0] == "|-":
                content = builder[1:]
            else:
                content = builder
            if content[0] == "! Packet ID": tables.append(content)
            builder = []
        else:
            if len(builder) != 0 or not line.startswith("|-"):
                builder.append(line.strip())

# process types out of tables into a constant structure
@dataclass
class PacketElement:
    name: str
    type: str
    notes: str

@dataclass
class PacketTableEntry:
    id: str
    name: str
    state: str
    bound_to: str
    content: list[PacketElement]

def convert_table_to_entry(table: list[str]) -> PacketTableEntry:
    # get packet ID
    packet_id_text = [s for s in table if '\'\'protocol:\'\'<br/><code>' in s or "| 0x" in s][0]
    if packet_id_text.startswith('| 0x'):
        packet_id = packet_id_text[2:]
    else:
        packet_id_content = re.findall(r"''protocol:''<br\/><code>(.*?)<\/code>", packet_id_text)
        if len(packet_id_content) == 1:
            packet_id = packet_id_content[0]
        else:
            packet_id = '0x00'
            print("No packet ID in", table)
    if len(packet_id) == "4": print("Not packet ID length")

    # get packet name
    packet_name_text = [s for s in table if '\'\'resource:\'\'<br/><code>' in s]
    if len(packet_name_text) > 0:
        packet_name = re.findall(r"''resource:''<br\/><code>(.*?)<\/code>", packet_name_text[0])[0]
    else:
        packet_name = "legacy_ping"

    # cluster lines
    clusters: list[list[str]] = []
    cluster: list[str] = []
    ignore_fields = False
    for line in table:
        if line.startswith("|-"):
            clusters.append(cluster)
            cluster = []
        else:
            if "no fields" in line:
                ignore_fields = True
            cluster.append(line)
    clusters.append(cluster)

    # build entries
    entries: list[PacketElement] = []
    if not ignore_fields:
        for idx, cluster in enumerate(clusters[1:]):
            if '||' in cluster[0]:
                cluster = cluster[0].split("||")

            # filter
            if "See below" in cluster[-2]: continue
            if len(cluster) <= 3 and "| rowspan=" in cluster[0]: continue

            # stop if we return to ! lines
            if cluster[0].startswith("! "): break

            # make sure we have enough lines
            if len(cluster) < 3: cluster.append("|")

            # decode type
            type_tokens = [s for s in cluster if "| {{Type" in s]
            if len(type_tokens) > 0:
                tokens = re.findall(r"\{\{Type\|(.*?)\}\}", type_tokens[0])
                if len(tokens) > 1:
                    type = f"{tokens[0]} {tokens[1]}"
                else:
                    type = tokens[0]
            else:
                type = cluster[-2][2:]

        # decode name
        entry_name = cluster[-3][2:].split("|")[-1]
        if entry_name[-1] == "?":
            entry_name = entry_name[:-1]

        # add entry
        entries.append(PacketElement(
            name = entry_name,
            type = type,
            notes = cluster[-1]
        ))

    # find state
    state = clusters[1][clusters[0].index("! State")].split("|")[-1].strip()
    bound_to = clusters[1][clusters[0].index("! Bound To")].split("|")[-1].strip()

    return PacketTableEntry(
        id = packet_id,
        name = packet_name,
        state = state,
        bound_to = bound_to,
        content = entries
    )


table_entries = []
for table in tables:
    entry = convert_table_to_entry(table)
    table_entries.append(entry)
# files
file = open('table.txt', 'r')
output = open('output.txt', 'w')

# vars
subindex = 0
id = 0
name = ""
strid = ""

# add player option
print("    PLAYER(Integer.MAX_VALUE, \"REPLACE\", \"Player\"),", file = output)

# for each line
for line in file.readlines():
    if line == "|-\n":
        subindex = 0
        if id != "! Type" and id != "{class=\"wikitable\"": 
            print("    " + name.upper().replace(" ", "_") + "(" + id + ", \"" + strid + "\", \"" + name + "\"),", file = output)
    else:
        token = line.replace("| ", "").replace("\n", "")
        if subindex == 0:
            id = token
        elif subindex == 1:
            name = token
        elif subindex == 4:
            strid = token.replace("<code>", "").replace("</code>", "")
        subindex += 1

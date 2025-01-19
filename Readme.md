# Meld
A custom bare-bones Kotlin Minecraft server implementation with the goal of being light-weight, fast and 
cross-platform.  Meld is designed with the goal of allowing for completely custom games using Minecraft as their 
engine.  Therefore, everything is customizable and the server itself just serves as an interface between the modules 
and the clients, and does not add even basic functionality.

To add basic functionality to the servers, it is recommended to use the following standard modules unless you really 
know what you are doing:
 - Meld-Module-Entities
 - Meld-Module-Inventories
 - Meld-Module-Login
 - Meld-Module-Player
 - Meld-Module-World

## Goals
1. Cross platform
2. Malleability + Modularity
3. Light As A Feather + Speeeeeeeeed

## Platforms
| Version | Support      |
|---------|--------------|
| Java    | Complete     |
| Bedrock | In Progress  |

## Protocol
To get a copy of the current Minecraft protocol, we decompile the current Minecraft client via https://github.com/hube12/DecompilerMC
inside the decompiler directory that is "git ignored", and then use the protocol directory generated to generate our own
copy of the protocol via a python script.

## Modules
A core part of Meld is its modules system.  These are like the plugins you may have seen in Spigot or Paper.  However,
they are much more core and low-level in Meld (you may have noticed the standard modules in this project that handle 
everything from logins to player states).



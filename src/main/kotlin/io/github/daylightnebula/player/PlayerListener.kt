package io.github.daylightnebula.player

import io.github.daylightnebula.Meld
import io.github.daylightnebula.entities.JavaEntityStatusPacket
import io.github.daylightnebula.events.Event
import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.events.EventHandler
import io.github.daylightnebula.events.EventListener
import io.github.daylightnebula.login.LoginEvent
import io.github.daylightnebula.login.packets.JavaDifficultyPacket
import io.github.daylightnebula.networking.bedrock.BedrockConnection
import io.github.daylightnebula.networking.java.JavaConnection
import io.github.daylightnebula.player.packets.JavaSetPlayerPositionPacket
import io.github.daylightnebula.player.packets.JavaSetSpawnPositionPacket
import io.github.daylightnebula.player.packets.login.JavaAbilitiesPacket
import io.github.daylightnebula.player.packets.login.JavaFeatureFlagsPacket
import io.github.daylightnebula.player.packets.join.JavaJoinPacket
import io.github.daylightnebula.registries.BedrockRegistries
import io.github.daylightnebula.worlds.chunks.packets.JavaSetCenterChunkPacket
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i
import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.protocol.bedrock.data.*
import org.cloudburstmc.protocol.bedrock.packet.BiomeDefinitionListPacket
import org.cloudburstmc.protocol.bedrock.packet.CreativeContentPacket
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket
import org.cloudburstmc.protocol.common.util.OptionalBoolean
import java.util.*

class PlayerListener: EventListener {
    // on login
    @EventHandler
    fun onLoginEvent(event: LoginEvent) {
        println("Login from ${event.connection}")
        // TODO difficulty packet + bedrock
        // TODO held item slot packet + bedrock

        // create player and broadcast pre join
        val player = Player(event.connection, event.uid)
        event.connection.player = player
        EventBus.callEvent(PreJoinEvent(player))

        // match login handler based on connection type
        when (event.connection) {
            // java connections
            is JavaConnection -> {
                // send join packets
                event.connection.sendPacket(JavaJoinPacket(player))
                event.connection.sendPacket(JavaFeatureFlagsPacket())
                event.connection.sendPacket(JavaAbilitiesPacket())
                event.connection.sendPacket(JavaEntityStatusPacket(player))
                event.connection.sendPacket(JavaDifficultyPacket())
                event.connection.sendPacket(JavaSetCenterChunkPacket())

                // send positions
                event.connection.sendPacket(JavaSetSpawnPositionPacket(Vector3i.from(0, 60, 0), 0f))
                event.connection.sendPacket(JavaSetPlayerPositionPacket(0.0, 60.0, 0.0, 0f, 0f))
            }

            // bedrock connections
            is BedrockConnection -> {
                //https://github.com/GeyserMC/Geyser/blob/b344e21f7f729998eb4b1b7d948bc184594b8864/core/src/main/java/org/geysermc/geyser/session/GeyserSession.java#L620
                // send start game packet
                event.connection.sendPacket(StartGamePacket().apply {
                    seed = -1L
                    dimensionId = 0
                    generatorId = 1
                    playerGameType = player.gameMode.bedrockGameMode()
                    levelGameType = player.gameMode.bedrockGameMode()
                    difficulty = 1
                    defaultSpawn = Vector3i.ZERO
                    playerPosition = Vector3f.ZERO
                    rotation = Vector2f.ZERO
                    isAchievementsDisabled = true
                    currentTick = -1
                    eduEditionOffers = 0
                    isEduFeaturesEnabled = false
                    rainLevel = 0F
                    lightningLevel = 1f
                    isMultiplayerGame = true
                    isBroadcastingToLan = true
                    platformBroadcastMode = GamePublishSetting.PUBLIC
                    xblBroadcastMode = GamePublishSetting.PUBLIC
                    isCommandsEnabled = false
                    isTexturePacksRequired = false
                    isBonusChestEnabled = false
                    isStartingWithMap = false
                    isTrustingPlayers = true
                    defaultPlayerPermission = PlayerPermission.MEMBER
                    serverChunkTickRange = Meld.simDistance
                    isBehaviorPackLocked = false
                    isResourcePackLocked = false
                    isFromLockedWorldTemplate = false
                    isUsingMsaGamertagsOnly = false
                    isFromWorldTemplate = false
                    isWorldTemplateOptionLocked = false
                    spawnBiomeType = SpawnBiomeType.DEFAULT
                    customBiomeName = ""
                    educationProductionId = ""
                    forceExperimentalGameplay = OptionalBoolean.empty()
                    levelId = Meld.serverName
                    levelName = Meld.serverName
                    premiumWorldTemplateId = "00000000-0000-0000-0000-000000000000"
                    enchantmentSeed = 0
                    multiplayerCorrelationId = ""
                    itemDefinitions = listOf()
                    vanillaVersion = "*"
                    isInventoriesServerAuthoritative = true
                    serverEngine = ""
                    playerPropertyData = NbtMap.EMPTY
                    worldTemplateId = UUID.randomUUID()
                    chatRestrictionLevel = ChatRestrictionLevel.NONE
                    authoritativeMovementMode = AuthoritativeMovementMode.CLIENT
                    rewindHistorySize = 0
                    isServerAuthoritativeBlockBreaking = false
                })

                // send biomes definitions
                event.connection.sendPacket(BiomeDefinitionListPacket().apply {
                    definitions = BedrockRegistries.BIOMES_NBT.get()
                })

                // send creative content
                event.connection.sendPacket(CreativeContentPacket().apply {
                    contents = arrayOf()
                })

                // send player spawn status packet
                event.connection.sendPacket(PlayStatusPacket().apply {
                    status = PlayStatusPacket.Status.PLAYER_SPAWN
                })
            }
        }

        // mark player joined
        player.joinSent = true

        // call join event
        EventBus.callEvent(JoinEvent(player))
    }
}

class PreJoinEvent(val player: Player): Event
class JoinEvent(val player: Player): Event
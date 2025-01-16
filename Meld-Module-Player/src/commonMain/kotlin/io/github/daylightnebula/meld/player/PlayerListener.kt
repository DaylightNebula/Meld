package io.github.daylightnebula.meld.player

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.entities.packets.JavaEntityStatusPacket
import io.github.daylightnebula.meld.login.LoginEvent
import io.github.daylightnebula.meld.player.extensions.hasPlayer
import io.github.daylightnebula.meld.player.extensions.player
import io.github.daylightnebula.meld.player.packets.JavaDifficultyPacket
import io.github.daylightnebula.meld.player.packets.JavaSetPlayerPositionPacket
import io.github.daylightnebula.meld.player.packets.JavaSetSpawnPositionPacket
import io.github.daylightnebula.meld.player.packets.join.JavaJoinPacket
import io.github.daylightnebula.meld.player.packets.login.JavaAbilitiesPacket
import io.github.daylightnebula.meld.server.ConnectionAbortedEvent
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.events.EventExecutor
import io.github.daylightnebula.meld.server.events.EventListener
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class PlayerListener: EventListener {
    override val executors: List<EventExecutor<*, *>> = listOf(
        EventExecutor(ConnectionAbortedEvent, this::onConnectionAborted),
        EventExecutor(LoginEvent, this::onLoginEvent)
    )

    // on disconnect
    fun onConnectionAborted(event: ConnectionAbortedEvent) {
        try {
            if (event.connection.hasPlayer) event.connection.player.despawn()
        } catch (_: Exception) {}
    }

    // on login
    @OptIn(ExperimentalUuidApi::class)
    fun onLoginEvent(event: LoginEvent) {
        // create player and broadcast pre join
        val player = Player(event.connection, event.uid)
        event.connection.player = player
        EventBus.callEvent(PreJoinEvent(player))

        // match login handler based on connection type
        when (event.connection) {
            // java connections
            is JavaConnection -> {
                val connection = event.connection as JavaConnection

                // send join packets
                connection.sendPacket(JavaJoinPacket(player))
                connection.sendPacket(JavaAbilitiesPacket())
                connection.sendPacket(JavaEntityStatusPacket(player.id, 24))
                connection.sendPacket(JavaDifficultyPacket())

                // send positions
                connection.sendPacket(JavaSetSpawnPositionPacket(Float3(0f, 60f, 0f), 0f))
                connection.sendPacket(JavaSetPlayerPositionPacket(Float3(0f, 60f, 0f), Float3(), Float2(0f, 0f)))
            }

            // bedrock connections
//            is BedrockConnection -> {
//                //https://github.com/GeyserMC/Geyser/blob/b344e21f7f729998eb4b1b7d948bc184594b8864/core/src/main/java/org/geysermc/geyser/session/GeyserSession.java#L620
//                // send start game packet
//                (event.connection as BedrockConnection).sendPacket(StartGamePacket().apply {
//                    seed = -1L
//                    dimensionId = 0
//                    generatorId = 1
//                    playerGameType = player.gameMode.bedrockGameMode()
//                    levelGameType = player.gameMode.bedrockGameMode()
//                    difficulty = 1
//                    defaultSpawn = Vector3i.ZERO
//                    playerPosition = Vector3f.ZERO
//                    rotation = Vector2f.ZERO
//                    isAchievementsDisabled = true
//                    currentTick = -1
//                    eduEditionOffers = 0
//                    isEduFeaturesEnabled = false
//                    rainLevel = 0F
//                    lightningLevel = 1f
//                    isMultiplayerGame = true
//                    isBroadcastingToLan = true
//                    platformBroadcastMode = GamePublishSetting.PUBLIC
//                    xblBroadcastMode = GamePublishSetting.PUBLIC
//                    isCommandsEnabled = false
//                    isTexturePacksRequired = false
//                    isBonusChestEnabled = false
//                    isStartingWithMap = false
//                    isTrustingPlayers = true
//                    defaultPlayerPermission = PlayerPermission.MEMBER
//                    serverChunkTickRange = io.github.daylightnebula.meld.server.Meld.simDistance
//                    isBehaviorPackLocked = false
//                    isResourcePackLocked = false
//                    isFromLockedWorldTemplate = false
//                    isUsingMsaGamertagsOnly = false
//                    isFromWorldTemplate = false
//                    isWorldTemplateOptionLocked = false
//                    spawnBiomeType = SpawnBiomeType.DEFAULT
//                    customBiomeName = ""
//                    educationProductionId = ""
//                    forceExperimentalGameplay = OptionalBoolean.empty()
//                    levelId = io.github.daylightnebula.meld.server.Meld.serverName
//                    levelName = io.github.daylightnebula.meld.server.Meld.serverName
//                    premiumWorldTemplateId = "00000000-0000-0000-0000-000000000000"
//                    enchantmentSeed = 0
//                    multiplayerCorrelationId = ""
//                    itemDefinitions = listOf()
//                    vanillaVersion = "*"
//                    isInventoriesServerAuthoritative = true
//                    serverEngine = ""
//                    playerPropertyData = NbtMap.EMPTY
//                    worldTemplateId = UUID.randomUUID()
//                    chatRestrictionLevel = ChatRestrictionLevel.NONE
//                    authoritativeMovementMode = AuthoritativeMovementMode.CLIENT
//                    rewindHistorySize = 0
//                    isServerAuthoritativeBlockBreaking = false
//                })
//
//                // send biomes definitions
//                (event.connection as BedrockConnection).sendPacket(BiomeDefinitionListPacket().apply {
//                    definitions = BedrockRegistries.BIOMES_NBT.get()
//                })
//
//                // send creative content
//                (event.connection as BedrockConnection).sendPacket(CreativeContentPacket().apply {
//                    contents = arrayOf()
//                })
//
//                // send player spawn status packet
//                (event.connection as BedrockConnection).sendPacket(PlayStatusPacket().apply {
//                    status = PlayStatusPacket.Status.PLAYER_SPAWN
//                })
//            }
        }

        // mark player joined
        player.joinSent = true

        // call join event
        EventBus.callEvent(JoinEvent(player))
    }
}

@OptIn(ExperimentalUuidApi::class)
class PreJoinEvent(val player: Player): Event {
    companion object: Event.Data<PreJoinEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PreJoinEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
class JoinEvent(val player: Player): Event {
    companion object: Event.Data<JoinEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(JoinEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

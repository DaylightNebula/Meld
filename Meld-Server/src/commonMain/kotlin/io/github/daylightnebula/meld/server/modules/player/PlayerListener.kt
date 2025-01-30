package io.github.daylightnebula.meld.server.modules.player

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.SpawnInfo
import io.github.daylightnebula.meld.server.modules.login.LoginEvent
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.events.EventExecutor
import io.github.daylightnebula.meld.server.events.EventListener
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.entities.Player
import io.github.daylightnebula.meld.server.events.ConnectionAbortedEvent
import io.github.daylightnebula.meld.server.generated.JavaClientPlayAbilities
import io.github.daylightnebula.meld.server.generated.JavaClientPlayDifficulty
import io.github.daylightnebula.meld.server.generated.JavaClientPlayEntityMetadata
import io.github.daylightnebula.meld.server.generated.JavaClientPlayEntityStatus
import io.github.daylightnebula.meld.server.generated.JavaClientPlayEntityUpdateAttributes
import io.github.daylightnebula.meld.server.generated.JavaClientPlayGameStateChange
import io.github.daylightnebula.meld.server.generated.JavaClientPlayHeldItemSlot
import io.github.daylightnebula.meld.server.generated.JavaClientPlayInitializeWorldBorder
import io.github.daylightnebula.meld.server.generated.JavaClientPlayLogin
import io.github.daylightnebula.meld.server.generated.JavaClientPlayPosition
import io.github.daylightnebula.meld.server.generated.JavaClientPlaySetSlot
import io.github.daylightnebula.meld.server.generated.JavaClientPlaySetTickingState
import io.github.daylightnebula.meld.server.generated.JavaClientPlaySetTitleTime
import io.github.daylightnebula.meld.server.generated.JavaClientPlaySimulationDistance
import io.github.daylightnebula.meld.server.generated.JavaClientPlaySpawnPosition
import io.github.daylightnebula.meld.server.generated.JavaClientPlayStepTick
import io.github.daylightnebula.meld.server.generated.JavaClientPlayUpdateTime
import io.github.daylightnebula.meld.server.generated.JavaClientPlayUpdateViewDistance
import io.github.daylightnebula.meld.server.generated.JavaClientPlayWindowItems
import io.github.daylightnebula.meld.server.generated.TypeSlot
import io.github.daylightnebula.meld.server.networking.java.packets.JavaClientPlayPlayerInfo
import io.github.daylightnebula.meld.server.utils.TeleportCounter
import io.github.daylightnebula.meld.server.utils.hasPlayer
import io.github.daylightnebula.meld.server.utils.player
import kotlin.random.Random
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
        val player = Player(event.connection, event.uid, position = Float3(0f, 40f, 0f))
        event.connection.player = player
        EventBus.callEvent(PreJoinEvent(player))

        // match login handler based on connection type
        when (event.connection) {
            // java connections
            is JavaConnection -> {
                val connection = event.connection

                // send join packets
                connection.sendPacket(JavaClientPlayLogin(
                    entityId = player.id,
                    isHardcore = false,
                    worldNames = listOf("overworld"),
                    maxPlayers = Meld.config.maxPlayers,
                    viewDistance = Meld.config.viewDistance,
                    simulationDistance = Meld.config.simDistance,
                    reducedDebugInfo = false,
                    enableRespawnScreen = true, // todo what if this is false
                    doLimitedCrafting = false,
                    worldState = SpawnInfo(
                        dimension = 0,
                        name = "minecraft:overworld",
                        hashedSeed = Random.nextLong(),
                        gameMode = player.gameMode.ordinal.toUByte(),
                        previousGameMode = -1,
                        isDebug = false,
                        isFlat = false,
                        death = null,
                        portalCooldown = 0,
                        seaLevel = 40
                    ),
                    enforcesSecureChat = false
                ))
                connection.sendPacket(JavaClientPlayDifficulty(
                    difficulty = 0u,
                    difficultyLocked = true
                ))
                connection.sendPacket(JavaClientPlayAbilities(
                    flags = 0,
                    flyingSpeed = 0.05f,
                    walkingSpeed = 0.1f
                ))
                connection.sendPacket(JavaClientPlayHeldItemSlot(0))
                connection.sendPacket(JavaClientPlayEntityStatus(
                    entityId = player.id,
                    entityStatus = 24
                ))
                connection.sendPacket(JavaClientPlayDifficulty(
                    difficulty = 0u,
                    difficultyLocked = true
                ))
                connection.sendPacket(JavaClientPlaySetTickingState(20f, false))
                connection.sendPacket(JavaClientPlayStepTick(0))
                connection.sendPacket(JavaClientPlayUpdateViewDistance(Meld.config.viewDistance))
                connection.sendPacket(JavaClientPlaySimulationDistance(Meld.config.simDistance))
                connection.sendPacket(JavaClientPlayUpdateTime(
                    age = 1057,
                    time = 471128696,
                    tickDayTime = false
                ))
                connection.sendPacket(JavaClientPlayInitializeWorldBorder(
                    x = 0.0,
                    z = 0.0,
                    oldDiameter = 59999968.0,
                    newDiameter = 59999968.0,
                    speed = 1,
                    portalTeleportBoundary = 0,
                    warningBlocks = 0,
                    warningTime = 0
                ))
                connection.sendPacket(JavaClientPlayGameStateChange(13u, 0f))

                // send positions
                connection.sendPacket(JavaClientPlaySpawnPosition(
                    location = player.position,
                    angle = 0f
                ))
                connection.sendPacket(JavaClientPlayPosition(
                    teleportId = TeleportCounter.nextID(),
                    x = player.position.x.toDouble(),
                    y = player.position.y.toDouble(),
                    z = player.position.z.toDouble(),
                    dx = 0.0,
                    dy = 0.0,
                    dz = 0.0,
                    yaw = player.rotation.y,
                    pitch = player.rotation.x,
                    flags = 0
                ))
                connection.sendPacket(JavaClientPlayPlayerInfo(
                    data = listOf(JavaClientPlayPlayerInfo.Data(
                        uuid = player.uid,
                        actions = listOf(JavaClientPlayPlayerInfo.AddPlayerAction(
                            name = "DaylightNebula",
                            properties = listOf()
                        ))
                    ))
                ))
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

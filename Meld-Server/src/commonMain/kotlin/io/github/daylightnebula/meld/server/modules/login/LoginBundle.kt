package io.github.daylightnebula.meld.server.modules.login

import io.github.daylightnebula.meld.server.*
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.java.*
import io.github.daylightnebula.meld.server.registries.RegistryCodec
import io.github.daylightnebula.meld.server.registries.codec.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
private val tempUIDStorage = mutableMapOf<IConnection<*>, Uuid>()
object LoginBundle: PacketBundle {
    override fun registerJavaPackets() = javaPackets(
        javaPacket(
            creator = JavaServerHandshakeSetProtocol,
            execute = this::onHandshake
        ),

        javaPacket(
            creator = JavaServerStatusPingStart,
            execute = this::onStatusStatus
        ),

        javaPacket(
            creator = JavaServerStatusPing,
            execute = this::onStatusPing
        ),

        javaPacket(
            creator = JavaServerLoginLoginStart,
            execute = this::onInitiateLogin
        ),

        javaPacket(
            creator = JavaServerConfigSettings,
            execute = this::onClientInfo
        ),

        javaPacket(
            creator = JavaServerLoginLoginAcknowledged,
            execute = this::onLoginAcknowledged
        ),

        javaPacket(JavaServerConfigCustomPayload, this::onConfigMessage),
        javaPacket(JavaServerConfigFinishConfiguration, this::onFinishConfig),

        javaPacket(
            id = 0x07,
            state = JavaConnectionState.CONFIG,
            creator = PacketCommonSelectKnownPacks,
            execute = this::onClientPacksLoaded
        )
    )

    fun onHandshake(connection: JavaConnection, packet: JavaServerHandshakeSetProtocol) =
        when (packet.nextState) {
            1 -> connection.state = JavaConnectionState.STATUS
            2 -> connection.state = JavaConnectionState.LOGIN
            else -> throw IllegalArgumentException("Unknown handshake next state ${packet.nextState}")
        }

    fun onStatusStatus(connection: JavaConnection, packet: JavaServerStatusPingStart) =
        connection.sendPacket(
            JavaClientStatusServerInfo(
                response = Meld.json.encodeToString(JavaNetworkController.pingJson())
            )
        )

    fun onStatusPing(connection: JavaConnection, packet: JavaServerStatusPing) = connection.sendPacket(JavaClientStatusPing(packet.time))

    @OptIn(ExperimentalUuidApi::class)
    fun onInitiateLogin(connection: JavaConnection, packet: JavaServerLoginLoginStart) {
        tempUIDStorage[connection] = packet.playerUUID

        // respond
        println("Sending login success")
        connection.sendPacket(
            JavaClientLoginSuccess(
                uuid = tempUIDStorage[connection]!!,
                username = packet.username,
                properties = emptyList()
            )
        )
    }

    fun onLoginAcknowledged(connection: JavaConnection, packet: JavaServerLoginLoginAcknowledged) {
        // move to config state
        connection.state = JavaConnectionState.CONFIG
    }

    fun onClientInfo(connection: JavaConnection, packet: JavaServerConfigSettings) {
        connection.sendPacket(JavaClientConfigFeatureFlags(
            features = listOf("minecraft:vanilla")
        ))
        connection.sendPacket(PacketCommonSelectKnownPacks(listOf(
            PacketCommonSelectKnownPacks.Packs(
                namespace = "minecraft",
                id = "core",
                version = "1.21.4"
            )
        )), id = 0x0E)
    }

    fun onClientPacksLoaded(connection: JavaConnection, packet: PacketCommonSelectKnownPacks) {
        connection.sendPacket(registryClientPacket(BiomeRegistry))
        connection.sendPacket(registryClientPacket(ChatRegistry))
        connection.sendPacket(registryClientPacket(TrimPatternRegistry))
        connection.sendPacket(registryClientPacket(TrimMaterialRegistry))
        connection.sendPacket(registryClientPacket(WolfVariantRegistry))
        connection.sendPacket(registryClientPacket(PaintingVariantRegistry))
        connection.sendPacket(registryClientPacket(DimensionRegistry))
        connection.sendPacket(registryClientPacket(DamageTypeRegistry))
        connection.sendPacket(registryClientPacket(BannerPatternRegistry))
        connection.sendPacket(registryClientPacket(EnchantmentRegistry))
        connection.sendPacket(registryClientPacket(JukeboxSongRegistry))
        connection.sendPacket(registryClientPacket(InstrumentRegistry))
        connection.sendPacket(JavaClientConfigTags(
            DefaultTags.defaultTags["tags"]!!.jsonObject.map {
                JavaClientConfigTags.Tags(it.key, it.value.jsonArray.map { e ->
                    val entry = e.jsonObject
                    TypeTags(
                        tagName = entry["tag_name"]!!.jsonObject["raw_string"]!!.jsonPrimitive.content,
                        entries = entry["entries"]!!.jsonArray.map { it.jsonPrimitive.int }
                    )
                })
            }
        ))
        connection.sendPacket(JavaClientConfigFinishConfiguration())
    }

    @OptIn(ExperimentalUuidApi::class)
    fun onFinishConfig(connection: JavaConnection, packet: JavaServerConfigFinishConfiguration) {
        connection.state = JavaConnectionState.IN_GAME
        EventBus.callEvent(LoginEvent(connection, tempUIDStorage.remove(connection)!!))
    }

    fun onConfigMessage(connection: JavaConnection, packet: JavaServerConfigCustomPayload) =
        when (packet.channel) {
            "minecraft:brand" -> {
                connection.sendPacket(JavaClientConfigCustomPayload("minecraft:brand", packet.data))
            }

            else -> println("Unknown plugin message channel ${packet.channel}")
        }

//    @PacketHandler
//    fun onBedrockRequestNetworkSettings(connection: BedrockConnection, packet: RequestNetworkSettingsPacket) {
//        // send back network settings
//        connection.sendPacket(NetworkSettingsPacket().apply {
//            compressionAlgorithm = PacketCompressionAlgorithm.ZLIB
//            compressionThreshold = 512
//        })
//
//        // update compression settings
//        connection.session.setCompression(PacketCompressionAlgorithm.ZLIB)
//        connection.session.setCompressionLevel(9)
//        connection.compressionEnabled = true
//    }
//
//    @PacketHandler
//    fun onBedrockLogin(connection: BedrockConnection, packet: LoginPacket) {
//        connection.sendPacket(PlayStatusPacket().apply { status = PlayStatusPacket.Status.LOGIN_SUCCESS })
//        connection.sendPacket(ResourcePacksInfoPacket().apply {})
//    }
//
//    @PacketHandler
//    fun onBedrockClientCacheStatus(connection: BedrockConnection, packet: ClientCacheStatusPacket) =
//        println("WARN ClientCacheStatusPacket is not implemented")
//
//    @PacketHandler
//    fun onBedrockResourcePackClientResponse(connection: BedrockConnection, packet: ResourcePackClientResponsePacket) {
//        // unpack
//        val status = (packet as ResourcePackClientResponsePacket).status
//
//        // process depending on the status given in the input packet
//        when (status) {
//            ResourcePackClientResponsePacket.Status.HAVE_ALL_PACKS -> {
//                connection.sendPacket(ResourcePackStackPacket().apply {
//                    isForcedToAccept = false
//                    isExperimentsPreviouslyToggled = false
//                    gameVersion = "1.20"
//                })
//            }
//
//            ResourcePackClientResponsePacket.Status.COMPLETED -> {
//                // call login event
//                println("Resource pack completed")
//                // https://wiki.vg/Bedrock_Protocol#Start_Game
//            }
//
//            ResourcePackClientResponsePacket.Status.NONE -> TODO("Bedrock resourcepack $status not implemented")
//            ResourcePackClientResponsePacket.Status.REFUSED -> TODO("Bedrock resourcepack $status not implemented")
//            ResourcePackClientResponsePacket.Status.SEND_PACKS -> TODO("Bedrock resourcepack $status not implemented")
//
//            else -> throw NotImplementedError()
//        }
//    }
}

fun registryClientPacket(registry: RegistryCodec.Codec) = JavaClientConfigRegistryData(
    id = registry.name(),
    entries = registry.build().map { JavaClientConfigRegistryData.Entries(it.first, it.second) }
)

@OptIn(ExperimentalUuidApi::class)
class LoginEvent(
    val connection: IConnection<*>,
    val uid: Uuid
): Event {
    companion object: Event.Data<LoginEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(LoginEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}
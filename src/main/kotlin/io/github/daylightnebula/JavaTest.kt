package io.github.daylightnebula

import com.github.steveice10.mc.auth.data.GameProfile
import com.github.steveice10.mc.auth.exception.request.RequestException
import com.github.steveice10.mc.auth.service.AuthenticationService
import com.github.steveice10.mc.auth.service.MojangAuthenticationService
import com.github.steveice10.mc.auth.service.SessionService
import com.github.steveice10.mc.protocol.MinecraftConstants
import com.github.steveice10.mc.protocol.MinecraftProtocol
import com.github.steveice10.mc.protocol.ServerLoginHandler
import com.github.steveice10.mc.protocol.codec.MinecraftCodec
import com.github.steveice10.mc.protocol.data.ProtocolState
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import com.github.steveice10.mc.protocol.data.status.PlayerInfo
import com.github.steveice10.mc.protocol.data.status.ServerStatusInfo
import com.github.steveice10.mc.protocol.data.status.VersionInfo
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoBuilder
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler
import com.github.steveice10.mc.protocol.data.status.handler.ServerPingTimeHandler
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundSystemChatPacket
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket
import com.github.steveice10.opennbt.NBTIO
import com.github.steveice10.opennbt.tag.builtin.CompoundTag
import com.github.steveice10.packetlib.ProxyInfo
import com.github.steveice10.packetlib.Server
import com.github.steveice10.packetlib.Session
import com.github.steveice10.packetlib.event.server.ServerAdapter
import com.github.steveice10.packetlib.event.server.ServerClosedEvent
import com.github.steveice10.packetlib.event.server.SessionAddedEvent
import com.github.steveice10.packetlib.event.server.SessionRemovedEvent
import com.github.steveice10.packetlib.event.session.DisconnectedEvent
import com.github.steveice10.packetlib.event.session.PacketSendingEvent
import com.github.steveice10.packetlib.event.session.SessionAdapter
import com.github.steveice10.packetlib.packet.Packet
import com.github.steveice10.packetlib.tcp.TcpClientSession
import com.github.steveice10.packetlib.tcp.TcpServer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import java.io.DataInput
import java.io.DataInputStream
import java.io.IOException
import java.time.Instant
import java.util.Arrays
import java.util.zip.GZIPInputStream


object MinecraftProtocolTest {
    private const val SPAWN_SERVER = true
    private const val VERIFY_USERS = false
    private const val HOST = "127.0.0.1"
    private const val PORT = 25565
    private val PROXY: ProxyInfo? = null
//    private val AUTH_PROXY: Proxy = Proxy.NO_PROXY
//    private const val USERNAME = "Username"
//    private const val PASSWORD = "Password"
    @JvmStatic
    fun main(args: Array<String>) {
        if (SPAWN_SERVER) {
            val sessionService = SessionService()
//            sessionService.proxy = AUTH_PROXY
            val server: Server = TcpServer(
                HOST, PORT
            ) { MinecraftProtocol() }
            server.setGlobalFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService)
            server.setGlobalFlag(MinecraftConstants.VERIFY_USERS_KEY, VERIFY_USERS)
            server.setGlobalFlag(MinecraftConstants.SERVER_INFO_BUILDER_KEY, ServerInfoBuilder { session: Session? ->
                ServerStatusInfo(
                    VersionInfo(MinecraftCodec.CODEC.minecraftVersion, MinecraftCodec.CODEC.protocolVersion),
                    PlayerInfo(100, 0, arrayOfNulls<GameProfile>(0).toMutableList()),
                    Component.text("Hello world!"),
                    null,
                    false
                )
            })
            server.setGlobalFlag(MinecraftConstants.SERVER_LOGIN_HANDLER_KEY, ServerLoginHandler { session: Session ->
                session.send(
                    ClientboundLoginPacket(
                        0,
                        false,
                        GameMode.SURVIVAL,
                        GameMode.SURVIVAL,
                        arrayOf<String>("minecraft:world"),
                        loadNetworkCodec(),
                        "minecraft:overworld",
                        "minecraft:world",
                        0L,
                        100,
                        16,
                        16,
                        false,
                        false,
                        false,
                        false,
                        null,
                        0
                    )
                )
            })
//            server.setGlobalFlag(MinecraftConstants.SERVER_COMPRESSION_THRESHOLD, 100)
            server.addListener(object : ServerAdapter() {
                override fun serverClosed(event: ServerClosedEvent) {
                    println("Server closed.")
                }

                override fun sessionAdded(event: SessionAddedEvent) {
                    event.session.addListener(object : SessionAdapter() {
                        override fun packetReceived(session: Session, packet: Packet) {
                            println("Packet $packet")
//                            if (packet is ServerboundChatPacket) {
//                                val profile = event.session.getFlag<GameProfile>(MinecraftConstants.PROFILE_KEY)
//                                println(profile.name + ": " + (packet as ServerboundChatPacket).message)
//                                val msg: Component = Component.text("Hello, ")
//                                    .color(NamedTextColor.GREEN)
//                                    .append(
//                                        Component.text(profile.name)
//                                            .color(NamedTextColor.AQUA)
//                                            .decorate(TextDecoration.UNDERLINED)
//                                    )
//                                    .append(
//                                        Component.text("!")
//                                            .color(NamedTextColor.GREEN)
//                                    )
//                                session.send(ClientboundSystemChatPacket(msg, false))
//                            }
                        }

                        override fun packetSending(event: PacketSendingEvent) {
//                            if (event.)
                        }
                    })
                }

                override fun sessionRemoved(event: SessionRemovedEvent) {
                    val protocol = event.session.packetProtocol as MinecraftProtocol
                    if (protocol.state == ProtocolState.GAME) {
                        println("Protocol game!")
//                        event.server.close(false)
                    }
                }
            })
            server.bind()
        }
//        status()
//        login()
    }

    private fun status() {
        val sessionService = SessionService()
//        sessionService.proxy = AUTH_PROXY
        val protocol = MinecraftProtocol()
        val client: Session = TcpClientSession(HOST, PORT, protocol, PROXY)
        client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService)
        client.setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY,
            ServerInfoHandler { session: Session?, info: ServerStatusInfo ->
                println(
                    "Version: " + info.versionInfo.versionName
                            + ", " + info.versionInfo.protocolVersion
                )
                println(
                    "Player Count: " + info.playerInfo.onlinePlayers
                            + " / " + info.playerInfo.maxPlayers
                )
//                System.out.println("Players: " + Arrays.toString(info.playerInfo.players))
                println("Description: " + info.description)
                println("Icon: " + info.iconPng)
            })
        client.setFlag(MinecraftConstants.SERVER_PING_TIME_HANDLER_KEY,
            ServerPingTimeHandler { session: Session?, pingTime: Long ->
                println(
                    "Server ping took " + pingTime + "ms"
                )
            } as ServerPingTimeHandler)
        client.connect()
        while (client.isConnected) {
            try {
                Thread.sleep(5)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

//    private fun login() {
//        val protocol: MinecraftProtocol
//        if (VERIFY_USERS) {
////            try {
////                val authService: AuthenticationService = MojangAuthenticationService()
////                authService.username = USERNAME
////                authService.password = PASSWORD
////                authService.proxy = AUTH_PROXY
////                authService.login()
////                protocol = MinecraftProtocol(authService.selectedProfile, authService.accessToken)
////                println("Successfully authenticated user.")
////            } catch (e: RequestException) {
////                e.printStackTrace()
////                return
////            }
//        } else {
//            protocol = MinecraftProtocol(USERNAME)
//        }
//        val sessionService = SessionService()
//        sessionService.proxy = AUTH_PROXY
//        val client: Session = TcpClientSession(HOST, PORT, protocol, PROXY)
//        client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService)
//        client.addListener(object : SessionAdapter() {
//            override fun packetReceived(session: Session, packet: Packet) {
//                if (packet is ClientboundLoginPacket) {
//                    session.send(
//                        ServerboundChatPacket(
//                            "Hello, this is a test of MCProtocolLib.",
//                            Instant.now().toEpochMilli(),
//                            0,
//                            ByteArray(0),
//                            false,
//                            ArrayList<E>(),
//                            null
//                        )
//                    )
//                } else if (packet is ClientboundSystemChatPacket) {
//                    val message: Component = (packet as ClientboundSystemChatPacket).content
//                    println("Received Message: $message")
//                    session.disconnect("Finished")
//                }
//            }
//
//            override fun disconnected(event: DisconnectedEvent) {
//                println("Disconnected: " + event.reason)
//                if (event.cause != null) {
//                    event.cause.printStackTrace()
//                }
//            }
//        })
//        client.connect()
//    }

    private fun loadNetworkCodec(): CompoundTag {
        try {
            MinecraftProtocolTest::class.java.classLoader.getResourceAsStream("network_codec.nbt").use { inputStream ->
                DataInputStream(GZIPInputStream(inputStream)).use { stream ->
                    return NBTIO.readTag(
                        stream as DataInput
                    ) as CompoundTag
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            throw AssertionError("Unable to load network codec.")
        }
    }
}
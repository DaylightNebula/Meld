package net.minestom.server.network.packet.server;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public sealed interface SendablePacket
        permits CachedPacket, FramedPacket, LazyPacket, JavaServerPacket {

    @ApiStatus.Experimental
    static @NotNull JavaServerPacket extractServerPacket(@NotNull SendablePacket packet) {
        if (packet instanceof JavaServerPacket javaServerPacket) {
            return javaServerPacket;
        } else if (packet instanceof CachedPacket cachedPacket) {
            return cachedPacket.packet();
        } else if (packet instanceof FramedPacket framedPacket) {
            return framedPacket.packet();
        } else if (packet instanceof LazyPacket lazyPacket) {
            return lazyPacket.packet();
        } else {
            throw new RuntimeException("Unknown packet type: " + packet.getClass().getName());
        }
    }
}

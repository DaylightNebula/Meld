package net.minestom.server.network;

import io.github.daylightnebula.Meld;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.minestom.server.network.packet.server.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Utils class for packets. Including writing a {@link } into a {@link ByteBuffer}
 * for network processing.
 * <p>
 * Note that all methods are mostly internal and can change at any moment.
 * This is due to their very unsafe nature (use of local buffers as cache) and their potential performance impact.
 * Be sure to check the implementation code.
 */
public final class PacketUtils {
    private static final ThreadLocal<Deflater> LOCAL_DEFLATER = ThreadLocal.withInitial(Deflater::new);

    public static final boolean GROUPED_PACKET = getBoolean("minestom.grouped-packet", true);
    public static final boolean CACHED_PACKET = getBoolean("minestom.cached-packet", true);
    public static final boolean VIEWABLE_PACKET = getBoolean("minestom.viewable-packet", true);

    // Viewable packets
//    private static final Cache<Viewable, ViewableStorage> VIEWABLE_STORAGE_MAP = Caffeine.newBuilder().weakKeys().build();

    public static boolean getBoolean(String name, boolean defaultValue) {
        boolean result = defaultValue;
        try {
            final String value = System.getProperty(name);
            if (value != null) result = Boolean.parseBoolean(value);
        } catch (IllegalArgumentException | NullPointerException ignored) {
        }
        return result;
    }

    @Contract("_, null -> null; _, !null -> !null")
    public static String getString(@NotNull String name, @Nullable String defaultValue) {
        return System.getProperty(name, defaultValue);
    }

    private PacketUtils() {
    }

//    static boolean shouldUseCachePacket(final @NotNull JavaJavaServerPacket packet) {
////        if (!MinestomAdventure.AUTOMATIC_COMPONENT_TRANSLATION) return GROUPED_PACKET;
//        if (!(packet instanceof ComponentHoldingJavaJavaServerPacket holder)) return GROUPED_PACKET;
//        return !containsTranslatableComponents(holder);
//    }

//    private static boolean containsTranslatableComponents(final @NotNull ComponentHolder<?> holder) {
//        for (final Component component : holder.components()) {
//            if (isTranslatable(component)) return true;
//        }
//
//        return false;
//    }

    private static boolean isTranslatable(final @NotNull Component component) {
        if (component instanceof TranslatableComponent) return true;

        final var children = component.children();
        if (children.isEmpty()) return false;

        for (final Component child : children) {
            if (isTranslatable(child)) return true;
        }

        return false;
    }

//    /**
//     * Same as {@link #sendGroupedPacket(Collection, JavaServerPacket, Predicate)}
//     * but with the player validator sets to null.
//     *
//     * @see #sendGroupedPacket(Collection, JavaServerPacket, Predicate)
//     */
//    public static void sendGroupedPacket(@NotNull Collection<Player> players, @NotNull JavaServerPacket packet) {
//        sendGroupedPacket(players, packet, player -> true);
//    }
//
//    public static void broadcastPacket(@NotNull JavaServerPacket packet) {
//        sendGroupedPacket(MinecraftServer.getConnectionManager().getOnlinePlayers(), packet);
//    }
//
//    @ApiStatus.Experimental
//    public static void prepareViewablePacket(@NotNull Viewable viewable, @NotNull JavaServerPacket JavaServerPacket,
//                                             @Nullable Entity entity) {
//        if (entity != null && !entity.hasPredictableViewers()) {
//            // Operation cannot be optimized
//            entity.sendPacketToViewers(JavaServerPacket);
//            return;
//        }
//        if (!VIEWABLE_PACKET) {
//            sendGroupedPacket(viewable.getViewers(), JavaServerPacket, value -> !Objects.equals(value, entity));
//            return;
//        }
//        final Player exception = entity instanceof Player ? (Player) entity : null;
//        ViewableStorage storage = VIEWABLE_STORAGE_MAP.get(viewable, (unused) -> new ViewableStorage());
//        storage.append(viewable, JavaServerPacket, exception);
//    }

//    @ApiStatus.Experimental
//    public static void prepareViewablePacket(@NotNull Viewable viewable, @NotNull JavaServerPacket JavaServerPacket) {
//        prepareViewablePacket(viewable, JavaServerPacket, null);
//    }

//    @ApiStatus.Internal
//    public static void flush() {
//        if (VIEWABLE_PACKET) {
//            VIEWABLE_STORAGE_MAP.asMap().entrySet().parallelStream().forEach(entry ->
//                    entry.getValue().process(entry.getKey()));
//        }
//    }

    @ApiStatus.Internal
    public static @Nullable BinaryBuffer readPackets(@NotNull BinaryBuffer readBuffer, boolean compressed,
                                                     BiConsumer<Integer, ByteBuffer> payloadConsumer) throws DataFormatException {
        BinaryBuffer remaining = null;
        ByteBuffer pool = ObjectPool.PACKET_POOL.get();
        while (readBuffer.readableBytes() > 0) {
            final var beginMark = readBuffer.mark();
            try {
                // Ensure that the buffer contains the full packet (or wait for next socket read)
                final int packetLength = readBuffer.readVarInt();
                final int readerStart = readBuffer.readerOffset();
                if (!readBuffer.canRead(packetLength)) {
                    // Integrity fail
                    throw new BufferUnderflowException();
                }
                // Read packet https://wiki.vg/Protocol#Packet_format
                BinaryBuffer content = readBuffer;
                int decompressedSize = packetLength;
                if (compressed) {
                    final int dataLength = readBuffer.readVarInt();
                    final int payloadLength = packetLength - (readBuffer.readerOffset() - readerStart);
                    if (payloadLength < 0) {
                        throw new DataFormatException("Negative payload length " + payloadLength);
                    }
                    if (dataLength == 0) {
                        // Data is too small to be compressed, payload is following
                        decompressedSize = payloadLength;
                    } else {
                        // Decompress to content buffer
                        content = BinaryBuffer.wrap(pool);
                        decompressedSize = dataLength;
                        Inflater inflater = new Inflater(); // TODO: Pool?
                        inflater.setInput(readBuffer.asByteBuffer(readBuffer.readerOffset(), payloadLength));
                        inflater.inflate(content.asByteBuffer(0, dataLength));
                        inflater.reset();
                    }
                }
                // Slice packet
                ByteBuffer payload = content.asByteBuffer(content.readerOffset(), decompressedSize);
                final int packetId = Utils.readVarInt(payload);
                try {
                    payloadConsumer.accept(packetId, payload);
                } catch (Exception e) {
                    // Empty
                }
                // Position buffer to read the next packet
                readBuffer.readerOffset(readerStart + packetLength);
            } catch (BufferUnderflowException e) {
                readBuffer.reset(beginMark);
                remaining = BinaryBuffer.copy(readBuffer);
                break;
            }
        }
        ObjectPool.PACKET_POOL.add(pool);
        return remaining;
    }

    public static void writeFramedPacket(@NotNull ByteBuffer buffer,
                                         @NotNull JavaServerPacket packet,
                                         boolean compression) {
        writeFramedPacket(buffer, packet.getId(), packet,
                compression ? Meld.INSTANCE.getCompressionThreshold() : 0);
    }

    public static void writeFramedPacket(@NotNull ByteBuffer buffer,
                                         int id,
                                         @NotNull NetworkBuffer.Writer writer,
                                         int compressionThreshold) {
        NetworkBuffer networkBuffer = new NetworkBuffer(buffer, false);
        if (compressionThreshold <= 0) {
            // Uncompressed format https://wiki.vg/Protocol#Without_compression
            final int lengthIndex = networkBuffer.skipWrite(3);
            networkBuffer.write(NetworkBuffer.VAR_INT, id);
            networkBuffer.write(writer);
            final int finalSize = networkBuffer.writeIndex() - (lengthIndex + 3);
            Utils.writeVarIntHeader(buffer, lengthIndex, finalSize);
            buffer.position(networkBuffer.writeIndex());
            return;
        }
        // Compressed format https://wiki.vg/Protocol#With_compression
        final int compressedIndex = networkBuffer.skipWrite(3);
        final int uncompressedIndex = networkBuffer.skipWrite(3);

        final int contentStart = networkBuffer.writeIndex();
        networkBuffer.write(NetworkBuffer.VAR_INT, id);
        networkBuffer.write(writer);
        final int packetSize = networkBuffer.writeIndex() - contentStart;
        final boolean compressed = packetSize >= compressionThreshold;
        if (compressed) {
            // Packet large enough, compress it
            try (var hold = ObjectPool.PACKET_POOL.hold()) {
                final ByteBuffer input = hold.get().put(0, buffer, contentStart, packetSize);
                Deflater deflater = LOCAL_DEFLATER.get();
                deflater.setInput(input.limit(packetSize));
                deflater.finish();
                deflater.deflate(buffer.position(contentStart));
                deflater.reset();

                networkBuffer.skipWrite(buffer.position() - contentStart);
            }
        }
        // Packet header (Packet + Data Length)
        Utils.writeVarIntHeader(buffer, compressedIndex, networkBuffer.writeIndex() - uncompressedIndex);
        Utils.writeVarIntHeader(buffer, uncompressedIndex, compressed ? packetSize : 0);

        buffer.position(networkBuffer.writeIndex());
    }

    @ApiStatus.Internal
    public static ByteBuffer createFramedPacket(@NotNull ByteBuffer buffer, @NotNull JavaServerPacket packet, boolean compression) {
        writeFramedPacket(buffer, packet, compression);
        return buffer.flip();
    }

    @ApiStatus.Internal
    public static ByteBuffer createFramedPacket(@NotNull ByteBuffer buffer, @NotNull JavaServerPacket packet) {
        return createFramedPacket(buffer, packet, Meld.INSTANCE.getCompressionThreshold() > 0);
    }

    @ApiStatus.Internal
    public static FramedPacket allocateTrimmedPacket(@NotNull JavaServerPacket packet) {
        try (var hold = ObjectPool.PACKET_POOL.hold()) {
            final ByteBuffer temp = PacketUtils.createFramedPacket(hold.get(), packet);
            final int size = temp.remaining();
            final ByteBuffer buffer = ByteBuffer.allocateDirect(size).put(0, temp, 0, size);
            return new FramedPacket(packet, buffer);
        }
    }

//    private static final class ViewableStorage {
//        // Player id -> list of offsets to ignore (32:32 bits)
//        private final Int2ObjectMap<LongArrayList> entityIdMap = new Int2ObjectOpenHashMap<>();
//        private final BinaryBuffer buffer = ObjectPool.BUFFER_POOL.getAndRegister(this);
//
//        private synchronized void append(Viewable viewable, JavaServerPacket JavaServerPacket, Player player) {
//            try (var hold = ObjectPool.PACKET_POOL.hold()) {
//                final ByteBuffer framedPacket = createFramedPacket(hold.get(), JavaServerPacket);
//                final int packetSize = framedPacket.limit();
//                if (packetSize >= buffer.capacity()) {
//                    process(viewable);
//                    for (Player viewer : viewable.getViewers()) {
//                        if (!Objects.equals(player, viewer)) {
//                            writeTo(viewer.getPlayerConnection(), framedPacket, 0, packetSize);
//                        }
//                    }
//                    return;
//                }
//                if (!buffer.canWrite(packetSize)) process(viewable);
//                final int start = buffer.writerOffset();
//                this.buffer.write(framedPacket);
//                final int end = buffer.writerOffset();
//                if (player != null) {
//                    final long offsets = (long) start << 32 | end & 0xFFFFFFFFL;
//                    LongList list = entityIdMap.computeIfAbsent(player.getEntityId(), id -> new LongArrayList());
//                    list.add(offsets);
//                }
//            }
//        }
//
//        private synchronized void process(Viewable viewable) {
//            if (buffer.writerOffset() == 0) return;
//            ByteBuffer copy = ByteBuffer.allocateDirect(buffer.writerOffset());
//            copy.put(buffer.asByteBuffer(0, copy.capacity()));
//            viewable.getViewers().forEach(player -> processPlayer(player, copy));
//            this.buffer.clear();
//            this.entityIdMap.clear();
//        }
//
//        private void processPlayer(Player player, ByteBuffer buffer) {
//            final int size = buffer.limit();
//            final PlayerConnection connection = player.getPlayerConnection();
//            final LongArrayList pairs = entityIdMap.get(player.getEntityId());
//            if (pairs != null) {
//                // Ensure that we skip the specified parts of the buffer
//                int lastWrite = 0;
//                final long[] elements = pairs.elements();
//                for (int i = 0; i < pairs.size(); ++i) {
//                    final long offsets = elements[i];
//                    final int start = (int) (offsets >> 32);
//                    if (start != lastWrite) writeTo(connection, buffer, lastWrite, start - lastWrite);
//                    lastWrite = (int) offsets; // End = last 32 bits
//                }
//                if (size != lastWrite) writeTo(connection, buffer, lastWrite, size - lastWrite);
//            } else {
//                // Write all
//                writeTo(connection, buffer, 0, size);
//            }
//        }
//
//        private static void writeTo(PlayerConnection connection, ByteBuffer buffer, int offset, int length) {
//            if (connection instanceof PlayerSocketConnection socketConnection) {
//                socketConnection.write(buffer, offset, length);
//                return;
//            }
//            // TODO for non-socket connection
//        }
//    }
}

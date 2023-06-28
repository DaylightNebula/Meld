package net.minestom.server.network.packet.server.login;

import net.kyori.adventure.text.Component;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.ComponentHoldingJavaServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;

import static net.minestom.server.network.NetworkBuffer.COMPONENT;

public record LoginDisconnectPacketJava(@NotNull Component kickMessage) implements ComponentHoldingJavaServerPacket {
    public LoginDisconnectPacketJava(@NotNull NetworkBuffer reader) {
        this(reader.read(COMPONENT));
    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
        writer.write(COMPONENT, kickMessage);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_DISCONNECT;
    }

//    @Override
//    public @NotNull Collection<Component> components() {
//        return List.of(this.kickMessage);
//    }
//
//    @Override
//    public @NotNull LoginDisconnectPacketJava copyWithOperator(@NotNull UnaryOperator<Component> operator) {
//        return new LoginDisconnectPacketJava(operator.apply(this.kickMessage));
//    }
}

package com.kltyton.stardewfishingFabric.common.networking;

import com.kltyton.stardewfishingFabric.StardewfishingFabric;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record C2SCompleteMinigamePacket(boolean success, double accuracy) implements CustomPacketPayload {
    public static final Type<C2SCompleteMinigamePacket> TYPE = new Type<>(ResourceLocation.parse(StardewfishingFabric.MODID + ":complete_minigame"));
    
    public static final StreamCodec<FriendlyByteBuf, C2SCompleteMinigamePacket> CODEC = StreamCodec.of(
            C2SCompleteMinigamePacket::encode, C2SCompleteMinigamePacket::decode
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void encode(FriendlyByteBuf buf, C2SCompleteMinigamePacket packet) {
        buf.writeBoolean(packet.success);
        if (packet.success) {
            buf.writeDouble(packet.accuracy);
        }
    }

    public static C2SCompleteMinigamePacket decode(FriendlyByteBuf buf) {
        boolean success = buf.readBoolean();
        double accuracy = success ? buf.readDouble() : -1;
        return new C2SCompleteMinigamePacket(success, accuracy);
    }
}

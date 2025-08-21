package com.kltyton.stardewfishingFabric.common.networking;

import com.kltyton.stardewfishingFabric.StardewfishingFabric;
import com.kltyton.stardewfishingFabric.common.FishBehavior;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record S2CStartMinigamePacket(FishBehavior behavior) implements CustomPacketPayload {
    public static final Type<S2CStartMinigamePacket> TYPE = new Type<>(ResourceLocation.parse(StardewfishingFabric.MODID + ":start_minigame"));
    
    public static final StreamCodec<FriendlyByteBuf, S2CStartMinigamePacket> CODEC = StreamCodec.composite(
            FishBehavior.STREAM_CODEC, S2CStartMinigamePacket::behavior,
            S2CStartMinigamePacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

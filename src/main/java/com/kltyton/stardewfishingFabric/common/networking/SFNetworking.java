package com.kltyton.stardewfishingFabric.common.networking;

import com.kltyton.stardewfishingFabric.StardewfishingFabric;
import com.kltyton.stardewfishingFabric.client.ClientEvents;
import com.kltyton.stardewfishingFabric.common.FishBehavior;
import com.kltyton.stardewfishingFabric.common.FishingDataStorage;
import com.kltyton.stardewfishingFabric.common.FishingHookLogic;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

public class SFNetworking {

    // 注册 payload types (只需要注册一次)
    public static void registerPayloadTypes() {
        PayloadTypeRegistry.playS2C().register(S2CStartMinigamePacket.TYPE, S2CStartMinigamePacket.CODEC);
        PayloadTypeRegistry.playC2S().register(C2SCompleteMinigamePacket.TYPE, C2SCompleteMinigamePacket.CODEC);
    }

    // 注册客户端网络处理器
    public static void registerClientHandlers() {
        ClientPlayNetworking.registerGlobalReceiver(S2CStartMinigamePacket.TYPE, (packet, context) -> {
            context.client().execute(() -> ClientEvents.openFishingScreen(packet.behavior()));
        });
    }

    // 注册服务器端网络处理器
    public static void registerServerHandlers() {
        ServerPlayNetworking.registerGlobalReceiver(C2SCompleteMinigamePacket.TYPE, (packet, context) -> {
            ServerPlayer player = context.player();
            FishingHook hook = FishingDataStorage.getHookForPlayer(player);
            ItemStack items = FishingDataStorage.getItemsForPlayer(player);

            context.server().execute(() -> FishingHookLogic.endMinigame(player, packet.success(), packet.accuracy(), hook, items));
        });
    }

    // 向玩家发送开始迷你游戏的数据包
    public static void sendToPlayer(ServerPlayer player, S2CStartMinigamePacket packet) {
        ServerPlayNetworking.send(player, packet);
    }

    // 向服务器发送完成迷你游戏的数据包
    public static void sendToServer(C2SCompleteMinigamePacket packet) {
        ClientPlayNetworking.send(packet);
    }
}

package com.kltyton.stardewfishingFabric.common.networking;

import com.kltyton.stardewfishingFabric.StardewfishingFabric;
import com.kltyton.stardewfishingFabric.client.ClientEvents;
import com.kltyton.stardewfishingFabric.common.FishBehavior;
import com.kltyton.stardewfishingFabric.common.FishingDataStorage;
import com.kltyton.stardewfishingFabric.common.FishingHookLogic;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

public class SFNetworking {
    // 定义开始迷你游戏的数据包ID
    private static final ResourceLocation START_MINIGAME_PACKET_ID = new ResourceLocation(StardewfishingFabric.MODID, "start_minigame");
    // 定义完成迷你游戏的数据包ID
    private static final ResourceLocation COMPLETE_MINIGAME_PACKET_ID = new ResourceLocation(StardewfishingFabric.MODID, "complete_minigame");

    // 注册网络事件
    public static void register() {
        // 注册客户端接收的全局数据包处理，用于开始迷你游戏
        ClientPlayNetworking.registerGlobalReceiver(START_MINIGAME_PACKET_ID, (client, handler, buf, responseSender) -> {
            FishBehavior behavior = new FishBehavior(buf);
            client.execute(() -> ClientEvents.openFishingScreen(behavior));
        });

        // 注册服务器端接收的全局数据包处理，用于完成迷你游戏
        ServerPlayNetworking.registerGlobalReceiver(COMPLETE_MINIGAME_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            boolean success = buf.readBoolean();
            double accuracy = success ? buf.readDouble() : -1;
            FishingHook hook = FishingDataStorage.getHookForPlayer(player);
            ItemStack items = FishingDataStorage.getItemsForPlayer(player);

            server.execute(() -> FishingHookLogic.endMinigame(player, success, accuracy, hook, items));
        });
    }

    // 向玩家发送开始迷你游戏的数据包
    public static void sendToPlayer(ServerPlayer player, FriendlyByteBuf packet) {
        ServerPlayNetworking.send(player, START_MINIGAME_PACKET_ID, packet);
    }

    // 向服务器发送完成迷你游戏的数据包
    public static void sendToServer(FriendlyByteBuf packet) {
        ClientPlayNetworking.send(COMPLETE_MINIGAME_PACKET_ID, packet);
    }
}

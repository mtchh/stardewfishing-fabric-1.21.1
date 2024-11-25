package com.kltyton.stardewfishingFabric.common.networking;

import com.kltyton.stardewfishingFabric.StardewfishingFabric;
import com.kltyton.stardewfishingFabric.common.FishingDataStorage;
import com.kltyton.stardewfishingFabric.common.FishingHookLogic;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

// 定义从客户端到服务器（C2S）的完成迷你游戏的数据包
public class C2SCompleteMinigamePacket {
    private final boolean success;
    private final double accuracy;

    public C2SCompleteMinigamePacket(boolean success, double accuracy) {
        this.success = success;
        this.accuracy = accuracy;
    }

    // 将数据包内容编码到FriendlyByteBuf中，以便通过网络发送
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(success);
        if (success) buf.writeDouble(accuracy);
    }

    // 从FriendlyByteBuf中解码数据包
    public static C2SCompleteMinigamePacket decode(FriendlyByteBuf buf) {
        boolean success = buf.readBoolean();
        double accuracy = success ? buf.readDouble() : -1;
        return new C2SCompleteMinigamePacket(success, accuracy);
    }

    // 处理接收到的数据包
    public static void handle(C2SCompleteMinigamePacket packet, ServerPlayer player, FishingHook hook, ItemStack items) {
        if (player == null) return;
        FishingHookLogic.endMinigame(player, packet.success, packet.accuracy, hook, items);
    }


    // 注册数据包
    public static void register() {
        ResourceLocation id = new ResourceLocation(StardewfishingFabric.MODID, "complete_minigame");
        ServerPlayNetworking.registerGlobalReceiver(id, (server, player, handler, buf, responseSender) -> {
            C2SCompleteMinigamePacket packet = C2SCompleteMinigamePacket.decode(buf);

            // 获取 FishingHook 和 ItemStack 对象
            FishingHook hook = FishingDataStorage.getHookForPlayer(player);
            ItemStack items = FishingDataStorage.getItemsForPlayer(player);

            server.execute(() -> handle(packet, player, hook, items));
        });
    }

}

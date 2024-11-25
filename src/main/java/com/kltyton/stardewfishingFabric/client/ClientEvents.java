package com.kltyton.stardewfishingFabric.client;

import com.kltyton.stardewfishingFabric.common.FishBehavior;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;

public class ClientEvents implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // 注册客户端每帧结束时的事件
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.level != null) {
                SoundManager soundManager = client.getSoundManager();
                // 自定义声音管理逻辑
                soundManager.tick(false);
            }
        });
    }

    // 打开钓鱼屏幕的静态方法
    public static void openFishingScreen(FishBehavior behavior) {
        Minecraft.getInstance().setScreen(new FishingScreen(behavior));
    }
}

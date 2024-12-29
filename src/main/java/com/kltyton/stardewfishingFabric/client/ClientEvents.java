package com.kltyton.stardewfishingFabric.client;

import com.kltyton.stardewfishingFabric.common.FishBehavior;
import com.kltyton.stardewfishingFabric.common.networking.SFNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;

public class ClientEvents implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SFNetworking.register();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.level != null) {
                SoundManager soundManager = client.getSoundManager();
                soundManager.tick(false);
            }
        });
    }

    // 打开钓鱼屏幕的静态方法
    public static void openFishingScreen(FishBehavior behavior) {
        Minecraft.getInstance().setScreen(new FishingScreen(behavior));
    }
}

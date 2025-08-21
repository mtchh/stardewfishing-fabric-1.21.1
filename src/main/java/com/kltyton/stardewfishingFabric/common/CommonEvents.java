package com.kltyton.stardewfishingFabric.common;

import com.kltyton.stardewfishingFabric.server.FishBehaviorReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class CommonEvents {

    // 初始化公共事件
    public static void initialize() {
        // 注册资源重载监听器
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(FishBehaviorReloadListener.create());
    }
}

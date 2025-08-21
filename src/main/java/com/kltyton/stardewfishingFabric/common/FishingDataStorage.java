package com.kltyton.stardewfishingFabric.common;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class FishingDataStorage {
    private static final Map<ServerPlayer, FishingHook> playerHookMap = new HashMap<>();
    private static final Map<ServerPlayer, ItemStack> playerItemMap = new HashMap<>();

    public static void storeData(ServerPlayer player, FishingHook hook, ItemStack items) {
        playerHookMap.put(player, hook);
        playerItemMap.put(player, items);
    }

    public static FishingHook getHookForPlayer(ServerPlayer player) {
        return playerHookMap.get(player);
    }

    public static ItemStack getItemsForPlayer(ServerPlayer player) {
        return playerItemMap.get(player);
    }

    public static void clearDataForPlayer(ServerPlayer player) {
        playerHookMap.remove(player);
        playerItemMap.remove(player);
    }
}

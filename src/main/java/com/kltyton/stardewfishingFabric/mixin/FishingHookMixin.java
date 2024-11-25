package com.kltyton.stardewfishingFabric.mixin;

import com.kltyton.stardewfishingFabric.StardewfishingFabric;
import com.kltyton.stardewfishingFabric.common.FishingDataStorage;
import com.kltyton.stardewfishingFabric.common.FishingHookLogic;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {
    @Inject(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void retrieve(ItemStack stack, CallbackInfoReturnable<Integer> cir, Player player, int i, LootParams lootParams, LootTable lootTable, List list, Iterator var7, ItemStack items) {
        FishingHook hook = (FishingHook) (Object) this;
        ServerPlayer serverPlayer = (ServerPlayer) hook.getPlayerOwner();
        if (serverPlayer == null) return;
        if (items.is(StardewfishingFabric.STARTS_MINIGAME)) {
            boolean success = false;
            double accuracy = 0.0;
            FishingDataStorage.storeData(serverPlayer, hook, items);
            FishingHookLogic.startMinigame(serverPlayer, items);
            boolean minigameSuccess = FishingHookLogic.endMinigame(serverPlayer, success, accuracy, hook, items);
            if (!minigameSuccess) {
                cir.cancel();
            }
        }
    }

    @Redirect(method = "catchingFish", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/FishingHook;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
    private void redirectPlaySound(FishingHook instance, SoundEvent soundEvent, float v, float x) {
        instance.playSound(StardewfishingFabric.FISH_BITE,1.0F, 1.0F);
    }
}

package com.kltyton.stardewfishingFabric.mixin;

import com.kltyton.stardewfishingFabric.StardewfishingFabric;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingRodItem.class)
public class FishingRodItemMixin {

    @Redirect(method = "use",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V",
                    ordinal = 0))
    private void redirectPlaySoundFirst(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch) {
        Player nearestPlayer = Minecraft.getInstance().level.getNearestPlayer(x, y, z, 1, false);
        if (nearestPlayer != null && nearestPlayer.fishing instanceof FishingHook) {
            FishingHook fishingHook = nearestPlayer.fishing;

            boolean isBiting = fishingHook.getEntityData().get(FishingHook.DATA_BITING);

            if (isBiting) {
                instance.playSound(null, x, y, z, StardewfishingFabric.FISH_HIT, SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else {
                instance.playSound(null, x, y, z, StardewfishingFabric.PULL_ITEM, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
    }

    @Redirect(method = "use",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V",
                    ordinal = 1))
    private void redirectPlaySoundSecond(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch) {
        instance.playSound(null, x, y, z, StardewfishingFabric.CAST, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }
}


package com.teampotato.dimensionalsycnfixes;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod(DimensionalSycnFixes.MOD_ID)
public class DimensionalSycnFixes {
    public static final String MOD_ID = "dimensionalsycnfixes";

    public DimensionalSycnFixes() {
        MinecraftForge.EVENT_BUS.addListener((PlayerEvent.PlayerChangedDimensionEvent event) -> {
            Player player = event.getEntity();
            player.giveExperiencePoints(0);
            final Map<MobEffect, MobEffectInstance> activeEffects = player.getActiveEffectsMap();
            synchronized (activeEffects) {
                new Object2ObjectOpenHashMap<>(activeEffects).forEach((mobEffect, mobEffectInstance) -> {
                    player.removeEffect(mobEffect);
                    player.addEffect(mobEffectInstance);
                });
            }
        });
    }
}

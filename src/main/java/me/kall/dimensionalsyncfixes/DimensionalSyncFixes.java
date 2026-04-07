package me.kall.dimensionalsyncfixes;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Map;

@Mod(DimensionalSyncFixes.MOD_ID)
public final class DimensionalSyncFixes {
    public static final String MOD_ID = "dimensionalsyncfixes";

    public DimensionalSyncFixes() {
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerChangedDimensionEvent.class, event -> {
            Player player = event.getEntity();
            player.giveExperiencePoints(0);
            final Map<Holder<MobEffect>, MobEffectInstance> activeEffects = player.getActiveEffectsMap();
            synchronized (activeEffects) {
                new Object2ObjectArrayMap<>(activeEffects).forEach((mobEffectHolder, mobEffectInstance) -> {
                    player.removeEffect(mobEffectHolder);
                    player.addEffect(mobEffectInstance);
                });
            }
        });
    }
}

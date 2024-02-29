package com.teampotato.dimensionalsyncfixes;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.Map;

public class DimensionalSyncFixes implements ModInitializer {
	@Override
	public void onInitialize() {
		ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
			player.addExperience(0);
			final Map<StatusEffect, StatusEffectInstance> activeStatusEffects = player.getActiveStatusEffects();
			synchronized (activeStatusEffects) {
				new Object2ObjectOpenHashMap<>(activeStatusEffects).forEach((statusEffect, statusEffectInstance) -> {
					player.removeStatusEffect(statusEffect);
					player.addStatusEffect(statusEffectInstance);
				});
			}
		});
	}
}
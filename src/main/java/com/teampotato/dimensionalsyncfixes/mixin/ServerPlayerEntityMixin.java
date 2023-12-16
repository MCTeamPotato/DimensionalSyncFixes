package com.teampotato.dimensionalsyncfixes.mixin;

import com.mojang.authlib.GameProfile;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Shadow public abstract void addExperience(int experience);

    @Inject(method = "worldChanged", at = @At("RETURN"))
    private void onChangeWorld(ServerWorld origin, CallbackInfo ci) {
        this.addExperience(0);
        final Map<StatusEffect, StatusEffectInstance> activeStatusEffects = this.getActiveStatusEffects();
        synchronized (activeStatusEffects) {
            new Object2ObjectOpenHashMap<>(activeStatusEffects).forEach((statusEffect, statusEffectInstance) -> {
                this.removeStatusEffect(statusEffect);
                this.addStatusEffect(statusEffectInstance);
            });
        }
    }
}

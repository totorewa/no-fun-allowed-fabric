package me.totorewa.mcmods.fabric.api.helpers;

import me.totorewa.mcmods.fabric.api.events.items.consumables.UndyingTotemEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public final class UndyingTotemHelper {
    public static UndyingTotemEvent createDefaultEvent(ItemStack item, DamageSource source, Entity consumer) {
        return new UndyingTotemEvent(item, source, consumer, Arrays.asList(
                new MobEffectInstance(MobEffects.REGENERATION, 900, 1),
                new MobEffectInstance(MobEffects.ABSORPTION, 100, 1),
                new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0)));
    }

    public static UndyingTotemEvent createIgnoreEvent(ItemStack item, DamageSource source, Entity consumer) {
        return new UndyingTotemEvent(item, source, consumer);
    }
}

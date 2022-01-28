package me.totorewa.mcmods.fabric.api.events.items.consumables;

import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.Registry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class UndyingTotemEvent extends AbstractConsumableItemEvent {
    private static final Event<Listener> listeners = EventFactory.createArrayBacked(Listener.class,
            listeners -> event -> {
                for (Listener listener : listeners) {
                    listener.beforeTotemUse(event);
                }
            });

    private static final Map<MobEffect, Integer> mobEffectIds = new Object2IntOpenHashMap<>();
    private final Map<Integer, MobEffectInstance> effects = new Int2ReferenceOpenHashMap<>();
    private final DamageSource damageSource;

    private boolean preventDeath = true;

    public UndyingTotemEvent(
            final ItemStack item,
            final DamageSource damageSource,
            final Iterable<MobEffectInstance> effects) {
        this(item, damageSource);
        for (MobEffectInstance effect : effects) {
            addEffect(effect);
        }
    }

    public UndyingTotemEvent(
            final ItemStack item,
            final DamageSource damageSource) {
        super(item);
        this.damageSource = damageSource;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }

    public boolean hasEffects() {
        return !effects.isEmpty();
    }

    public Iterable<MobEffectInstance> getEffects() {
        return effects.values();
    }

    public void addEffect(MobEffect effect, int ticks, int amplitude) {
        addEffect(new MobEffectInstance(effect, ticks, amplitude));
    }

    public void removeEffect(MobEffect effect) {
        effects.remove(getEffectId(effect));
    }

    public void clearEffects() {
        effects.clear();
    }

    public boolean willLive() {
        return preventDeath;
    }

    public void kill() {
        kill(true);
    }

    public void kill(boolean clearEffects) {
        preventDeath = false;
        if (clearEffects) clearEffects();
    }

    public void live() {
        live(true);
    }

    public void live(boolean clearEffects) {
        preventDeath = true;
        if (clearEffects) clearEffects();
    }

    private void addEffect(MobEffectInstance mobEffect) {
        int id = getEffectId(mobEffect.getEffect());
        effects.put(id, mobEffect);
    }

    private static int getEffectId(final MobEffect effect) {
        if (mobEffectIds.containsKey(effect)) {
            return mobEffectIds.get(effect);
        }
        int id = Registry.MOB_EFFECT.getId(effect);
        mobEffectIds.put(effect, id);
        return id;
    }

    public static void listen(Listener listener) {
        listeners.register(listener);
    }

    public static void beforeTotemUse(UndyingTotemEvent event) {
        listeners.invoker().beforeTotemUse(event);
    }

    public interface Listener {
        void beforeTotemUse(UndyingTotemEvent event);
    }
}

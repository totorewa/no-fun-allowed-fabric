package me.totorewa.mcmods.fabric.api.events.entities.living;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class EntityDeathEvent extends AbstractLivingEntityEvent {
    private static final Event<EntityDeathEvent.Listener> listeners =
            EventFactory.createArrayBacked(
                    EntityDeathEvent.Listener.class,
                    listeners -> event -> {
                        for (EntityDeathEvent.Listener listener : listeners) {
                            listener.onDying(event);
                        }
                    });
    private static boolean hasListeners;

    private final DamageSource source;
    private boolean dropExp = true;
    private boolean dropGear = true;
    private boolean dropLoot = true;

    public EntityDeathEvent(final LivingEntity entity, final DamageSource source) {
        super(entity);
        this.source = source;
    }

    public DamageSource getDamageSource() {
        return source;
    }

    public boolean canDropExperience() {
        return this.dropExp;
    }

    public void preventExperienceDrop() {
        this.dropExp = false;
    }

    public boolean canDropGear() {
        return this.dropGear;
    }

    public void preventGearDrop() {
        this.dropGear = false;
    }

    public boolean canDropLoot() {
        return this.dropLoot;
    }

    public void preventLootDrop() {
        this.dropLoot = false;
    }

    public static boolean hasListeners() {
        return hasListeners;
    }

    public static void listen(EntityDeathEvent.Listener listener) {
        listeners.register(listener);
        hasListeners = true;
    }

    public static void onDying(EntityDeathEvent event) {
        listeners.invoker().onDying(event);
    }

    public interface Listener {
        void onDying(EntityDeathEvent event);
    }
}

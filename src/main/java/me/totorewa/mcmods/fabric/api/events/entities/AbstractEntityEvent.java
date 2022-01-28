package me.totorewa.mcmods.fabric.api.events.entities;

import me.totorewa.mcmods.fabric.api.events.AbstractEvent;
import net.minecraft.world.entity.Entity;

public abstract class AbstractEntityEvent extends AbstractEvent {
    private final Entity entity;

    public AbstractEntityEvent(final Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}

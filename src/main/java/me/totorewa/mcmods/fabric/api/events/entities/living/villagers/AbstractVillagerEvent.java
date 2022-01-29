package me.totorewa.mcmods.fabric.api.events.entities.living.villagers;

import me.totorewa.mcmods.fabric.api.events.entities.living.AbstractLivingEntityEvent;
import net.minecraft.world.entity.npc.Villager;

public class AbstractVillagerEvent extends AbstractLivingEntityEvent {
    public AbstractVillagerEvent(Villager entity) {
        super(entity);
    }

    public Villager getVillager() {
        return (Villager) getEntity();
    }
}

package me.totorewa.mcmods.fabric.api.events.items;

import me.totorewa.mcmods.fabric.api.events.AbstractEvent;
import net.minecraft.world.item.ItemStack;

public class AbstractItemEvent extends AbstractEvent {
    private final ItemStack item;

    public AbstractItemEvent(final ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}

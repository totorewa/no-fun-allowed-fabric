package me.totorewa.mcmods.fabric.api.events.items;

import net.minecraft.world.item.ItemStack;

public class AbstractItemEvent {
    private final ItemStack item;

    public AbstractItemEvent(final ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}

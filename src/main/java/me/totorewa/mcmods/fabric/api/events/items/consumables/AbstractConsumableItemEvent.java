package me.totorewa.mcmods.fabric.api.events.items.consumables;

import me.totorewa.mcmods.fabric.api.events.items.AbstractItemEvent;
import net.minecraft.world.item.ItemStack;

public class AbstractConsumableItemEvent extends AbstractItemEvent {
    private boolean consumptionPrevented;

    public AbstractConsumableItemEvent(ItemStack item) {
        super(item);
    }

    @Override
    public void cancel() {
        super.cancel();
        consumptionPrevented = true;
    }

    public void preventConsumption() {
        consumptionPrevented = true;
    }

    public boolean isConsumptionPrevented() {
        return consumptionPrevented;
    }
}

package me.totorewa.mcmods.fabric.api.events.entities.living.villagers;

import me.totorewa.mcmods.fabric.api.events.entities.living.AbstractLivingEntityEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.npc.Villager;

public class VillagerRestockEvent extends AbstractVillagerEvent {
    private static final Event<VillagerRestockEvent.Listener> listeners =
            EventFactory.createArrayBacked(
                    VillagerRestockEvent.Listener.class,
                    listeners -> event -> {
                        for (VillagerRestockEvent.Listener listener : listeners) {
                            listener.onRestock(event);
                        }
                    });
    private static boolean hasListeners;

    public VillagerRestockEvent(Villager entity) {
        super(entity);
    }

    public static boolean hasListeners() {
        return hasListeners;
    }

    public static void listen(VillagerRestockEvent.Listener listener) {
        listeners.register(listener);
        hasListeners = true;
    }

    public static void onRestock(VillagerRestockEvent event) {
        listeners.invoker().onRestock(event);
    }

    public interface Listener {
        void onRestock(VillagerRestockEvent event);
    }
}

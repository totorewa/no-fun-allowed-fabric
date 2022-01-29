package me.totorewa.mcmods.fabric.api.events.entities.living.villagers;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.npc.Villager;

public class VillagerReputationEvent extends AbstractVillagerEvent {
    private static final Event<VillagerReputationEvent.Listener> listeners =
            EventFactory.createArrayBacked(
                    VillagerReputationEvent.Listener.class,
                    listeners -> event -> {
                        for (VillagerReputationEvent.Listener listener : listeners) {
                            listener.onReputationGain(event);
                        }
                    });
    private static boolean hasListeners;
    private final ReputationEventType reputationEventType;
    private final Entity reputableEntity;

    public VillagerReputationEvent(final Villager villager,
                                   final ReputationEventType reputationEventType,
                                   final Entity reputableEntity) {
        super(villager);
        this.reputationEventType = reputationEventType;
        this.reputableEntity = reputableEntity;
    }

    public Entity getReputableEntity() {
        return reputableEntity;
    }

    public ReputationEventType getReputationEventType() {
        return reputationEventType;
    }

    public static boolean hasListeners() {
        return hasListeners;
    }

    public static void listen(VillagerReputationEvent.Listener listener) {
        listeners.register(listener);
        hasListeners = true;
    }

    public static void onReputationGain(VillagerReputationEvent event) {
        listeners.invoker().onReputationGain(event);
    }

    public interface Listener {
        void onReputationGain(VillagerReputationEvent event);
    }
}

package me.totorewa.mcmods.fabric.api.events.entities.living.raid;

import me.totorewa.mcmods.fabric.api.events.entities.living.AbstractLivingEntityEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;

public class JoinRaidEvent extends AbstractLivingEntityEvent {
    private static final Event<JoinRaidEvent.Listener> listeners =
            EventFactory.createArrayBacked(
                    JoinRaidEvent.Listener.class,
                    listeners -> event -> {
                        for (JoinRaidEvent.Listener listener : listeners) {
                            listener.onRaidJoin(event);
                        }
                    });
    private static boolean hasListeners;
    private final Raid raid;
    private final Vec3i pos;

    public JoinRaidEvent(final Raid raid, final Raider raider, Vec3i pos) {
        super(raider);
        this.raid = raid;
        this.pos = pos;
    }

    public Raid getRaid() {
        return raid;
    }

    public Raider getRaider() {
        return (Raider) getEntity();
    }

    public Vec3i getPos() {
        return pos;
    }

    public static boolean hasListeners() {
        return hasListeners;
    }

    public static void listen(JoinRaidEvent.Listener listener) {
        listeners.register(listener);
        hasListeners = true;
    }

    public static void onRaidJoin(JoinRaidEvent event) {
        listeners.invoker().onRaidJoin(event);
    }

    public interface Listener {
        void onRaidJoin(JoinRaidEvent event);
    }
}

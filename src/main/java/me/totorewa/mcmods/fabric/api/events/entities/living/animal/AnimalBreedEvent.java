package me.totorewa.mcmods.fabric.api.events.entities.living.animal;

import me.totorewa.mcmods.fabric.api.events.entities.living.AbstractLivingEntityEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;

public class AnimalBreedEvent extends AbstractLivingEntityEvent {
    private static final Event<AnimalBreedEvent.Listener> listeners =
            EventFactory.createArrayBacked(
                    AnimalBreedEvent.Listener.class,
                    listeners -> event -> {
                        for (AnimalBreedEvent.Listener listener : listeners) {
                            listener.onBreed(event);
                        }
                    });
    private static boolean hasListeners;

    private final ServerLevel level;
    private final Animal partner;

    public AnimalBreedEvent(
            final ServerLevel level,
            final Animal animal,
            final Animal partner) {
        super(animal);
        this.level = level;
        this.partner = partner;
    }

    public ServerLevel getLevel() {
        return level;
    }

    public Animal getAnimal() {
        return (Animal)getEntity();
    }

    public Animal getBreedingPartner() {
        return partner;
    }

    public static boolean hasListeners() {
        return hasListeners;
    }

    public static void listen(AnimalBreedEvent.Listener listener) {
        listeners.register(listener);
        hasListeners = true;
    }

    public static void onBreed(AnimalBreedEvent event) {
        listeners.invoker().onBreed(event);
    }

    public interface Listener {
        void onBreed(AnimalBreedEvent event);
    }
}

package me.totorewa.mcmods.nofunallowed.listeners.entities.living.animal;

import me.totorewa.mcmods.fabric.api.events.entities.living.animal.AnimalBreedEvent;
import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

import java.util.List;

public class AnimalBreedEventListener implements AnimalBreedEvent.Listener {
    @Override
    public void onBreed(AnimalBreedEvent event) {
        if (NoFunAllowedConfig.animalBreedingDensityLimit > -1) {
            Level level = event.getLevel();
            Animal animal = event.getAnimal();
            
            List<?> entitiesInArea = level.getEntities(animal.getType(),
                    animal.getBoundingBox().inflate(NoFunAllowedConfig.animalBreedingDensityRadius),
                    Entity::isAlive);

            if (entitiesInArea.size() >= NoFunAllowedConfig.animalBreedingDensityLimit)
                event.cancel();
        }
    }
}

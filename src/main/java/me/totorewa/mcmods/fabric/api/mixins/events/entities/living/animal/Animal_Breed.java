package me.totorewa.mcmods.fabric.api.mixins.events.entities.living.animal;

import me.totorewa.mcmods.fabric.api.events.entities.living.animal.AnimalBreedEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Animal.class)
public class Animal_Breed {
    @Inject(method = "spawnChildFromBreeding", at = @At("HEAD"), cancellable = true)
    private void onSpawnChildFromBreeding(ServerLevel level, Animal partner, CallbackInfo ci) {
        if (AnimalBreedEvent.hasListeners()) {
            AnimalBreedEvent event = new AnimalBreedEvent(level, (Animal)(Object) this, partner);
            AnimalBreedEvent.onBreed(event);
            if (event.isCancelled()) {
                Animal parent = (Animal)(Object) this;
                parent.setAge(6000);
                partner.setAge(6000);
                parent.resetLove();
                partner.resetLove();
                level.broadcastEntityEvent(parent, EntityEvent.VILLAGER_ANGRY);
                ci.cancel();
            }
        }
    }
}

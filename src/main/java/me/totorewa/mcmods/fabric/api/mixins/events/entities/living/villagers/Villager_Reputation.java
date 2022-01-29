package me.totorewa.mcmods.fabric.api.mixins.events.entities.living.villagers;

import me.totorewa.mcmods.fabric.api.events.entities.living.villagers.VillagerReputationEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public class Villager_Reputation {
    @Inject(method = "onReputationEventFrom", at = @At("HEAD"), cancellable = true)
    private void beforeOnReputationEventFrom(ReputationEventType type, Entity entity, CallbackInfo ci) {
        if (VillagerReputationEvent.hasListeners()) {
            VillagerReputationEvent event = new VillagerReputationEvent((Villager)(Object)this, type, entity);
            VillagerReputationEvent.onReputationGain(event);
            if (event.isCancelled()) {
                ci.cancel();
            }
        }
    }
}

package me.totorewa.mcmods.fabric.api.mixins.events.entities.living.villagers;

import me.totorewa.mcmods.fabric.api.events.entities.living.villagers.VillagerRestockEvent;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class Villager_OnRestock {
    @Shadow
    protected abstract void updateDemand();

    @Inject(method = "restock", at = @At("HEAD"), cancellable = true)
    private void beforeRestock(CallbackInfo ci) {
        if (VillagerRestockEvent.hasListeners()) {
            VillagerRestockEvent event = new VillagerRestockEvent((Villager)(Object)this);
            VillagerRestockEvent.onRestock(event);
            if (event.isCancelled()) {
                updateDemand(); // Still update demand because we're dicks
                ci.cancel();
            }
        }
    }
}

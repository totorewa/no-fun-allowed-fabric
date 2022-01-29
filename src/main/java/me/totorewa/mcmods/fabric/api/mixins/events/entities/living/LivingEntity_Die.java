package me.totorewa.mcmods.fabric.api.mixins.events.entities.living;

import me.totorewa.mcmods.fabric.api.events.entities.living.EntityDeathEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntity_Die {
    private EntityDeathEvent toto$deathEvent;

    @Inject(method = "die", at = @At("HEAD"), cancellable = true)
    private void onDie(DamageSource source, CallbackInfo ci) {
        EntityDeathEvent event = new EntityDeathEvent((LivingEntity) (Object)this, source);
        EntityDeathEvent.onDying(event);
        if (event.isCancelled()) {
            ((LivingEntity) (Object)this).setHealth(1.0f);
            ci.cancel();
        } else {
            toto$deathEvent = event;
        }
    }

    @Inject(method = "die", at = @At("RETURN"))
    private void afterDie(DamageSource source, CallbackInfo ci) {
        toto$deathEvent = null;
    }

    @Inject(method = "shouldDropExperience", at = @At("HEAD"), cancellable = true)
    private void alterShouldDropExperience(CallbackInfoReturnable<Boolean> cir) {
        if (toto$deathEvent != null && !toto$deathEvent.canDropExperience())
            cir.setReturnValue(false);
    }

    @Inject(method = "shouldDropLoot", at = @At("HEAD"), cancellable = true)
    private void alterShouldDropLoot(CallbackInfoReturnable<Boolean> cir) {
        if (toto$deathEvent != null && !toto$deathEvent.canDropLoot())
            cir.setReturnValue(false);
    }

    @Inject(method = "dropEquipment", at = @At("HEAD"), cancellable = true)
    private void alterDropEquipment(CallbackInfo ci) {
        if (toto$deathEvent != null && !toto$deathEvent.canDropGear())
            ci.cancel();
    }
}

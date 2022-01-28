package me.totorewa.mcmods.fabric.api.mixins.events.entities.living.raid;

import me.totorewa.mcmods.fabric.api.events.entities.living.raid.JoinRaidEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public class Raid_Join {
    @Inject(method = "joinRaid", at = @At("HEAD"), cancellable = true)
    private void onJoinRaid(int group, Raider raider, BlockPos pos, boolean existsInWorld, CallbackInfo ci) {
        if (JoinRaidEvent.hasListeners()) {
            JoinRaidEvent event = new JoinRaidEvent((Raid)(Object)this, raider, pos);
            JoinRaidEvent.onRaidJoin(event);
            if (event.isCancelled()) {
                ci.cancel();
            }
        }
    }
}

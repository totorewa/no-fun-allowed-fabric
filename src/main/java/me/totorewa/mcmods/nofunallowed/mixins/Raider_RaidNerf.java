package me.totorewa.mcmods.nofunallowed.mixins;

import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import me.totorewa.mcmods.nofunallowed.interfaces.RaiderRaidHistory;
import me.totorewa.mcmods.nofunallowed.permissions.Permissions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.raid.Raider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raider.class)
public class Raider_RaidNerf implements RaiderRaidHistory {
    // Not serialized and saved to file so can be reset with a chunk unload, hence "nerf" rather than outright disable
    private boolean toto$disableBadOmen = false;

    @Override
    public boolean toto$hasBeenInRaid() {
        return toto$disableBadOmen;
    }

    @Override
    public void toto$raidJoinAttempted() {
        toto$disableBadOmen = true;
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void onDie(DamageSource damageSource, CallbackInfo ci) {
        if (toto$disableBadOmen &&
                (!NoFunAllowedConfig.nerfRaidFarms
                || Permissions.BYPASS_RAIDFARMNERF.hasPermission(damageSource.getEntity()))) {
            toto$disableBadOmen = false;
        }
    }

    @Redirect(method = "die", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/raid/Raider;isPatrolLeader()Z",
            ordinal = 1
    ))
    private boolean disableBadOmenForRaid(Raider patroller) {
        return !toto$disableBadOmen && patroller.isPatrolLeader();
    }
}

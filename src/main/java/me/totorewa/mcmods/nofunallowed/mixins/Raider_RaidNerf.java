package me.totorewa.mcmods.nofunallowed.mixins;

import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import me.totorewa.mcmods.nofunallowed.interfaces.RaiderRaidHistory;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.raid.Raider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Raider.class)
public class Raider_RaidNerf implements RaiderRaidHistory {
    // Not serialized and saved to file so can be reset with a chunk unload, hence "nerf" rather than outright disable
    private boolean toto$triedJoiningRaid = false;

    @Override
    public boolean toto$hasBeenInRaid() {
        return toto$triedJoiningRaid;
    }

    @Override
    public void toto$raidJoinAttempted() {
        toto$triedJoiningRaid = true;
    }

    @Redirect(method = "die", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/raid/Raider;isPatrolLeader()Z",
            ordinal = 1
    ))
    private boolean disableBadOmenForRaid(Raider patroller) {
        return (!NoFunAllowedConfig.nerfRaidFarms || !toto$triedJoiningRaid) && patroller.isPatrolLeader();
    }
}

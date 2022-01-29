package me.totorewa.mcmods.nofunallowed.mixins;

import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Mob.class)
public interface Mob_XpRewardAccessor {
    @Accessor
    int getXpReward();

    @Accessor
    void setXpReward(int reward);
}

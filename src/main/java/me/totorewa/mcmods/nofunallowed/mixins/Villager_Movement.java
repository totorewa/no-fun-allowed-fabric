package me.totorewa.mcmods.nofunallowed.mixins;

import me.totorewa.mcmods.nofunallowed.interfaces.VillagerMovement;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class Villager_Movement extends AbstractVillager implements VillagerMovement {
    private final static int CHECK_MOVEMENT_DELAY = 100;
    private boolean toto$hasMoved;
    private Vec3i toto$lastPos;
    private int toto$nextCheckTick;

    public Villager_Movement(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    protected abstract VillagerData getVillagerData();

    @Override
    public boolean toto$hasMovedSinceLastCheck() {
        // Get if the villagers position has changed and then reset
        boolean hasMoved = toto$hasMoved;
        toto$hasMoved = false;
        return hasMoved;
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void afterTick(CallbackInfo ci) {
        // Check every 100 ticks to see if the villager's position has changed
        // If villager has no profession, we don't care about tracking them since this is being used
        // to prevent restocking and unemployed villagers can't restock.
        if (getVillagerData().getProfession() == VillagerProfession.NONE) return;

        int ticks = level.getServer().getTickCount();
        if (ticks >= toto$nextCheckTick) {
            toto$nextCheckTick += CHECK_MOVEMENT_DELAY;
            final Vec3i lastPos = toto$lastPos;
            toto$lastPos = blockPosition();
            if (!toto$lastPos.equals(lastPos)) {
                toto$hasMoved = true;
            }
        }
    }
}

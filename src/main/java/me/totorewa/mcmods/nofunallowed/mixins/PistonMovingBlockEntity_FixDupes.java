package me.totorewa.mcmods.nofunallowed.mixins;

import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonMovingBlockEntity.class)
public class PistonMovingBlockEntity_FixDupes {
    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z",
                    ordinal = 0
            )
    )
    private static boolean updateAirBlock(Level level, BlockPos blockPos, BlockState blockState, int flags) {
        if (NoFunAllowedConfig.preventPistonExploits)
            flags |= 2; // force update. ghost block client-side without.
        return level.setBlock(blockPos, blockState, flags);
    }
}

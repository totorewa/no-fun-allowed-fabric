package me.totorewa.mcmods.nofunallowed.mixins;

import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import me.totorewa.mcmods.nofunallowed.helpers.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Mixin is not used
@Mixin(Level.class)
public class Level_UnbreakableBlock {
    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
            at = @At("HEAD"), cancellable = true)
    private void preventUnbreakableBlockReplacement(
            BlockPos pos, BlockState state,
            int flags, int maxDepth, CallbackInfoReturnable<Boolean> cir) {
        if (NoFunAllowedConfig.preventPistonExploits) {
            Level level = (Level) (Object) this;
            BlockState currentState = level.getBlockState(pos);
            if (BlockHelper.IsUnbreakable(currentState)) {
                cir.setReturnValue(false);
            }
        }
    }
}

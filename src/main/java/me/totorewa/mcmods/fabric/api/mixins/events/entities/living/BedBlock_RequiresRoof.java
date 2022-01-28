package me.totorewa.mcmods.fabric.api.mixins.events.entities.living;

import me.totorewa.mcmods.fabric.api.events.entities.living.EntityBedEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public class BedBlock_RequiresRoof {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void beforeUse(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
                           BlockHitResult blockHit, CallbackInfoReturnable<InteractionResult> cir) {
        if (EntityBedEvent.hasListeners()) {
            EntityBedEvent event = new EntityBedEvent(player, state, pos);
            EntityBedEvent.onUseBed(event);
            if (event.isCancelled()) {
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}

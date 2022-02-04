package me.totorewa.mcmods.nofunallowed.mixins;

import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PistonMovingBlockEntity.class)
public interface PistonMovingBlockEntity_BlockStateAccessor {
    @Accessor
    void setMovedState(BlockState state);
    @Accessor
    BlockState getMovedState();
}

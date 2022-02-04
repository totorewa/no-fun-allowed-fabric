package me.totorewa.mcmods.nofunallowed.mixins;

import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import me.totorewa.mcmods.nofunallowed.helpers.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;

@Mixin(PistonBaseBlock.class)
public class PistonBaseBlock_FixDupes {
    private Direction toto$direction;
    private BlockPos toto$desyncPos;
    private BlockPos toto$realPos;
    private BlockState toto$realMovedState;
    private boolean toto$dupeDetected;

    @Inject(method = "triggerEvent", at = @At("HEAD"))
    private void onTriggerEvent(BlockState state, Level level, BlockPos pos,
                                int type, int data, CallbackInfoReturnable<Boolean> cir) {
        if (NoFunAllowedConfig.preventPistonExploits) {
            Direction eventDirection = Direction.from3DDataValue(data & 7);
            Direction currentDirection = state.getValue(PistonBaseBlock.FACING);
            if (eventDirection != currentDirection) {
                toto$direction = eventDirection;
                toto$desyncPos = pos.relative(currentDirection);
            }
        }
    }

    @Inject(method = "triggerEvent", at = @At("RETURN"))
    private void afterTriggerEvent(BlockState state, Level level, BlockPos pos,
                                   int type, int data, CallbackInfoReturnable<Boolean> cir) {
        if (toto$desyncPos != null) {
            ChunkSource chunkSource = level.getChunkSource();
            if (chunkSource instanceof ServerChunkCache)
                ((ServerChunkCache) chunkSource).blockChanged(toto$desyncPos);
            toto$desyncPos = null;
        }
    }

    @ModifyVariable(method = "triggerEvent", at = @At("STORE"), ordinal = 0)
    private Direction injected(Direction direction) {
        if (toto$direction != null) {
            direction = toto$direction;
            toto$direction = null;
        }
        return direction;
    }

    @Inject(
            method = "moveBlocks",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
                    shift = At.Shift.AFTER,
                    ordinal = 3
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true)
    private void moveBlocks(Level level, BlockPos pos, Direction facingDirection, boolean push,
                            CallbackInfoReturnable<Boolean> cir, BlockPos pos2, PistonStructureResolver resolver,
                            Map<BlockPos, BlockState> map, List<BlockPos> pushList, List<BlockState> pushStates,
                            List<BlockPos> destroyList, BlockState states[], Direction movementDirection,
                            int j, int k, BlockPos movingPos, BlockState movingState) {
        if (NoFunAllowedConfig.preventPistonExploits) {
            BlockState cachedState = pushStates.get(k);
            toto$dupeDetected = !cachedState.is(movingState.getBlock()) && BlockHelper.IsPistonDupable(cachedState);
            if (toto$dupeDetected) {
                toto$realPos = movingPos.relative(movementDirection);
                toto$realMovedState = movingState;
            }
            level.setBlock(movingPos, Blocks.AIR.defaultBlockState(), 18);
        }
    }

    @Redirect(
            method = "moveBlocks",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlockEntity(Lnet/minecraft/world/level/block/entity/BlockEntity;)V",
                    ordinal = 0
            )
    )
    private void substituteRealBlockState(Level level, BlockEntity blockEntity) {
        if (toto$dupeDetected) {
            toto$dupeDetected = false;
            if (blockEntity instanceof PistonMovingBlockEntity
                    && blockEntity.getBlockPos().equals(toto$realPos))
                ((PistonMovingBlockEntity_BlockStateAccessor) blockEntity).setMovedState(toto$realMovedState);
        }

        level.setBlockEntity(blockEntity);
    }
}

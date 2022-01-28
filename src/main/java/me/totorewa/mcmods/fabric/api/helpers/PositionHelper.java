package me.totorewa.mcmods.fabric.api.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

public final class PositionHelper {
    public static boolean IsHighestBlock(Level level, BlockPos pos) {
        return level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ()) == pos.getY();
    }
}

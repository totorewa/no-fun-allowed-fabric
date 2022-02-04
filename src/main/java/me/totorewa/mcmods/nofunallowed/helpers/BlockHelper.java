package me.totorewa.mcmods.nofunallowed.helpers;

import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class BlockHelper {
    public static boolean IsUnbreakable(Block block) {
        return block == Blocks.BEDROCK
                || block == Blocks.END_PORTAL_FRAME
                || block == Blocks.END_PORTAL
                || block == Blocks.END_GATEWAY;
    }

    public static boolean IsUnbreakable(BlockState state) {
        return IsUnbreakable(state.getBlock());
    }

    public static boolean IsPistonDupable(Block block) {
        return block == Blocks.TNT
                || block instanceof WoolCarpetBlock
                || block instanceof BaseRailBlock;
    }

    public static boolean IsPistonDupable(BlockState state) {
        return IsPistonDupable(state.getBlock());
    }
}

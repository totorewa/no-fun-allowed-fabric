package me.totorewa.mcmods.fabric.api.helpers;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class BlockHelper {
    public boolean isUnbreakable(Block block) {
        return block == Blocks.BEDROCK
                || block == Blocks.END_PORTAL_FRAME
                || block == Blocks.END_PORTAL
                || block == Blocks.END_GATEWAY;
    }
}

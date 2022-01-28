package me.totorewa.mcmods.nofunallowed.mixins;

import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItem_NetherRoof {
    @Inject(method = "placeBlock", at = @At("HEAD"), cancellable = true)
    private void beforePlaceBlock(BlockPlaceContext context, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (NoFunAllowedConfig.disableBuildingAboveRoof) {
            Level level = context.getLevel();
            Player player = context.getPlayer();
            if (player.getAbilities().instabuild) return; // Ignore if creative
            if (level.dimension() == Level.NETHER && context.getClickedPos().getY() > 127) {
                Component message = new TranslatableComponent("build.tooHigh", 127)
                        .withStyle(ChatFormatting.RED);
                if (player instanceof ServerPlayer)
                    ((ServerPlayer) player).sendMessage(message, ChatType.GAME_INFO, Util.NIL_UUID);
                cir.setReturnValue(false);
            }
        }
    }
}

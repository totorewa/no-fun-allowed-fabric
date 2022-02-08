package me.totorewa.mcmods.nofunallowed.listeners.entities.living;

import me.totorewa.mcmods.fabric.api.events.entities.living.EntityBedEvent;
import me.totorewa.mcmods.fabric.api.helpers.PositionHelper;
import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import me.totorewa.mcmods.nofunallowed.permissions.Permissions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;

public class EntityBedEventListener implements EntityBedEvent.Listener {
    @Override
    public void onUseBed(EntityBedEvent event) {
        if (NoFunAllowedConfig.requireRoofToSleep
                && (!hasRoof(event, event.getHeadPos())
                || !hasRoof(event, event.getFeetPos()))) {
            if (event.isPlayer() && !Permissions.BYPASS_BEDROOF.hasPermission(event.getEntity())) {
                Player player = event.getPlayer();
                player.displayClientMessage(new TextComponent("You may not rest now; bed has no roof"), true);
            }
            event.cancel();
        } else if (NoFunAllowedConfig.lightLevelRequiredToSleep > 0
                && event.getEntity().getLevel().getBrightness(LightLayer.BLOCK, event.getTargetPos())
                < NoFunAllowedConfig.lightLevelRequiredToSleep) {
            if (event.isPlayer() && !Permissions.BYPASS_BEDLIGHT.hasPermission(event.getEntity())) {
                Player player = event.getPlayer();
                player.displayClientMessage(new TextComponent("You may not rest now; it is too dark"), true);
            }
            event.cancel();
        }
    }

    private boolean hasRoof(EntityBedEvent event, BlockPos pos) {
        return !PositionHelper.IsHighestBlock(event.getEntity().level, pos);
    }
}

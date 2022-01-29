package me.totorewa.mcmods.nofunallowed.listeners.entities.living;

import me.totorewa.mcmods.fabric.api.events.entities.living.EntityBedEvent;
import me.totorewa.mcmods.fabric.api.helpers.PositionHelper;
import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

public class EntityBedEventListener implements EntityBedEvent.Listener {
    @Override
    public void onUseBed(EntityBedEvent event) {
        if (NoFunAllowedConfig.requireRoofToSleep
                && (!hasRoof(event, event.getHeadPos())
                || !hasRoof(event, event.getFeetPos()))) {
            if (event.isPlayer()) {
                Player player = event.getPlayer();
                player.displayClientMessage(new TextComponent("You may not rest now; bed has no roof"), true);
            }
            event.cancel();
        }
    }

    private boolean hasRoof(EntityBedEvent event, BlockPos pos) {
        return !PositionHelper.IsHighestBlock(event.getEntity().level, pos);
    }
}

package me.totorewa.mcmods.nofunallowed.listeners.entities.living;

import me.totorewa.mcmods.fabric.api.events.entities.living.EntityBedEvent;
import me.totorewa.mcmods.fabric.api.helpers.PositionHelper;
import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import net.minecraft.core.BlockPos;

public class EntityBedEventListener implements EntityBedEvent.Listener {
    @Override
    public void onUseBed(EntityBedEvent event) {
        if (NoFunAllowedConfig.requireRoofToSleep
                && (!hasRoof(event, event.getHeadPos())
                || !hasRoof(event, event.getFeetPos())))
            event.cancel();
    }

    private boolean hasRoof(EntityBedEvent event, BlockPos pos) {
        return !PositionHelper.IsHighestBlock(event.getEntity().level, pos);
    }
}

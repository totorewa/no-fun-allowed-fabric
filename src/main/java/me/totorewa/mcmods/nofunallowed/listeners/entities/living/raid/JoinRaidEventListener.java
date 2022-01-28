package me.totorewa.mcmods.nofunallowed.listeners.entities.living.raid;

import me.totorewa.mcmods.fabric.api.events.entities.living.raid.JoinRaidEvent;
import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import me.totorewa.mcmods.nofunallowed.interfaces.RaiderRaidHistory;
import net.minecraft.world.entity.raid.Raider;

public class JoinRaidEventListener implements JoinRaidEvent.Listener {
    @Override
    public void onRaidJoin(JoinRaidEvent event) {
        if (NoFunAllowedConfig.nerfRaidFarms) {
            Raider raider = event.getRaider();
            ((RaiderRaidHistory) raider).toto$raidJoinAttempted();
        }
    }
}

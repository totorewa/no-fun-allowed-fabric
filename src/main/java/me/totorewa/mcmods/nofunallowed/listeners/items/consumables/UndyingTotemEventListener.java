package me.totorewa.mcmods.nofunallowed.listeners.items.consumables;

import me.totorewa.mcmods.fabric.api.events.items.consumables.UndyingTotemEvent;
import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;

public class UndyingTotemEventListener implements UndyingTotemEvent.Listener {
    @Override
    public void beforeTotemUse(UndyingTotemEvent event) {
        if (NoFunAllowedConfig.disableTotemsOfUndying) {
            event.kill();
        }
    }
}

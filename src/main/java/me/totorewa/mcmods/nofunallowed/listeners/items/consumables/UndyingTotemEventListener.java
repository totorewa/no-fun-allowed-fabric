package me.totorewa.mcmods.nofunallowed.listeners.items.consumables;

import me.totorewa.mcmods.fabric.api.events.items.consumables.UndyingTotemEvent;
import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import me.totorewa.mcmods.nofunallowed.permissions.PermissionChecker;
import me.totorewa.mcmods.nofunallowed.permissions.Permissions;

public class UndyingTotemEventListener implements UndyingTotemEvent.Listener {
    @Override
    public void beforeTotemUse(UndyingTotemEvent event) {
        if (NoFunAllowedConfig.disableTotemsOfUndying
                && !Permissions.BYPASS_TOTEM.hasPermission(event.getConsumptionEntity())) {
            event.kill();
        }
    }
}

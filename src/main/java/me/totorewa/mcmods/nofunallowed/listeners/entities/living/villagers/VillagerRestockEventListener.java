package me.totorewa.mcmods.nofunallowed.listeners.entities.living.villagers;

import me.totorewa.mcmods.fabric.api.events.entities.living.villagers.VillagerRestockEvent;
import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import me.totorewa.mcmods.nofunallowed.interfaces.VillagerMovement;
import net.minecraft.world.entity.npc.Villager;

public class VillagerRestockEventListener implements VillagerRestockEvent.Listener {
    @Override
    public void onRestock(VillagerRestockEvent event) {
        if (NoFunAllowedConfig.requireVillagerMovementToRestock) {
            Villager villager = event.getVillager();
            if (villager.isPassenger() || !((VillagerMovement) villager).toto$hasMovedSinceLastCheck())
                event.cancel();
        }
    }
}

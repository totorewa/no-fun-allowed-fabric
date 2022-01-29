package me.totorewa.mcmods.nofunallowed.listeners.entities.living;

import me.totorewa.mcmods.fabric.api.events.entities.living.EntityDeathEvent;
import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

public class EntityDeathEventListener implements EntityDeathEvent.Listener {
    @Override
    public void onDying(EntityDeathEvent event) {
        if (NoFunAllowedConfig.onlyDropXpOnPlayerKill) {
            DamageSource source = event.getDamageSource();
            if (!(source.getDirectEntity() instanceof Player))
                event.preventExperienceDrop();
        }
    }
}

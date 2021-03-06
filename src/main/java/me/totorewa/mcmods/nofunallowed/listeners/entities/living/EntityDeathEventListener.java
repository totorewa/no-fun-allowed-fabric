package me.totorewa.mcmods.nofunallowed.listeners.entities.living;

import me.totorewa.mcmods.fabric.api.events.entities.living.EntityDeathEvent;
import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import me.totorewa.mcmods.nofunallowed.helpers.GlobalExperienceDecay;
import me.totorewa.mcmods.nofunallowed.mixins.Mob_XpRewardAccessor;
import me.totorewa.mcmods.nofunallowed.permissions.Permissions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;

public class EntityDeathEventListener implements EntityDeathEvent.Listener {
    private final GlobalExperienceDecay experienceDecay;

    public EntityDeathEventListener(GlobalExperienceDecay experienceDecay) {
        this.experienceDecay = experienceDecay;
    }

    @Override
    public void onDying(EntityDeathEvent event) {
        LivingEntity entity = event.getLivingEntity();

        if (entity instanceof IronGolem && NoFunAllowedConfig.disableGolemDrops)
            event.preventLootDrop();

        if (!(entity instanceof Monster)) return; // TODO associate listener to specific entity class
        Monster monster = (Monster) entity;

        if (NoFunAllowedConfig.onlyDropXpOnPlayerKill
                || NoFunAllowedConfig.onlyDropLootOnPlayerKill
                || NoFunAllowedConfig.nerfMobFarms) {
            if (!(event.getDamageSource().getDirectEntity() instanceof ServerPlayer)) {
                preventDrops(event);
                return;
            }
        }

        if (NoFunAllowedConfig.nerfMobFarms
                && !Permissions.BYPASS_MOBFARMNERF.hasPermission(event.getDamageSource().getEntity())) {
            ServerPlayer player = (ServerPlayer) event.getDamageSource().getDirectEntity();
            int tick = monster.getLevel().getServer().getTickCount();
            Mob_XpRewardAccessor xpReward = ((Mob_XpRewardAccessor) monster);
            xpReward.setXpReward(
                    (int) (xpReward.getXpReward() * experienceDecay.getExperienceModifier(tick, player.getUUID())));
        }
    }

    private void preventDrops(EntityDeathEvent event) {
        if (NoFunAllowedConfig.nerfMobFarms) {
            event.preventLootDrop();
            event.preventExperienceDrop();
        } else {
            if (NoFunAllowedConfig.onlyDropLootOnPlayerKill)
                event.preventLootDrop();
            if (NoFunAllowedConfig.onlyDropXpOnPlayerKill)
                event.preventExperienceDrop();
        }
    }
}

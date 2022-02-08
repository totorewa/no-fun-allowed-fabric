package me.totorewa.mcmods.nofunallowed.listeners.entities.living.villagers;

import me.totorewa.mcmods.fabric.api.events.entities.living.villagers.VillagerReputationEvent;
import me.totorewa.mcmods.nofunallowed.NoFunAllowedConfig;
import me.totorewa.mcmods.nofunallowed.permissions.Permissions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.npc.Villager;

public class VillagerReputationEventListener implements VillagerReputationEvent.Listener {
    private static final int MAJOR_POSITIVE_CURE_REP = 20 * GossipType.MAJOR_POSITIVE.weight;

    @Override
    public void onReputationGain(VillagerReputationEvent event) {
        if (event.getReputationEventType() == ReputationEventType.ZOMBIE_VILLAGER_CURED
                && NoFunAllowedConfig.zombieCuringLimit >= 0) {
            Entity entity = event.getReputableEntity();
            if (!(entity instanceof ServerPlayer) || Permissions.BYPASS_CURINGLIMIT.hasPermission(entity)) return;
            ServerPlayer player = (ServerPlayer) entity;
            Villager villager = event.getVillager();
            GossipContainer gossips = villager.getGossips();

            int currentRep = gossips.getReputation(player.getUUID(), t -> t == GossipType.MAJOR_POSITIVE);
            int cures = currentRep / MAJOR_POSITIVE_CURE_REP;

            if (cures >= NoFunAllowedConfig.zombieCuringLimit) {
                event.cancel();
                int overflow = cures - NoFunAllowedConfig.zombieCuringLimit;
                if (overflow > 0) {
                    gossips.add(player.getUUID(), GossipType.MAJOR_POSITIVE, overflow * -20);
                    gossips.add(player.getUUID(), GossipType.MINOR_POSITIVE, overflow * -25);
                }
                if (NoFunAllowedConfig.overCuringDamagesReputation
                        && !Permissions.BYPASS_OVERCURING.hasPermission(entity)) {
                    if (cures > 0)
                        gossips.add(player.getUUID(), GossipType.MAJOR_POSITIVE, -20);
                    gossips.add(player.getUUID(), GossipType.MINOR_NEGATIVE, 50);
                }
            }

        }
    }
}

package me.totorewa.mcmods.fabric.api.events.entities.living;

import me.totorewa.mcmods.fabric.api.events.entities.AbstractEntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class AbstractLivingEntityEvent extends AbstractEntityEvent {
    private final boolean isPlayer;
    public AbstractLivingEntityEvent(final LivingEntity entity) {
        super(entity);
        isPlayer = entity instanceof Player;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public LivingEntity getLivingEntity() {
        return (LivingEntity) getEntity();
    }

    @Nullable
    public Player getPlayer() {
        return isPlayer ? (Player)getEntity() : null;
    }
}

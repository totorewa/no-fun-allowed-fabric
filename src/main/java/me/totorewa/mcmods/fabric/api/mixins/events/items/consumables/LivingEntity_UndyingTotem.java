package me.totorewa.mcmods.fabric.api.mixins.events.items.consumables;

import me.totorewa.mcmods.fabric.api.events.items.consumables.UndyingTotemEvent;
import me.totorewa.mcmods.fabric.api.helpers.UndyingTotemHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntity_UndyingTotem extends Entity {

    public LivingEntity_UndyingTotem(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    protected abstract ItemStack getItemInHand(InteractionHand hand);

    @Shadow
    protected abstract void setHealth(float health);

    @Shadow
    protected abstract boolean removeAllEffects();

    @Shadow
    @Final
    protected abstract boolean addEffect(MobEffectInstance mobEffect);

    /**
     * Produces an {@link UndyingTotemEvent} to manipulate the totem protection.
     * @author Totorewa
     * @reason Allow totem protection to be customised through an event API
     * @param source Source of damage that is attempting to kill the entity
     * @return Death is protected by totem
     */
    @Overwrite
    private boolean checkTotemDeathProtection(DamageSource source) {
        ItemStack totem = null;
        InteractionHand[] hands = InteractionHand.values();

        for (InteractionHand hand : hands) {
            ItemStack inHand = getItemInHand(hand);
            if (inHand.is(Items.TOTEM_OF_UNDYING)) {
                totem = inHand;
                break;
            }
        }

        if (totem == null)
            return false;

        ItemStack inHand = totem;

        // If the damage source bypasses the totem effect, create an effect with no effects.
        UndyingTotemEvent event =
                source.isBypassInvul()
                        ? UndyingTotemHelper.createIgnoreEvent(totem, source, this)
                        : UndyingTotemHelper.createDefaultEvent(totem, source, this);

        // Iterate over event listeners and let them modify the event
        UndyingTotemEvent.beforeTotemUse(event);

        // Consumption, protection, and effects are separated for added flexibility
        // NFA mod doesn't actually use any of this but I added it anyway. I'm already here, I might as well.
        if (!event.isConsumptionPrevented()) {
            inHand = totem.copy();
            totem.shrink(1);
        }

        if (event.willLive()) {
            // Award entity if it is a player (isn't it always in survival??)
            if (((Object)this) instanceof ServerPlayer) { // yep
                ServerPlayer player = (ServerPlayer) (Object) this; // yeppers
                player.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
                CriteriaTriggers.USED_TOTEM.trigger(player, inHand);
            }
            setHealth(1.0f);
            level.broadcastEntityEvent(this, (byte)35); // Play totem effect if it prevented entity from dying
        }

        if (event.hasEffects()) {
            // If event has effects to apply to entity, remove existing effects.
            removeAllEffects();
            Iterable<MobEffectInstance> mobEffects = event.getEffects();
            for (MobEffectInstance mobEffect : mobEffects) {
                addEffect(mobEffect);
            }
        }

        return event.willLive();
    }
}

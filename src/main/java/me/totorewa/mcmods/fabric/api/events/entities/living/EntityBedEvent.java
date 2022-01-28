package me.totorewa.mcmods.fabric.api.events.entities.living;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

public class EntityBedEvent extends AbstractLivingEntityEvent {
    private static final Event<EntityBedEvent.Listener> listeners =
            EventFactory.createArrayBacked(
                    EntityBedEvent.Listener.class,
                    listeners -> event -> {
                        for (EntityBedEvent.Listener listener : listeners) {
                            listener.onUseBed(event);
                        }
                    });
    private static boolean hasListeners;

    private final BlockState bedState;
    private final BlockPos pos;
    private BlockPos headPos;
    private BlockPos feetPos;

    public EntityBedEvent(final LivingEntity entity, final BlockState bedState, final BlockPos pos) {
        super(entity);
        this.bedState = bedState;
        this.pos = pos;
    }

    public BlockState getBedState() {
        return bedState;
    }

    public BlockPos getTargetPos() {
        return pos;
    }

    public BlockPos getHeadPos() {
        if (headPos == null)
            calculatePartPos();
        return headPos;
    }

    public BlockPos getFeetPos() {
        if (feetPos == null)
            calculatePartPos();
        return feetPos;
    }

    private void calculatePartPos() {
        if (bedState.getValue(BedBlock.PART) == BedPart.HEAD) {
            headPos = pos;
            feetPos = pos.relative(bedState.getValue(BedBlock.FACING).getOpposite());
        } else {
            feetPos = pos;
            headPos = pos.relative(bedState.getValue(BedBlock.FACING));
        }
    }

    public static boolean hasListeners() {
        return hasListeners;
    }

    public static void listen(EntityBedEvent.Listener listener) {
        listeners.register(listener);
        hasListeners = true;
    }

    public static void onUseBed(EntityBedEvent event) {
        listeners.invoker().onUseBed(event);
    }

    public interface Listener {
        void onUseBed(EntityBedEvent event);
    }
}

package me.totorewa.mcmods.nofunallowed.helpers;

import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GlobalExperienceDecay {
    private static final float XP_MODIFIER_DECAY_THRESHOLD = 0.03125f;
    private static final float XP_MODIFIER_DECAY = 0.5f;
    private static final float XP_MODIFIER_RESTORE_RATE = 0.02f;
    private static final float MAX_XP_MODIFIER = 1.0f;
    private final Map<UUID, Float> xpModifiers = new Object2FloatOpenHashMap<>();
    private int xpLastTick = 0;

    public float getExperienceModifier(int tick, UUID uuid) {
        restore(tick);
        float mod = xpModifiers.getOrDefault(uuid, MAX_XP_MODIFIER);
        decay(tick, uuid, mod);
        return mod;
    }

    private void decay(int tick, UUID uuid, float mod) {
        mod = mod <= XP_MODIFIER_DECAY_THRESHOLD ? 0 : mod * XP_MODIFIER_DECAY;
        xpModifiers.put(uuid, mod);
        xpLastTick = tick;
    }

    private void restore(int tick) {
        int elapsed = tick - xpLastTick;
        Set<UUID> removed = new ObjectArraySet<>(xpModifiers.size());
        for (Map.Entry<UUID, Float> entry : xpModifiers.entrySet()) {
            float mod = entry.getValue();
            for (var i = 0; i < elapsed && mod < MAX_XP_MODIFIER; i++)
                mod += mod * XP_MODIFIER_RESTORE_RATE;

            if (mod >= 1.0f) removed.add(entry.getKey());
            else entry.setValue(mod);
        }

        if (!removed.isEmpty())
            for (UUID uuid : removed)
                xpModifiers.remove(uuid);
    }
}

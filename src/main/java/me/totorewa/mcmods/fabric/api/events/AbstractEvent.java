package me.totorewa.mcmods.fabric.api.events;

public abstract class AbstractEvent {
    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }
}

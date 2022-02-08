package me.totorewa.mcmods.nofunallowed.permissions;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.world.entity.Entity;

public class LuckoPermissionChecker extends PermissionChecker {
    @Override
    public boolean check(Entity entity, String permission) {
        return Permissions.check(entity, permission) || super.check(entity, permission);
    }
}

package me.totorewa.mcmods.nofunallowed.permissions;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class PermissionChecker {
    private static PermissionChecker INSTANCE;

    public boolean check(Entity entity, String permission) {
        MinecraftServer server = entity.getLevel().getServer();
        return server != null
                && entity instanceof ServerPlayer
                && server.getPlayerList().isOp(((ServerPlayer) entity).getGameProfile());
    }

    public static boolean hasPermission(Entity entity, String permission) {
        if (INSTANCE == null) {
            INSTANCE = hasPermissionsApi()
                    ? new LuckoPermissionChecker()
                    : new PermissionChecker();
        }

        return INSTANCE.check(entity, permission);
    }

    private static boolean hasPermissionsApi() {
        try {
            Class.forName("me.lucko.fabric.api.permissions.v0.Permissions");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}

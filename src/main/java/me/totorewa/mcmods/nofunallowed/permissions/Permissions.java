package me.totorewa.mcmods.nofunallowed.permissions;

import net.minecraft.world.entity.Entity;

public final class Permissions {
    private static String PREFIX = "nofunallowed.bypass.";
    public static Permissions BYPASS_NETHERROOF = new Permissions("netherroof");
    public static Permissions BYPASS_TOTEM = new Permissions("totems");
    public static Permissions BYPASS_MOBFARMNERF = new Permissions("farm.mob");
    public static Permissions BYPASS_RAIDFARMNERF = new Permissions("farm.raid");
    public static Permissions BYPASS_OVERCURING = new Permissions("villager.overcuring");
    public static Permissions BYPASS_BEDROOF = new Permissions("bed.requireroof");
    public static Permissions BYPASS_BEDLIGHT = new Permissions("bed.requirelight");
    public static Permissions BYPASS_CURINGLIMIT = new Permissions("villager.curinglimit");

    private final String permission;

    private Permissions(String permission) {
        this.permission = PREFIX + permission;
    }

    public boolean hasPermission(final Entity entity) {
        return PermissionChecker.hasPermission(entity, permission);
    }
}

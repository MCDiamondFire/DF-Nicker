package com.diamondfire.dfnicker.bot.command.permissions;

import net.dv8tion.jda.api.entities.Member;

import java.util.HashMap;

public enum Permission {
    // Goes off DFS discord
    STAFF(528943220235960321L, 999),
    USER(718905838969815152L, 1);

    private static final HashMap<Long, Permission> roleMap = new HashMap<>();

    static {
        for (Permission perm : values()) {
            roleMap.put(perm.getRole(), perm);
        }
    }

    private final long role;
    private final int permissionLevel;

    Permission(long roleID, int permissionLevel) {
        this.role = roleID;
        this.permissionLevel = permissionLevel;
    }

    public static Permission fromRole(long roleID) {
        Permission perm = roleMap.get(roleID);
        if (perm == null) {
            return USER;
        }
        return perm;
    }

    public long getRole() {
        return role;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public boolean hasPermission(Member member) {
        return hasPermission(PermissionHandler.getPermission(member));
    }

    public boolean hasPermission(Permission permission) {
        return getPermissionLevel() <= permission.getPermissionLevel();
    }
}

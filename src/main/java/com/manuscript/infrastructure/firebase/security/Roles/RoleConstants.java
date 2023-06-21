package com.manuscript.infrastructure.firebase.security.Roles;

public class RoleConstants {
    public static final String SUPER_USER = "role_super";
    public static final String REG_USER = "role_user";

    public static Boolean isRole(String role) {
        if (role.equalsIgnoreCase(SUPER_USER)) {
            return true;
        } else if (role.equalsIgnoreCase(REG_USER)) {
            return true;
        } else {
            return false;
        }
    }
}

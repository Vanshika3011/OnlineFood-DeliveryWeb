package com.narola.finalproject.enums;

public enum UserRolesEnum {
    ADMIN(1, "Admin"),
    RESTAURANTADMIN(2, "Restaurant Admin"),
    CUSTOMER(3, "Customer"),
    DELIVERYAGENT(4, "Delivery Agent");

    private final int roleIdValue;
    private final String roleName;

    private UserRolesEnum(int roleIdValue, String roleName) {
        this.roleIdValue = roleIdValue;
        this.roleName = roleName;
    }

    public int getRoleIdValue() {
        return roleIdValue;
    }

    public String getRoleName() {
        return roleName;
    }

    public static void getUserRole() {
        UserRolesEnum[] roles = UserRolesEnum.values();
        for (UserRolesEnum role : roles) {
            if (role.getRoleIdValue() > 1) {
                System.out.println("Enter " + role.getRoleIdValue() + " to signup as " + role.getRoleName() + ".");
            }
        }
    }
}

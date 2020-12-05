package com.ehmeth.co.uk.db.models.User;

public enum UserRole {
    GLOBAL_ADMIN,
    STORE_ADMIN,
    STORE_USER,
    BUYER;

    public boolean isGlobalAdmin() {
        return this == GLOBAL_ADMIN;
    }

    public boolean isBuyer() {
        return this == BUYER;
    }

    public boolean isSeller() {
        return this == STORE_ADMIN || this == STORE_USER;
    }
}

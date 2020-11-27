package com.ehmeth.co.uk.db.models.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPublicProfile {

    private String email;
    private String firstName;
    private String id;
    private String address;
    private UserRole role;

    public UserPublicProfile() {
    }

    public UserPublicProfile(String email,
                             String firstName,
                             String id,
                             String address,
                             UserRole role) {
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.address = address;
        this.role = role;
    }
}

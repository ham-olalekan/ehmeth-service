package com.ehmeth.co.uk.db.models.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserPublicProfile {

    private String email;
    private String firstName;
    private String id;
    private String address;
    private UserRole role;
    private Date createdAt;
    private Date updatedAt;

    public UserPublicProfile() {
    }

    public UserPublicProfile(String email,
                             String firstName,
                             String id,
                             String address,
                             UserRole role,
                             Date createdAt,
                             Date updatedAt) {
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.address = address;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

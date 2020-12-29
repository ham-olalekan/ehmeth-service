package com.ehmeth.co.uk.db.models.User;

import com.ehmeth.co.uk.db.models.Address;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Document(collection = "users")
public class User implements Serializable {
    private static long serialVersionUID = 1393857723979143129L;

    @Id
    private String id;

    @Indexed
    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private UserRole role;

    private String addressId;

    private String hashedPassword;

    private String storeId; //for users that belong to a store

    private Date createdAt;

    private Date updatedAt;

    private UserStatus userStatus;

    private String userAddressId;

    public UserPublicProfile getPublicProfile(){
        return UserPublicProfile
                .builder()
                .email(this.email)
                .firstName(this.firstName)
                .role(this.role)
                .id(this.id)
                .build();
    }

    public UserAdminInfo getUserAdminInfo(){
        return UserAdminInfo.builder()
                .fullName(this.lastName + " "+ this.firstName)
                .phoneNumber(this.phoneNumber)
                .signupDate(this.createdAt)
                .email(this.email)
                .build();
    }
}

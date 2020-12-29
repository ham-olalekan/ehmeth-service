package com.ehmeth.co.uk.db.models.User;

import com.ehmeth.co.uk.db.models.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserAdminInfo {
    private String fullName;
    private String email;
    private String phoneNumber;
    private Address address;
    private Date signupDate;
    private UserStatus userStatus;
    private long numberOfOrders;
}

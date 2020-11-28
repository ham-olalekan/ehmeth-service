package com.ehmeth.co.uk.db.models.store;

import lombok.Data;

@Data
public class SellerSignupRequest {
    private String storeName;
    private String registrationNumber;
    private String registrationName;
    private String country;
    private String borough;
    private String postCode;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String address;
    private String password;
    private String storeImage;

    public void validate(){
    }
}

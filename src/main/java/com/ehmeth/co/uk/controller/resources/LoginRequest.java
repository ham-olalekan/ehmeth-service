package com.ehmeth.co.uk.controller.resources;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.controller.resources.LoginRequest;

import java.util.Map;

public interface AuthenticationService {
    Map<Object, Object> loginUser(LoginRequest loginRequest);
}

package com.ehmeth.co.uk.controller;

import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.controller.resources.LoginRequest;
import com.ehmeth.co.uk.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<ApiResponseJson> handleUserLogin(@RequestBody LoginRequest loginRequest) {
        log.info("Attempting to login user with request {}", loginRequest.getEmail());
        return new ResponseEntity(new ApiResponseJson(true, "login successful", authenticationService.loginUser(loginRequest)), HttpStatus.OK);
    }
}

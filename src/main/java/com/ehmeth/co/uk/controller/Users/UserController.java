package com.ehmeth.co.uk.controller.Users;

import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.db.models.User.BuyerCreationRequest;
import com.ehmeth.co.uk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/buyer/sign-up")
    public ResponseEntity<ApiResponseJson> handleBuyerAccountCreation(@RequestBody BuyerCreationRequest request) {
        log.info("Attempting to create user with details ", request);
        return new ResponseEntity(new ApiResponseJson(true, "successful", userService.createBuyerUser(request)), HttpStatus.OK);
    }
}

package com.ehmeth.co.uk.controller;

import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.db.models.product.SellerSignupRequest;
import com.ehmeth.co.uk.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/store")
public class StoreController {

    private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @CrossOrigin
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponseJson<?>> handleStoreSignUp(@RequestBody SellerSignupRequest request) {
        log.info("Creating a store for for request [{}]", request.toString());
        return new ResponseEntity(new ApiResponseJson(true, "successful", storeService.createStore(request)), HttpStatus.OK);
    }
}

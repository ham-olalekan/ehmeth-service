package com.ehmeth.co.uk.controller.stores;

import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.store.Store;
import com.ehmeth.co.uk.db.models.store.StoreStatus;
import com.ehmeth.co.uk.service.StoreService;
import com.ehmeth.co.uk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private StoreService storeService;
    private UserService userService;

    @Autowired
    public AdminController(StoreService storeService,
                           UserService userService) {
        this.storeService = storeService;
        this.userService = userService;
    }

    @CrossOrigin
    @PutMapping("/store/{storeId}/account")
    public ResponseEntity<ApiResponseJson> handleUpdateOfStoresAccount(@PathVariable("storeId") final String storeId,
                                                                       @RequestParam("status") final StoreStatus status,
                                                                       Principal principal) {
        String userId = principal.getName();
        User user = userService.getUserById(userId);
        if (!user.getRole().isGlobalAdmin()) {
            return new ResponseEntity(new ApiResponseJson(false, "Not Authorized", null), HttpStatus.UNAUTHORIZED);
        }
        Store store = storeService.findByStoreId(storeId).orElseThrow(() -> new NotFoundException("Store with Id [" + storeId + "] was not found"));
        store.setStoreStatus(status);
        storeService.update(store);
        return new ResponseEntity(new ApiResponseJson(true, "successul", store), HttpStatus.OK);
    }
}

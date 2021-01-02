package com.ehmeth.co.uk.controller.Users;

import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.order.OrderItemStatus;
import com.ehmeth.co.uk.db.models.store.Store;
import com.ehmeth.co.uk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("admin/users")
public class UserAdminController {

    private UserService userService;

    @Autowired
    public UserAdminController(UserService userService) { this.userService = userService; }


    @GetMapping("/buyers/all")
    public ResponseEntity<ApiResponseJson> handleFetchingOfAllBuyers(Principal principal,
                                                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                                                       @RequestParam(name = "size", defaultValue = "50") int size) {
        String userId = principal.getName();
        User user = userService.getUserById(userId);
        if (!user.getRole().isGlobalAdmin()) {
            return new ResponseEntity(new ApiResponseJson(false, "Un-Authorized Seller", null), HttpStatus.UNAUTHORIZED);
        }

        log.info("List of all buyers");
        return new ResponseEntity(new ApiResponseJson(true, "successful", userService.fetchAllBuyers(page, size)), HttpStatus.OK);
    }

}

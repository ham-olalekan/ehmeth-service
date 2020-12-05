package com.ehmeth.co.uk.controller.order;


import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.cart.CartItemModel;
import com.ehmeth.co.uk.db.models.order.Order;
import com.ehmeth.co.uk.service.OrderService;
import com.ehmeth.co.uk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;
    private UserService userService;

    @Autowired
    public OrderController(OrderService orderService,
                           UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseJson> handleCreationOfOrderItems(Principal principal,
                                                                      @RequestBody List<CartItemModel> cartItemModelList) {
        String userId = principal.getName();
        User user = userService.getUserById(userId);
        if(!user.getRole().isBuyer()) {
            return new ResponseEntity(new ApiResponseJson(false, "Not Authorized", null), HttpStatus.UNAUTHORIZED);
        }
        Order order = orderService.createOrder(user, cartItemModelList);

        return new ResponseEntity(new ApiResponseJson(true, "successful", order), HttpStatus.OK);
    }


}

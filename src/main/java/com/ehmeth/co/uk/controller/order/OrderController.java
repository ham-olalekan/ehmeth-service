package com.ehmeth.co.uk.controller.order;


import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.cart.CartItemModel;
import com.ehmeth.co.uk.db.models.order.Order;
import com.ehmeth.co.uk.db.models.order.OrderItem;
import com.ehmeth.co.uk.db.models.order.OrderItemStatus;
import com.ehmeth.co.uk.db.models.store.Store;
import com.ehmeth.co.uk.service.OrderService;
import com.ehmeth.co.uk.service.StoreService;
import com.ehmeth.co.uk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;
    private UserService userService;
    private StoreService storeService;

    @Autowired
    public OrderController(OrderService orderService,
                           UserService userService,
                           StoreService storeService) {
        this.orderService = orderService;
        this.userService = userService;
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseJson> handleCreationOfOrderItems(Principal principal,
                                                                      @RequestBody List<CartItemModel> cartItemModelList) {
        String userId = principal.getName();
        User user = userService.getUserById(userId);
        if (!user.getRole().isBuyer()) {
            return new ResponseEntity(new ApiResponseJson(false, "Not Authorized", null), HttpStatus.UNAUTHORIZED);
        }
        Order order = orderService.createOrder(user, cartItemModelList);

        return new ResponseEntity(new ApiResponseJson(true, "successful", order), HttpStatus.OK);
    }

    @GetMapping("/store")
    public ResponseEntity<ApiResponseJson> handleFetchingOfStoreOrders(Principal principal,
                                                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                                                       @RequestParam(name = "size", defaultValue = "50") int size,
                                                                       @RequestParam(name = "direction", defaultValue = "desc") String direction,
                                                                       @RequestParam(name = "status")OrderItemStatus status) {
        String userId = principal.getName();
        User user = userService.getUserById(userId);
        if (!user.getRole().isSeller()) {
            return new ResponseEntity(new ApiResponseJson(false, "Un-Authorized Seller", null), HttpStatus.UNAUTHORIZED);
        }
        Store store = storeService.findByStoreId(user.getStoreId()).orElseThrow(() -> new NotFoundException("Store with ID: [" + user.getStoreId() + "] not found"));
        return new ResponseEntity(new ApiResponseJson(true, "successful", orderService.fetchStoreOrders(store, page, size, direction,status)), HttpStatus.OK);
    }

    @GetMapping("/buyer")
    public ResponseEntity<ApiResponseJson> handleFetchingOfBuyerOrders(Principal principal,
                                                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                                                       @RequestParam(name = "size", defaultValue = "50") int size,
                                                                       @RequestParam(name = "direction", defaultValue = "desc") String direction) {
        String userId = principal.getName();
        User user = userService.getUserById(userId);
        if (!user.getRole().isBuyer()) {
            return new ResponseEntity(new ApiResponseJson(false, "Un-Authorized", null), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(new ApiResponseJson(true, "successful", orderService.fetchBuyerOrders(user, page, size, direction)), HttpStatus.OK);
    }

    @PutMapping("/{orderItemId}/status")
    public ResponseEntity<ApiResponseJson> handleUpdatingOrderStatus(Principal principal,
                                                                     @PathVariable("orderItemId") final String orderItemId,
                                                                     @RequestParam("orderStatus") OrderItemStatus status) {
        String userId = principal.getName();
        User user = userService.getUserById(userId);
        if (!user.getRole().isSeller()) {
            return new ResponseEntity(new ApiResponseJson(false, "Un-Authorized", null), HttpStatus.UNAUTHORIZED);
        }
        OrderItem orderItem = orderService.OrderItemById(orderItemId);
        orderItem.setStatus(status);
        return new ResponseEntity(new ApiResponseJson(true, "successful", orderService.updateOrderItem(orderItem)), HttpStatus.OK);
    }
}

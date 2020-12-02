package com.ehmeth.co.uk.controller.cart;

import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.db.models.order.Cart;
import com.ehmeth.co.uk.db.models.order.CartItem;
import com.ehmeth.co.uk.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("cart")
public class cartController {

    private CartService cartService;

    @Autowired
    public cartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{userId}/create")
    public ResponseEntity<ApiResponseJson> handleCreationOfCart(@PathVariable("userId") final String userId,
                                                                @RequestBody Cart request) {
        log.info("Creating new cart for request {}", request);
        return new ResponseEntity<>(new ApiResponseJson(true,"successful",
                cartService.createCart(userId, request)), HttpStatus.OK);
    }

    @PostMapping("/cart-item/{cartId}/create")
    public  ResponseEntity<ApiResponseJson> handleCreationOfCartItem(@PathVariable("cartId") final String cartId,
                                                                     @RequestParam(name = "productId", defaultValue = "") String productId,
                                                                     @RequestParam(name = "quantity", defaultValue = "1") int quantity,
                                                                     @RequestBody CartItem request) {
        log.info("Creating new cart-item for cart: {} ; request {}",cartId, request);
        return new ResponseEntity(new ApiResponseJson(true, "successful",
                cartService.addCartItem(cartId, productId, quantity, request)), HttpStatus.OK);
    }

    @GetMapping("/{cartId}/cart-items")
    public ResponseEntity<ApiResponseJson> FetchingAllCartItems(@PathVariable("cartId") final String cartId,
                                                                @RequestParam(name = "page", defaultValue = "0") int page,
                                                                @RequestParam(name = "size", defaultValue = "20") int size) {
        log.info("List of cart-items in cart: {}",cartId);
        return new ResponseEntity(new ApiResponseJson(true, "successful",
                cartService.fetchCartItemForCart(page, size, cartId)), HttpStatus.OK);
    }




}

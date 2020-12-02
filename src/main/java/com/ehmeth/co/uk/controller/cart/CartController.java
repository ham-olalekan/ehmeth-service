package com.ehmeth.co.uk.controller.cart;

import com.ehmeth.co.uk.Exceptions.BadRequestException;
import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.cart.Cart;
import com.ehmeth.co.uk.db.models.product.ProductPageModel;
import com.ehmeth.co.uk.service.CartService;
import com.ehmeth.co.uk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;
    private UserService userService;

    @Autowired
    public CartController(CartService cartService,
                          UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseJson> handleCreationOfCartItems(Principal principal,
                                                                     @RequestBody List<ProductPageModel> request) {
        if (request.size() == 0) {
            throw new BadRequestException("Minimum of One item is required to create a cart");
        }
        String userId = principal.getName();
        User buyer = userService.getUserById(userId);
        return new ResponseEntity<ApiResponseJson>(new ApiResponseJson(true, "successful", cartService.createCartItemsForUser(buyer, request)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponseJson<Cart>> handleFetchingOfCartForUser(Principal principal) {
        String userId = principal.getName();
        User buyer = userService.getUserById(userId);
        return new ResponseEntity(new ApiResponseJson(true, "successful", cartService.getUserCart(userId)), HttpStatus.OK);
    }
}

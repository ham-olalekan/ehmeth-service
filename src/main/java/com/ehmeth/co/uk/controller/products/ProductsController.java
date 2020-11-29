package com.ehmeth.co.uk.controller.products;

import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.product.Product;
import com.ehmeth.co.uk.service.ProductService;
import com.ehmeth.co.uk.service.StoreService;
import com.ehmeth.co.uk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "products")
public class ProductsController {
    private ProductService productService;
    private UserService userService;
    private StoreService storeService;

    @Autowired
    public ProductsController(ProductService productService,
                              UserService userService,
                              StoreService storeService) {
        this.productService = productService;
        this.userService = userService;
        this.storeService = storeService;
    }

    @PostMapping("/add-new")
    public ResponseEntity<ApiResponseJson> handleAddingProductsForSeller(@RequestBody Product product,
                                                                         Principal principal) {
        String userId = principal.getName();
        User user = userService.getUserById(userId);
        if (user.getRole().isGlobalAdmin() || user.getRole().isBuyer()) {
            return new ResponseEntity(new ApiResponseJson(false, "User un-authorized to create product product", null), HttpStatus.OK);
        }

        return new ResponseEntity(new ApiResponseJson(true, "successful", productService.AddProduct(user.getStoreId(), product)), HttpStatus.OK);
    }

}

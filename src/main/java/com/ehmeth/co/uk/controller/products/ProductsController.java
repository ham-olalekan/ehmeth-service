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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{storeId}/products")
    public ResponseEntity<ApiResponseJson> handleAddingProductsForSeller(@PathVariable("storeId") final String storeId,
                                                                         @RequestParam(name = "page",defaultValue = "0") int page,
                                                                         @RequestParam(name = "size", defaultValue = "20") int size) {
        //log.info("List of products in Store: {}",storeId);
        return new ResponseEntity(new ApiResponseJson(true, "successful", productService.fetchStoreProducts(page,size, storeId)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseJson> handleGettingOfAllProducts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                      @RequestParam(name = "size", defaultValue = "20") int size){
        //log.info("List of all products");
        return new ResponseEntity(new ApiResponseJson(true, "successful", productService.fetchAllProducts(page, size)), HttpStatus.OK);
    }
}

package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.product.Product;
import com.ehmeth.co.uk.db.models.product.ProductPageModel;

import java.util.Map;
import java.util.Optional;

public interface ProductService {
    Product AddProduct(final String storeId, Product product);

    Map<Object, Object> fetchStoreProducts(final int page,
                                           final int size,
                                           final String storeId);

    Map<Object, Object> fetchAllProducts(final int page,
                                         final int size,
                                         final String categoryId);

    ProductPageModel productToPageModel(Product product);

    ProductPageModel fetchProductModel(String productId);

    Product editProduct(String productId,
                        Product product);
    Optional<Product> getById(final String productId);



}

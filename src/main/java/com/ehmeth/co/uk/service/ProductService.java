package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.product.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Product AddProduct(final String storeId, Product product);

    Map<Object, Object> fetchStoreProducts(final int page,
                                           final int size,
                                           final String storeId);

    Map<Object, Object> fetchAllProducts(final int page,
                                         final int size);

    Product editProduct(String productId,
                        Product product);
}

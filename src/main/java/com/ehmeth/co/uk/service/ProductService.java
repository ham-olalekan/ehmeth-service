package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.product.Product;

import java.util.Map;

public interface ProductService {
    Product AddProduct(final String storeId, Product product);

    Map<Object, Object> fetchStoreProducts(final int page,
                                           final int size,
                                           final String storeId);

}

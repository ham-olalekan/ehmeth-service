package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.db.models.product.Product;
import com.ehmeth.co.uk.db.models.store.Store;
import com.ehmeth.co.uk.db.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private StoreService storeService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              StoreService storeService) {
        this.productRepository = productRepository;
        this.storeService = storeService;
    }

    @Override
    public Product AddProduct(final String storeId,
                              Product product) {
        Store store = storeService
                .findByStoreId(storeId)
                .orElseThrow(() -> new NotFoundException("store not found for ID [" + storeId + "]"));
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        product.setStoreId(store.getStoreId());
        return productRepository.save(product);
    }

    public Map<Object, Object> fetchStoreProducts(final int page,
                                                  final int size,
                                                  final String storeId) {
        Store store = storeService
                .findByStoreId(storeId)
                .orElseThrow(() -> new NotFoundException("store not found for ID [" + storeId + "]"));
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Product> productPage = productRepository.findByStoreId(storeId, pageRequest);

        Map<Object, Object> storeProductPage = new HashMap<>();

        storeProductPage.put("totalProducts", productPage.getTotalElements());
        storeProductPage.put("totalProductsOnPage", productPage.getNumberOfElements());
        storeProductPage.put("totalPages", productPage.getTotalPages());
        storeProductPage.put("products", productPage.getContent());
        return storeProductPage;
    }
}

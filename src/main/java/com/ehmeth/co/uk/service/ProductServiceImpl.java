package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.db.models.product.Product;
import com.ehmeth.co.uk.db.models.product.ProductPageModel;
import com.ehmeth.co.uk.db.models.store.Store;
import com.ehmeth.co.uk.db.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private StoreService storeService;

    private List<ProductPageModel> productPageModels;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              StoreService storeService) {
        this.productRepository = productRepository;
        this.storeService = storeService;
        this.productPageModels = new ArrayList<>();
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
        storeProductPage.put("products", productPageModels);
        return storeProductPage;
    }

    public Map<Object, Object> fetchAllProducts(final int page,
                                                final int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Product> productPages = productRepository.findAll(pageRequest);

        Map<Object, Object> productPage = new HashMap<>();
        productPage.put("totalProducts", productPages.getTotalElements());
        productPage.put("totalProductsOnPage", productPages.getNumberOfElements());
        productPage.put("totalPages", productPages.getTotalPages());
        productPages.getContent().forEach(p -> {
            productPageModels.add(productToPageModel(p));
        });
        productPage.put("products", productPageModels);
        return productPage;
    }

    public ProductPageModel productToPageModel(Product product) {
        Optional<Store> store = storeService.findByStoreId(product.getStoreId());
        String storeName;
        if (!store.isPresent()) {
            storeName = "";
        } else {
            storeName = store.get().getStoreName();
        }
        return new ProductPageModel(
                product.getId(),
                storeName,
                product.getLocalName(),
                product.getEnglishName(),
                product.getPricingType().getPrettyName(),
                "image.jpeg",
                product.getPriceValue(),
                0,
                product.getQuantity()
        );
    }

    @Override
    public ProductPageModel fetchProductModel(String productId) {
        Product productOption = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product with id[" + productId + "] not found"));
        return productToPageModel(productOption);
    }
}

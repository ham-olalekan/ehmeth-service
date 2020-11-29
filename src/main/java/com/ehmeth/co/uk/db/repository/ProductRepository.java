package com.ehmeth.co.uk.db.repository;

import com.ehmeth.co.uk.db.models.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Page<Product> findByStoreId(String storeId, Pageable pageable);
}

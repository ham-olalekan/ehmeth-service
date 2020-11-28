package com.ehmeth.co.uk.db.repository;

import com.ehmeth.co.uk.db.models.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends MongoRepository<ProductCategory, String> {
}

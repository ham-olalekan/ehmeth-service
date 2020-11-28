package com.ehmeth.co.uk.db.repository;

import com.ehmeth.co.uk.db.models.ProductSubCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSubCategoryRepository extends MongoRepository<ProductSubCategory, String> {
    List<ProductSubCategory> findByCategoryId(String categoryId);
}

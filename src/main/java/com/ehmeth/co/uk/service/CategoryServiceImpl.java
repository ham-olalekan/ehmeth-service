package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.db.models.ProductCategory;
import com.ehmeth.co.uk.db.models.ProductSubCategory;
import com.ehmeth.co.uk.db.repository.ProductCategoryRepository;
import com.ehmeth.co.uk.db.repository.ProductSubCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private ProductCategoryRepository productCategoryRepository;
    private ProductSubCategoryRepository productSubCategoryRepository;

    @Autowired
    public CategoryServiceImpl(ProductCategoryRepository productCategoryRepository,
                               ProductSubCategoryRepository productSubCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productSubCategoryRepository = productSubCategoryRepository;
    }

    @Override
    public ProductCategory createCategory(ProductCategory request) {
        return productCategoryRepository.save(request);
    }

    @Override
    public ProductSubCategory addSubCategory(String categoryId, ProductSubCategory subCategory) {
        ProductCategory category = productCategoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Invalid category id [" + categoryId + "]"));

        subCategory.setCategoryId(categoryId);
        return productSubCategoryRepository.save(subCategory);
    }

    @Override
    public List<ProductSubCategory> fetchSubCategoriesForCategory(final String categoryId){
        ProductCategory category = productCategoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Invalid category id [" + categoryId + "]"));

        return productSubCategoryRepository.findByCategoryId(categoryId);
    }

    public List<ProductCategory> fetchAllCategories(){
        return productCategoryRepository.findAll();
    }
}

package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.ProductCategory;
import com.ehmeth.co.uk.db.models.ProductSubCategory;

import java.util.List;

public interface CategoryService {
    ProductCategory createCategory(ProductCategory request);

    ProductSubCategory addSubCategory(final String categoryId, ProductSubCategory subCategory);

    List<ProductSubCategory> fetchSubCategoriesForCategory(final String categoryId);

    List<ProductCategory> fetchAllCategories();

    ProductCategory findProductCategory(final String categoryId);
}

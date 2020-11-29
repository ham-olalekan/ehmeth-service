package com.ehmeth.co.uk.controller.category;

import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.db.models.ProductCategory;
import com.ehmeth.co.uk.db.models.ProductSubCategory;
import com.ehmeth.co.uk.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseJson> handleCreationOfCategory(@RequestBody ProductCategory request) {
        log.info("Creating new category for request {}", request);
        return new ResponseEntity(new ApiResponseJson(true, "successful", categoryService.createCategory(request)), HttpStatus.OK);
    }

    @PostMapping("/sub-category/{categoryId}/create")
    public ResponseEntity<ApiResponseJson> handleCreationOfSubCategory(@PathVariable("categoryId") final String categoryId, @RequestBody ProductSubCategory request) {
        log.info("Creating new sub-category for category: {} ; request {}",categoryId, request);
        return new ResponseEntity(new ApiResponseJson(true, "successful", categoryService.addSubCategory(categoryId,request)), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponseJson> FetchingAllCategories() {
        log.info("List of categories");
        return new ResponseEntity(new ApiResponseJson(true, "successful", categoryService.fetchAllCategories()), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/sub-categories")
    public ResponseEntity<ApiResponseJson> FetchingAllSubCategories(@PathVariable("categoryId") final String categoryId) {
        log.info("List of sub-categories in category: {}",categoryId);
        return new ResponseEntity(new ApiResponseJson(true, "successful", categoryService.fetchSubCategoriesForCategory(categoryId)), HttpStatus.OK);
    }

}

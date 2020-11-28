package com.ehmeth.co.uk.controller.category;

import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.db.models.ProductCategory;
import com.ehmeth.co.uk.db.models.ProductSubCategory;
import com.ehmeth.co.uk.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

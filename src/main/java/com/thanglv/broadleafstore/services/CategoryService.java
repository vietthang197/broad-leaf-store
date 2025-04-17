package com.thanglv.broadleafstore.services;

import com.thanglv.broadleafstore.dto.PaginationDto;
import com.thanglv.broadleafstore.entity.Category;
import com.thanglv.broadleafstore.request.CategorySearchRequest;
import com.thanglv.broadleafstore.request.CreateCategoryRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    
    ResponseEntity<Category> getCategoryById(String id);
    
    Category createCategory(CreateCategoryRequest request);
    
    ResponseEntity<Category> updateCategory(String id, Category updatedCategory);
    
    ResponseEntity<Void> deleteCategory(String id);
    
    PaginationDto<Category> searchCategories(CategorySearchRequest request);
} 
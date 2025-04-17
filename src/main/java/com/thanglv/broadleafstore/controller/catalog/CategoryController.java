package com.thanglv.broadleafstore.controller.catalog;

import com.thanglv.broadleafstore.dto.PaginationDto;
import com.thanglv.broadleafstore.entity.Category;
import com.thanglv.broadleafstore.request.CategorySearchRequest;
import com.thanglv.broadleafstore.request.CreateCategoryRequest;
import com.thanglv.broadleafstore.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = {"*"}, allowCredentials = "true", allowedHeaders = "*")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories(CategorySearchRequest request) {
        // Nếu không có tham số tìm kiếm, trả về tất cả danh mục
        if ((request.getName() == null || request.getName().isEmpty()) &&
                (request.getSlug() == null || request.getSlug().isEmpty()) &&
                (request.getParentCategoryId() == null || request.getParentCategoryId().isEmpty())) {
            return categoryService.getAllCategories();
        }
        
        // Nếu có tham số tìm kiếm, thực hiện tìm kiếm và trả về kết quả phân trang
        PaginationDto<Category> result = categoryService.searchCategories(request);
        return result.getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public Category createCategory(@RequestBody CreateCategoryRequest request) {
        return categoryService.createCategory(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable String id, @RequestBody Category updatedCategory) {
        return categoryService.updateCategory(id, updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        return categoryService.deleteCategory(id);
    }
    
    @GetMapping("/search")
    public ResponseEntity<PaginationDto<Category>> searchCategories(CategorySearchRequest request) {
        return ResponseEntity.ok(categoryService.searchCategories(request));
    }
}

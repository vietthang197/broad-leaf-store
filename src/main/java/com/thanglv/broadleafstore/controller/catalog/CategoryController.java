package com.thanglv.broadleafstore.controller.catalog;

import com.thanglv.broadleafstore.entity.Category;
import com.thanglv.broadleafstore.repository.CategoryRepository;
import com.thanglv.broadleafstore.request.CreateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = {"*"}, allowCredentials = "true", allowedHeaders = "*")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String id) {
        return categoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Category createCategory(@RequestBody CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setDescription(request.getDescription());

        if (Strings.isNotBlank(request.getParentCategoryId())) {
            Optional<Category> parentCategoryOptional = categoryRepository.findById(request.getParentCategoryId());
            if (parentCategoryOptional.isPresent()) {
                category.setParentCategoryId(parentCategoryOptional.get().getId());
            } else {
                throw new RuntimeException("Parent category not found");
            }
        }
        return categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable String id, @RequestBody Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    category.setSlug(updatedCategory.getSlug());
                    category.setDescription(updatedCategory.getDescription());
                    category.setUpdatedAt(updatedCategory.getUpdatedAt());
                    return ResponseEntity.ok(categoryRepository.save(category));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

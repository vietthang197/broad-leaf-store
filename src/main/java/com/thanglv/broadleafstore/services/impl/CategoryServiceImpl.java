package com.thanglv.broadleafstore.services.impl;

import com.thanglv.broadleafstore.dto.PaginationDto;
import com.thanglv.broadleafstore.entity.Category;
import com.thanglv.broadleafstore.repository.CategoryRepository;
import com.thanglv.broadleafstore.request.CategorySearchRequest;
import com.thanglv.broadleafstore.request.CreateCategoryRequest;
import com.thanglv.broadleafstore.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public ResponseEntity<Category> getCategoryById(String id) {
        return categoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public Category createCategory(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setDescription(request.getDescription());

        if (Strings.isNotBlank(request.getParentCategoryId())) {
            Optional<Category> parentCategoryOptional = categoryRepository.findById(request.getParentCategoryId());
            if (parentCategoryOptional.isEmpty()) {
                throw new RuntimeException("Parent category not found");
            }
            
            // Lưu trước để có ID cho việc kiểm tra vòng tròn
            category = categoryRepository.save(category);
            
            // Kiểm tra vòng tròn parent-child
            if (wouldCreateCycle(category.getId(), request.getParentCategoryId())) {
                throw new RuntimeException("Cannot set parent: would create a circular reference");
            }
            
            category.setParentCategoryId(parentCategoryOptional.get().getId());
            return categoryRepository.save(category);
        }
        return categoryRepository.save(category);
    }

    @Override
    public ResponseEntity<Category> updateCategory(String id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    // Nếu đang cập nhật parentCategoryId
                    if (updatedCategory.getParentCategoryId() != null) {
                        // Kiểm tra vòng tròn parent-child
                        if (wouldCreateCycle(id, updatedCategory.getParentCategoryId())) {
                            throw new RuntimeException("Cannot set parent: would create a circular reference");
                        }
                    }
                    
                    category.setName(updatedCategory.getName());
                    category.setSlug(updatedCategory.getSlug());
                    category.setDescription(updatedCategory.getDescription());
                    category.setParentCategoryId(updatedCategory.getParentCategoryId());
                    category.setUpdatedAt(updatedCategory.getUpdatedAt());
                    return ResponseEntity.ok(categoryRepository.save(category));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteCategory(String id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Kiểm tra xem việc đặt parentId cho categoryId có tạo ra vòng tròn hay không
     * 
     * @param categoryId ID của category sẽ được cập nhật
     * @param parentId ID của category cha mới
     * @return true nếu sẽ tạo ra vòng tròn, false nếu không
     */
    private boolean wouldCreateCycle(String categoryId, String parentId) {
        // Nếu category cha là chính nó -> vòng tròn
        if (categoryId.equals(parentId)) {
            return true;
        }
        
        // Tìm tất cả con cháu của categoryId
        Set<String> descendants = new HashSet<>();
        findAllDescendants(categoryId, descendants);
        
        // Nếu parentId nằm trong danh sách con cháu -> vòng tròn
        return descendants.contains(parentId);
    }
    
    /**
     * Tìm tất cả con cháu của một category
     * 
     * @param categoryId ID của category cần tìm con cháu
     * @param descendants Set chứa ID của tất cả con cháu (sẽ được cập nhật)
     */
    private void findAllDescendants(String categoryId, Set<String> descendants) {
        // Tìm tất cả category có parentCategoryId = categoryId
        List<Category> children = mongoTemplate.find(
            Query.query(Criteria.where("parentCategoryId").is(categoryId)), 
            Category.class
        );
        
        for (Category child : children) {
            String childId = child.getId();
            // Nếu con này đã được xử lý -> bỏ qua để tránh vòng lặp vô hạn
            if (descendants.contains(childId)) {
                continue;
            }
            
            // Thêm vào danh sách con cháu
            descendants.add(childId);
            
            // Tìm tiếp các cháu (gọi đệ quy)
            findAllDescendants(childId, descendants);
        }
    }

    @Override
    public PaginationDto<Category> searchCategories(CategorySearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Query query = new Query().with(pageable);

        if (request.getName() != null && !request.getName().isEmpty()) {
            query.addCriteria(Criteria.where("name").regex(request.getName(), "i"));
        }

        if (request.getSlug() != null && !request.getSlug().isEmpty()) {
            query.addCriteria(Criteria.where("slug").regex(request.getSlug(), "i"));
        }

        if (request.getParentCategoryId() != null && !request.getParentCategoryId().isEmpty()) {
            query.addCriteria(Criteria.where("parentCategoryId").is(request.getParentCategoryId()));
        }

        List<Category> categories = mongoTemplate.find(query, Category.class);
        long count = mongoTemplate.count(query.skip(-1).limit(-1), Category.class);

        Page<Category> categoryPage = PageableExecutionUtils.getPage(categories, pageable, () -> count);
        PaginationDto<Category> categoryPaginationDto = new PaginationDto<>();
        categoryPaginationDto.setContent(categoryPage.getContent());
        PaginationDto.PageInfo pageInfo = new PaginationDto.PageInfo();
        pageInfo.setTotalPages(categoryPage.getTotalPages());
        pageInfo.setTotalElements(categoryPage.getTotalElements());
        pageInfo.setSize(categoryPage.getSize());
        pageInfo.setNumber(categoryPage.getNumber());
        categoryPaginationDto.setPageInfo(pageInfo);
        return categoryPaginationDto;
    }
} 
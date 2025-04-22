package com.thanglv.broadleafstore.repository;

import com.thanglv.broadleafstore.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByParentCategoryId(String parentCategoryId);
}

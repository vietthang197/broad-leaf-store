package com.thanglv.broadleafstore.repository;

import com.thanglv.broadleafstore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAllByOrderByCreatedAtDesc();
    Page<Product> findAllByAvailableOnlineOrderByCreatedAtDesc(Boolean availableOnline, Pageable pageable);
    
    // Đếm số lượng sản phẩm có tham chiếu đến một category
    long countByCategoryId(String categoryId);
}

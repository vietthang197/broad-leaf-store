package com.thanglv.broadleafstore.repository;

import com.thanglv.broadleafstore.entity.ProductVariant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends MongoRepository<ProductVariant, String> {
}

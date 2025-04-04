package com.thanglv.broadleafstore.repository;

import com.thanglv.broadleafstore.entity.ProductOptionValue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionValueRepository extends MongoRepository<ProductOptionValue, String> {
}

package com.thanglv.broadleafstore.repository;

import com.thanglv.broadleafstore.entity.ProductOptionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionTypeRepository extends MongoRepository<ProductOptionType, String> {
}

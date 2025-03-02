package com.thanglv.broadleafstore.repository;

import com.thanglv.broadleafstore.entity.ProductOption;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends MongoRepository<ProductOption, String> {
}

package com.thanglv.broadleafstore.repository;

import com.thanglv.broadleafstore.entity.ProductAssets;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAssetsRepository extends MongoRepository<ProductAssets, String> {
}

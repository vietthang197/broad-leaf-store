package com.thanglv.broadleafstore.repository;

import com.thanglv.broadleafstore.entity.Asset;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends MongoRepository<Asset, String> {
}

package com.ehmeth.co.uk.db.repository;

import com.ehmeth.co.uk.db.models.store.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends MongoRepository<Store, String> {
    Optional<Store> findByStoreId(final String storeId);
}

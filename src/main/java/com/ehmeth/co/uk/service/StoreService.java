package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.product.SellerSignupRequest;
import com.ehmeth.co.uk.db.models.store.Store;

import java.util.Optional;

public interface StoreService {
    Store createStore(SellerSignupRequest request);
    Optional<Store> findByStoreId(final String id);
    Store update(Store store);
}

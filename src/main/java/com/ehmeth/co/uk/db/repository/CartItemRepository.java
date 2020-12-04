package com.ehmeth.co.uk.db.repository;

import com.ehmeth.co.uk.db.models.cart.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartItemRepository extends MongoRepository<CartItem, String> {
    List<CartItem> findByUserId(final String userId);
}

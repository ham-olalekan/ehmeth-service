package com.ehmeth.co.uk.db.repository;

import com.ehmeth.co.uk.db.models.cart.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
}

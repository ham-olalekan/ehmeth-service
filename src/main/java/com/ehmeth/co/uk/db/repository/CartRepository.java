package com.ehmeth.co.uk.db.repository;

import com.ehmeth.co.uk.db.models.order.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByCartId(final String cartId);
}

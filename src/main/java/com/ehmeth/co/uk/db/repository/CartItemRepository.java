package com.ehmeth.co.uk.db.repository;

import com.ehmeth.co.uk.db.models.order.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends MongoRepository<CartItem, String> {
    Page<CartItem> findByCartId(String cardId, Pageable pageable);
}


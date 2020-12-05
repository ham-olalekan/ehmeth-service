package com.ehmeth.co.uk.db.repository;

import com.ehmeth.co.uk.db.models.order.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderItemRepository extends MongoRepository<OrderItem, String> {
    Page<OrderItem> findByStoreId(String storeId, Pageable pageable);
}

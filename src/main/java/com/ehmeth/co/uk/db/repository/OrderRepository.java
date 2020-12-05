package com.ehmeth.co.uk.db.repository;

import com.ehmeth.co.uk.db.models.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findByOrderId(final String orderId);
}

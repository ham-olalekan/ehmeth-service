package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.cart.CartItemModel;
import com.ehmeth.co.uk.db.models.order.Order;
import com.ehmeth.co.uk.db.models.order.OrderItem;
import com.ehmeth.co.uk.db.models.order.OrderItemStatus;
import com.ehmeth.co.uk.db.models.store.Store;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order createOrder(User user, List<CartItemModel> cartItems);

    Map<Object, Object> fetchStoreOrders(Store store,
                                         int page,
                                         int size,
                                         String direction,
                                         OrderItemStatus status);

    Map<Object, Object> fetchBuyerOrders(User user,
                                         int page,
                                         int size,
                                         String direction);

    OrderItem updateOrderItem(OrderItem orderItem);

    OrderItem OrderItemById(String orderItemId);
}

package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.cart.CartItemModel;
import com.ehmeth.co.uk.db.models.order.Order;
import com.ehmeth.co.uk.db.models.store.Store;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order createOrder(User user, List<CartItemModel> cartItems);

    Map<Object, Object> fetchStoreOrders(Store store,
                                         int page,
                                         int size,
                                         String direction);

    Map<Object, Object> fetchBuyerOrders(User user,
                           int page,
                           int size,
                           String direction);
}

package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.cart.CartItemModel;
import com.ehmeth.co.uk.db.models.order.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, List<CartItemModel> cartItems);
}

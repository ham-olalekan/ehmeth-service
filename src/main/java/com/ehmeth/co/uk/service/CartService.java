package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.order.Cart;
import com.ehmeth.co.uk.db.models.order.CartItem;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CartService {
    Cart createCart(final String userId, Cart request);

    Optional<Cart> findByCartId( final String id);

    CartItem addCartItem(final String id,
                         final String productId,
                         int quantity,
                         CartItem cartItem);

    Map<Object, Object> fetchCartItemForCart(final int page,
                                            final int size,
                                            final String id);
}

package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.cart.Cart;
import com.ehmeth.co.uk.db.models.cart.CartItem;
import com.ehmeth.co.uk.db.models.cart.CartItemModel;
import com.ehmeth.co.uk.db.models.product.ProductPageModel;

import java.util.List;

public interface CartService {
    List<CartItemModel> createCartItemsForUser(final User user,List<ProductPageModel> requests);
    Cart getUserCart(String userId);
    void deleteAll(List<CartItem> cartItems);
}

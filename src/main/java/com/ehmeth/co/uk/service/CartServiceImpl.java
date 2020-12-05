package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.cart.Cart;
import com.ehmeth.co.uk.db.models.cart.CartItem;
import com.ehmeth.co.uk.db.models.cart.CartItemModel;
import com.ehmeth.co.uk.db.models.product.ProductPageModel;
import com.ehmeth.co.uk.db.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CartServiceImpl implements CartService {

    private CartItemRepository cartItemRepository;
    private ProductService productService;

    @Autowired
    public CartServiceImpl(CartItemRepository cartItemRepository,
                           ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    @Override
    public List<CartItemModel> createCartItemsForUser(final User user,
                                                      List<ProductPageModel> requests) {

        List<CartItem> cartItems = new ArrayList<>();
        for (ProductPageModel p : requests) {
            CartItem cartItem = new CartItem(
                    user.getId(),
                    p.getId(),
                    p.getQty(),
                    new Date(),
                    new Date()
            );
            cartItems.add(cartItem);
        }
        return cartItemsToCartItemModel(cartItemRepository.saveAll(cartItems));
    }

    /**
     * creates a CartItemModel from a ProductPageModel
     *
     * @param productPageModel
     * @return
     */
    private CartItemModel cartItemToModel(ProductPageModel productPageModel) {
        return new CartItemModel(
                productPageModel.getId(),
                productPageModel.getEnglishName(),
                productPageModel.getImageUrl(),
                productPageModel.getPricingType(),
                productPageModel.getAmount(),
                productPageModel.getStoreName(),
                productPageModel.getQty()
        );
    }

    /**
     * save a new CartItem to the repo
     *
     * @param cart
     * @return
     */
    private CartItem save(CartItem cart) {
        return cartItemRepository.save(cart);
    }

    /**
     * converts a list of cartItems to CartItemModel
     *
     * @param items
     * @return
     */
    private List<CartItemModel> cartItemsToCartItemModel(List<CartItem> items) {
        List<CartItemModel> cartItemModels = new ArrayList<>();
        items.forEach(cartItem -> {
            cartItemModels.add(cartItemToModel(cartItem));
        });
        return cartItemModels;
    }

    /**
     * converts a single CartItem to CartItemModel
     *
     * @param cartItem
     * @return
     */
    public CartItemModel cartItemToModel(CartItem cartItem) {
        ProductPageModel productPageModel = productService.fetchProductModel(cartItem.getProductId());
        return new CartItemModel(productPageModel.getId(),
                productPageModel.getEnglishName(),
                productPageModel.getImageUrl(),
                productPageModel.getPricingType(),
                productPageModel.getAmount(),
                productPageModel.getStoreName(),
                cartItem.getQuantity());
    }

    @Override
    public Cart getUserCart(String userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        return new Cart(cartItemsToCartItemModel(cartItems));
    }

    @Override
    public void deleteAll(List<CartItem> cartItems) {
        cartItemRepository.deleteAll(cartItems);
    }
}

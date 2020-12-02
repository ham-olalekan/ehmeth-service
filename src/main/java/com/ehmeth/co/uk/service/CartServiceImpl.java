package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.db.models.order.Cart;
import com.ehmeth.co.uk.db.models.order.CartItem;
import com.ehmeth.co.uk.db.models.product.Product;
import com.ehmeth.co.uk.db.repository.CartItemRepository;
import com.ehmeth.co.uk.db.repository.CartRepository;
import com.ehmeth.co.uk.db.repository.ProductRepository;
import com.ehmeth.co.uk.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CartServiceImpl implements  CartService{
    private CartRepository cartRepository;
    private CartService cartService;
    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart createCart(String userId, Cart request) {

        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Invalid user id [" + userId + "]"));

        request.setCreatedAt(new Date());
        request.setUpdatedAt(new Date());
        request.setUserId(userId);
        return cartRepository.save(request);
    }

    @Override
    public Optional<Cart> findByCartId(String id) {
        return cartRepository.findByCartId(id);
    }

    @Override
    public CartItem addCartItem(String id, String productId, int quantity, CartItem cartItem) {
        Cart cart = cartService
                .findByCartId(id)
                .orElseThrow(() -> new NotFoundException("Invalid cart id [" + id + "]"));

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException("Invalid product id [" + productId + "]"));

        if(String.valueOf(quantity) == "undefined" || String.valueOf(quantity) == null )
            quantity = 1;


        cartItem.setProductId(product.getId());
        cartItem.setQuantity(cartItem.getQuantity());
        cartItem.setCreatedAt(new Date());
        cartItem.setUpdatedAt(new Date());
        cartItem.setCartId(cart.getId());
        return cartItemRepository.save(cartItem);
    }

    
    @Override
    public Map<Object, Object> fetchCartItemForCart(final int page,
                                                    final int size,
                                                    final String id) {
        Cart cart = cartRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Invalid cart id [" + id + "]"));
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<CartItem> cartItemPage = cartItemRepository.findByCartId(id, pageRequest);

        Map<Object, Object> cartPage = new HashMap<>();

        cartPage.put("totalProducts", cartItemPage.getTotalElements());
        cartPage.put("totalProductsOnPage", cartItemPage.getNumberOfElements());
        cartPage.put("totalPages", cartItemPage.getTotalPages());
        cartPage.put("products", cartItemPage.getContent());

        double total = 0;

        for (CartItem item : cartItemPage.getContent()){
            total += item.getSubtotal();
            System.out.println(total);
        }

        cart.setTotal(total);
        cartRepository.save(cart);

        return cartPage;
    }

}

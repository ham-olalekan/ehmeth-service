package com.ehmeth.co.uk.db.models.cart;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Cart {

    private BigDecimal total;

    List<CartItemModel> cartItems;

    @JsonCreator
    public Cart(List<CartItemModel> cartItems) {
        this.cartItems = cartItems;
        this.total = getCartTotal();
    }

    private BigDecimal getCartTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for(CartItemModel cartItemModel: cartItems){
            total = total.add(cartItemModel.getSubTotal());
        }
        System.out.println(total);
        return total;
    }
}

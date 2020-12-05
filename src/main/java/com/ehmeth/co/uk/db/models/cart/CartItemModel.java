package com.ehmeth.co.uk.db.models.cart;


import com.ehmeth.co.uk.db.models.product.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartItemModel {
    private String productName;
    private String productId;
    private String productImage;
    private String storeName;
    private BigDecimal unitPrice;
    private String pricingType;
    private int quantity;
    private BigDecimal subTotal;

    @JsonCreator
    public CartItemModel(String productId,
                         String productName,
                         String productImage,
                         String pricingType,
                         BigDecimal unitPrice,
                         String storeName,
                         int quantity) {
        this.productName = productName;
        this.productImage = productImage;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.storeName = storeName;
        this.pricingType = pricingType;
        this.productId = productId;
        this.subTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

}

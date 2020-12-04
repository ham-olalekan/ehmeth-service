package com.ehmeth.co.uk.db.models.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductPageModel {
    private String id;
    private String storeName;
    private String localName;
    private String englishName;
    private String pricingType;
    private String imageUrl;
    private BigDecimal amount;
    private int qty;
    private int totalProductQuantity;

    public ProductPageModel(String id,
                            String storeName,
                            String localName,
                            String englishName,
                            String pricingType,
                            String imageUrl,
                            BigDecimal amount,
                            int qty,
                            int totalProductQuantity) {
        this.id = id;
        this.storeName = storeName;
        this.localName = localName;
        this.englishName = englishName;
        this.pricingType = pricingType;
        this.imageUrl = imageUrl;
        this.amount = amount;
        this.qty = qty;
        this.totalProductQuantity = totalProductQuantity;
    }
}

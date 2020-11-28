package com.ehmeth.co.uk.db.models.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreationRequest {
    private String englishName;
    private String localName;
    private ProductPricingType pricingType;
    private int quantity;
    private String description;
}

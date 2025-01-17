package com.ehmeth.co.uk.db.models.product;

import lombok.Getter;

@Getter
public enum ProductPricingType {
    PER_KILOGRAM("per kilogram"),
    PER_GRAM("per gram"),
    COUNT("each");

    private String prettyName;

    ProductPricingType(String prettyName) {
        this.prettyName = prettyName;
    }
}

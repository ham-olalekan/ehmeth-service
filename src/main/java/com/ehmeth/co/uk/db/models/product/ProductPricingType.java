package com.ehmeth.co.uk.db.models.product;

public enum ProductPricingType {
    PER_KILOGRAM("per kilogram"),
    PER_GRAM("per gram"),
    COUNT("per count");

    private String prettyName;

    ProductPricingType(String prettyName) {
        this.prettyName = prettyName;
    }
}

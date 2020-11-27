package com.ehmeth.co.uk.db.models.product;

public enum ProductType {
    ORGANIC("Organic"),
    INORGANIC("Inorganic");

    private String prettyName;

    ProductType(String prettyName) {
        this.prettyName = prettyName;
    }
}

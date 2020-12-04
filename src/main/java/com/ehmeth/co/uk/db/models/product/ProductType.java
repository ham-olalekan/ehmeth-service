package com.ehmeth.co.uk.db.models.product;

import lombok.Getter;

@Getter
public enum ProductType {
    ORGANIC("Organic"),
    INORGANIC("Inorganic");

    private String prettyName;

    ProductType(String prettyName) {
        this.prettyName = prettyName;
    }
}

package com.ehmeth.co.uk.db.models;

import lombok.Getter;

@Getter
public enum Currency {

    POUND("GBP");
    private String code;

    Currency(String code) {
        this.code = code;
    }
}

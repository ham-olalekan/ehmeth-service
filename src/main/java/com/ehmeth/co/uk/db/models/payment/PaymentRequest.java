package com.ehmeth.co.uk.db.models.payment;

import lombok.Data;

@Data
public class PaymentRequest {
    public enum Currency {
        EUR, USD;
    }
    private String description;
    private int amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;
}

package com.ehmeth.co.uk.db.models.payment;

import lombok.Data;

@Data
public class PaymentInfo {
    private final long amount;
    private final String receiptEmail;
    private final String source;
    private final String currency;
    private final String description;
    private final String orderId;
    private final String userEmail;
    private final int page;
}

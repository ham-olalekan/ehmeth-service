package com.ehmeth.co.uk.vendor.request;

import lombok.Data;

@Data
public class ChargeRequest {
    private int amount;
    private String stripeToken;
}

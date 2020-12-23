package com.ehmeth.co.uk.vendor.response;

import lombok.Data;

@Data
public class StripePaymentResponse {
    private boolean paymentSuccessful;
    private boolean message;
}

package com.ehmeth.co.uk.service;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Payout;
import com.stripe.model.PayoutCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl {

    private final long amount;
    private final String receiptEmail;
    private final String source;
    private final String currency;
    private final String description;
    private final String orderId;
    private final String userEmail;
    private final int page;

    public PaymentServiceImpl(long amount,
                              String receiptEmail,
                              String source,
                              String currency,
                              String description,
                              String orderId,
                              String userEmail,
                              int page) {
        this.amount = amount;
        this.receiptEmail = receiptEmail;
        this.source = source;
        this.currency = currency;
        this.description = description;
        this.orderId = orderId;
        this.userEmail = userEmail;
        this.page = page;
    }

    private Map<String, Object> getCharge() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", this.amount);
        params.put("currency", this.currency);
        // source should obtained with Stripe.js
        params.put("source", this.source);
        params.put("description", this.description
        );
        params.put("receipt_email",this.receiptEmail);

        Map<String, String> initialMetadata = new HashMap<>();
        initialMetadata.put("order_id", orderId);
        initialMetadata.put("receipt_email", userEmail);

        params.put("metadata", initialMetadata);

        return params;
    }

    private Map<String, Object> returnCharge () {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", this.page);
        return params;
    }

    private Map<String, Object> cretePayout() {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", this.amount);
        params.put("currency", this.currency);
        return params;
    }

    public Charge chargeNewCard() throws StripeException {
        Charge charge = Charge.create(getCharge());
        return charge;
    }

    public PayoutCollection fetchAllCharges() throws StripeException {
        PayoutCollection payout = Payout.list(returnCharge());
        return payout;
    }

    public Payout makePayout() throws StripeException {
        Payout payout = Payout.create(cretePayout());
        return payout;
    }


}

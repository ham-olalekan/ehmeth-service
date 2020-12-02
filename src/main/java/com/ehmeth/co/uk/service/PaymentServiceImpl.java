package com.ehmeth.co.uk.service;


import com.stripe.Stripe;
import com.stripe.model.Charge;
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

    public PaymentServiceImpl(long amount,
                              String receiptEmail,
                              String description,
                              String currency) {
        this.amount = amount;
        this.source = "tok_visa";
        this.currency = currency;
        this.description = description;
        this.receiptEmail = receiptEmail;
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
        return params;
    }

}

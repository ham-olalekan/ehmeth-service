package com.ehmeth.co.uk.service;


import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Payout;
import com.stripe.model.PayoutCollection;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements  PaymentService{

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

    private Map<String, Object> chargeACard() {
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

    private Map<String, Object> listAllPayout() {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", this.page);
        return params;
    }

    private Map<String, Object> createNewPayout() {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", this.amount);
        params.put("currency", this.currency);
        return params;
    }

    public Charge chargeNewCard() throws StripeException {
        Charge charge = Charge.create(chargeACard());
        return charge;
    }

    public PayoutCollection fetchAllCharges() throws StripeException {
        PayoutCollection payout = Payout.list(listAllPayout());
        return payout;
    }

    public Payout makePayout() throws StripeException {
        Payout payout = Payout.create(createNewPayout());
        return payout;
    }


    @Override
    public Charge getCharge(double amount,
                                         String currency,
                                         String source,
                                         String description,
                                         int orderId,
                                         String userEmail) throws StripeException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("currency", currency);
        // source should obtained with Stripe.js
        params.put("source", source);
        params.put("description", description
        );

        Map<String, String> initialMetadata = new HashMap<>();
        initialMetadata.put("order_id", Integer.toString(orderId));
        initialMetadata.put("receipt_email", userEmail);

        params.put("metadata", initialMetadata);

        Charge charge = Charge.create(params);

        return charge;

    }

    @Override
    public PayoutCollection cretePayout(double amount, String currency) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", this.amount);
        params.put("currency", this.currency);
        
        PayoutCollection payout = Payout.create(params);
        return payout;

    }

    @Override
    public Payout returnCharge(String page) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", this.page);
        Payout payout = Payout.list(params);
        return payout;
    }
}

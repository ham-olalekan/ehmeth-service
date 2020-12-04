package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.db.models.payment.PaymentInfo;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Payout;
import com.stripe.model.PayoutCollection;

import java.util.HashMap;
import java.util.Map;

public interface PaymentService {

    public Charge getCharge(final double amount,
                            final String currency,
                            final String source,
                            final String description,
                            final int orderId,
                            final String userEmail) throws StripeException;

    public Payout returnCharge (final String page) throws StripeException;

    public PayoutCollection cretePayout(final double amount,
                                        final String currency) throws StripeException;

}

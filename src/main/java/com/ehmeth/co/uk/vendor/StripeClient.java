package com.ehmeth.co.uk.vendor;

import com.ehmeth.co.uk.db.models.Currency;
import com.ehmeth.co.uk.db.models.payment.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.RateLimitException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class StripeClient {

    private String secretKey = "sk_test_51HtfKGHoOjXD7cGZ2decZ4kc5l9NN4klwUOwvLdbpyyCZ90GEbSFZj5n7qsdc9i7oBhupWkQd3lGdZNPxkWeqfLJ00c3rv10qe";

    private RequestOptions requestOptions;

    @Autowired
    public StripeClient() {
         requestOptions = RequestOptions.builder()
                .setApiKey(secretKey)
                .build();
    }

    public Charge callStripeForPayment(PaymentRequest request) {
        try {
            Map<String, Object> params = new HashMap<>();
            final String source = getChargeInfo(request.getToken()).getId();
            params.put("amount", request.getAmount());
            params.put("currency", Currency.POUND.getCode());
            params.put("source", source);
            params.put(
                    "description",
                    "Ehmeth Order payment"
            );
            Charge charge = Charge.create(params);
            if (charge.getPaid()) {
                log.info("payment successful: {}", charge.toString());
                return charge;
            }
        } catch (CardException e) {
            // Since it's a decline, CardException will be caught
            System.out.println("Status is: " + e.getCode());
            System.out.println("Message is: " + e.getMessage());
            return null;
        } catch (RateLimitException e) {
            // Too many requests made to the API too quickly
            return null;
        } catch (InvalidRequestException e) {
            // Invalid parameters were supplied to Stripe's API
            return null;
        } catch (AuthenticationException e) {
            // Authentication with Stripe's API failed
            // (maybe you changed API keys recently)
            return null;
        } catch (StripeException e) {
            // Display a very generic error to the user, and maybe send
            // yourself an email
            return null;
        } catch (Exception e) {
            // Something else happened, completely unrelated to Stripe
            return null;
        }
        return null;
    }

    private Charge getChargeInfo(final String paymentToken) throws StripeException {
        try {
            Charge info = Charge.retrieve(paymentToken,requestOptions);
            log.info("Info : {}", info);
            return info;
        } catch (StripeException ex) {
            log.info("stripe request failed Error: {}", ex.getMessage());
            throw ex;
        }
    }
}

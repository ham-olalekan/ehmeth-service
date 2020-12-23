package com.ehmeth.co.uk.controller.payment;

import com.ehmeth.co.uk.controller.resources.ApiResponseJson;
import com.ehmeth.co.uk.db.models.payment.PaymentRequest;
import com.ehmeth.co.uk.vendor.StripeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("payment")
public class PaymentController {

    private StripeClient stripeClient;

    @Autowired
    public PaymentController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    @PostMapping
    public ResponseEntity<ApiResponseJson<?>> handlePaymentWithStripe(@RequestBody PaymentRequest paymentRequest) {
        boolean valid = paymentRequest.isValid();
        if (!valid) {
            log.info("Invalid request: {}", paymentRequest);
            return new ResponseEntity(new ApiResponseJson(false, "Invalid payment request", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new ApiResponseJson(true, "successful", stripeClient.callStripeForPayment(paymentRequest)), HttpStatus.OK);
    }
}

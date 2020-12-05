package com.ehmeth.co.uk.events;

import com.ehmeth.co.uk.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCreationEventListener {

    private CartService cartService;

    @Autowired
    public OrderCreationEventListener(CartService cartService) {
        this.cartService = cartService;
    }

    @EventListener
    private void handleOrderCreation(OrderCreationEvent event) {
        log.info("Order creation successful:::: order [{}]", event.getOrder());
    }
}

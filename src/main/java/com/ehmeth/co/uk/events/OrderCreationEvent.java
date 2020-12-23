package com.ehmeth.co.uk.events;

import com.ehmeth.co.uk.db.models.order.Order;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderCreationEvent extends ApplicationEvent {
    private Order order;

    public OrderCreationEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }
}

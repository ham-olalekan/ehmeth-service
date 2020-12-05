package com.ehmeth.co.uk.db.models.order;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "order")
public class Order implements Serializable {
    private static final long serialVersionUID = -1907829101121517800L;

    @Id
    private String id;

    @Indexed
    private String buyerId;

    @Indexed
    private String orderId;

    private BigDecimal processingFee;

    private BigDecimal shippingFee;

    private BigDecimal vat;

    private List<OrderItem> orderItems;

    private Date createdAt;

    private Date updatedAt;

    public Order() {
        this.shippingFee = BigDecimal.ZERO;
        this.vat = BigDecimal.ZERO;
        this.processingFee = BigDecimal.ZERO;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    //constructor overload
    public Order(final String buyerId) {
        this.shippingFee = BigDecimal.ZERO;
        this.vat = BigDecimal.ZERO;
        this.processingFee = BigDecimal.ZERO;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.buyerId = buyerId;
    }
}

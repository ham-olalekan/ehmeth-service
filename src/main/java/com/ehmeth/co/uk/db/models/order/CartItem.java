package com.ehmeth.co.uk.db.models.order;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "cartItem")
public class CartItem {

    @Id
    private String id;

    @Indexed
    private String cartId;

    @Indexed
    private String productId;

    private int quantity;

    private double unitPrice;

    @Transient
    private double subtotal = this.unitPrice * this.quantity;

    private Date createdAt;

    private Date updatedAt;
}

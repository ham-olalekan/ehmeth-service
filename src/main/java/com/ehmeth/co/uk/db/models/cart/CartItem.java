package com.ehmeth.co.uk.db.models.cart;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Document(collection = "cart_item")
public class CartItem implements Serializable {
    private static long serialVersionUID = -2532533147355806064L;

    @Id
    private String id;

    @Indexed
    private String userId;

    private String productId;

    private int quantity;

    private Date createdAt;

    private Date updatedAt;

    private BigDecimal subTotal;

    public CartItem(String userId,
                    String productId,
                    int quantity,
                    Date createdAt,
                    Date updatedAt) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

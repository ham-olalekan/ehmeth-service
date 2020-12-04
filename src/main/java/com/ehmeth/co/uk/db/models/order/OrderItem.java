package com.ehmeth.co.uk.db.models.order;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Document(collection = "order_item")
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 7106912005301638966L;

    @Id
    private String id;

    @Indexed
    private String orderId;

    @Indexed
    private String storeId;//the id of the store the produt belongs to

    private OrderItemStatus status;

    private String productName;

    private String productImage;

    private String storeName;

    private BigDecimal unitPrice;

    private String pricingType;

    private int quantity;

    private BigDecimal subTotal;

    private Date createdAt;

    private Date updatedAt;
}

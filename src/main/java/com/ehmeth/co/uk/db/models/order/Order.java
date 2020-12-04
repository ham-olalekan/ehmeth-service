package com.ehmeth.co.uk.db.models.order;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "order")
public class Order implements Serializable {
    private static final long serialVersionUID = -1907829101121517800L;

    @Id
    private String id;

    @Indexed
    private String userId;

    private List<OrderItem> orderItems;

}

package com.ehmeth.co.uk.db.models.order;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data

@Document(collection = "Cart")
public class Cart {

    @Id
    private String id;

    @Indexed
    private String userId;

    private double total;

    private Date createdAt;

    private Date updatedAt;

}

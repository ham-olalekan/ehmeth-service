package com.ehmeth.co.uk.db.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = "product-category")
public class ProductCategory implements Serializable {
    private static final long serialVersionUID = 7327001563268937860L;

    private String id;

    private String name;

    private String description;

    private Date createdAt;

    private Date updatedAt;
}

package com.ehmeth.co.uk.db.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = "product-sub-category")
public class ProductSubCategory implements Serializable {

    private static final long serialVersionUID = 7327001563268937860L;

    @Id
    private String id;

    private String name;

    private String description;

    @Indexed
    private String categoryId;

    private Date createdAt;

    private Date updatedAt;
}

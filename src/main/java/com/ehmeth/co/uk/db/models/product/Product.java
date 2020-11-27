package com.ehmeth.co.uk.db.models.product;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "product")
public class Product {

    private static long serialVersionUID = 1257489610999361419L;

    @Id
    private String id;

    private String englishName;

    private String localName;

    private String description;

    private int minimumPurchase;

    private int quantity;

    private List<String> productImages;

    private ProductType productType;

}

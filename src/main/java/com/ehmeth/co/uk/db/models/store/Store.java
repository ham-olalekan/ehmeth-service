package com.ehmeth.co.uk.db.models.store;

import com.ehmeth.co.uk.db.models.Address;
import com.ehmeth.co.uk.db.models.product.Product;
import com.ehmeth.co.uk.db.models.User.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@Document(collection = "Store")
public class Store implements Serializable {

    private static long serialVersionUID = 615329941219538710L;

    @Id
    private String id;

    private String storeName;

    @Indexed
    private String storeId;

    private String registrationName;

    @Indexed
    private String registrationNumber;

    private String registrationImageUrl;

    private String mobileNumber;

    private StoreStatus storeStatus;

    private AccountType accountType;

    private String addressId;

    private String landLine;

    private Date createdAt;

    private Date updatedAt;

    public StorePublicInfo getPublicInfo(){
        return StorePublicInfo
                .builder()
                .storeId(this.id)
                .storeName(this.storeName)
                .build();
    }
}

package com.ehmeth.co.uk.db.models.store;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StorePublicInfo {
    private String storeName;
    private String storeId;
    private String storeNumber;

    public StorePublicInfo(String storeName,
                           String storeId,
                           String storeNumber) {
        this.storeName = storeName;
        this.storeId = storeId;
        this.storeNumber = storeNumber;
    }

    public StorePublicInfo() {
    }
}

package com.ehmeth.co.uk.db.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Builder
@Document
public class Address implements Serializable {
    private static long serialVersionUID = -9073689127164084064L;

    @Id
    private String id;

    private String country;

    private String borough;

    private String postCode;

    private String address;

}

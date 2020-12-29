package com.ehmeth.co.uk.db.models;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedData<T> {
    private int totalPages;
    private long totalNumberOfElements;
    private List<T> data;

    public PaginatedData(int totalPages,
                         long totalNumberOfElements,
                         List<T> data) {
        this.totalPages = totalPages;
        this.totalNumberOfElements = totalNumberOfElements;
        this.data = data;
    }
}

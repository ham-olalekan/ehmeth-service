package com.ehmeth.co.uk.controller.resources;

import lombok.Data;

@Data
public class ApiResponseJson<T> {
    private boolean status;
    private String message;
    private T data;
    public ApiResponseJson(boolean status,
                           String message,
                           T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}

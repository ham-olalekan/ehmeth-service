package com.ehmeth.co.uk.db.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class AuthToken implements Serializable {

    private static final long serialVersionUID = 1187654324562734L;

    private String jwt;
    private Date expires;
    private String role;

    public AuthToken(String jwt, Date expires, String role) {
        this.jwt = jwt;
        this.expires = expires;
        this.role = role;
    }

    public AuthToken() {
    }

    public String getJwt() {
        return jwt;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getExpires() {
        return expires;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthToken{");
        sb.append("jwt='").append(jwt).append('\'');
        sb.append(", expires=").append(expires);
        sb.append(", role='").append(role).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

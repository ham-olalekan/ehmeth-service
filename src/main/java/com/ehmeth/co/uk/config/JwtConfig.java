package com.ehmeth.co.uk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.signing.key}")
    private String jwtSigningKey;

    @Value("${jwt.admin.password}")
    private String adminPassword;

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
}

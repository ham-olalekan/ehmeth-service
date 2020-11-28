package com.ehmeth.co.uk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.signing.key}")
    private String jwtSigningKey;

    @Value("${jwt.admin.password}")
    private String adminPassword;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter(jwtSigningKey));
        registrationBean.addUrlPatterns("*");
        return registrationBean;
    }
    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
}

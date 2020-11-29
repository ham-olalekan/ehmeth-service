package com.ehmeth.co.uk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() //allow cors pre-flight request without authentication
                .antMatchers("/store/sign-up").permitAll()
                .antMatchers("/admin/store/*/account").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/user/buyer/sign-up").permitAll()
                .antMatchers("/category/create").permitAll()
                .antMatchers("/category/sub-category/*/create").permitAll()
                .antMatchers(HttpMethod.POST,"/products/add-new").permitAll()
                .antMatchers(HttpMethod.GET,"/products/*/products").permitAll()
                .antMatchers("/category/categories").permitAll()
                .antMatchers("/category/*/sub-categories").permitAll()
                .antMatchers("/products/all").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
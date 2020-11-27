package com.ehmeth.co.uk.service;

import com.ehmeth.co.uk.Exceptions.BadRequestException;
import com.ehmeth.co.uk.Exceptions.NotFoundException;
import com.ehmeth.co.uk.config.JwtConfig;
import com.ehmeth.co.uk.controller.resources.LoginRequest;
import com.ehmeth.co.uk.db.models.AuthToken;
import com.ehmeth.co.uk.db.models.User.User;
import com.ehmeth.co.uk.db.models.store.Store;
import com.ehmeth.co.uk.util.DateUtil;
import com.ehmeth.co.uk.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserService userService;

    private StoreService storeService;

    private JwtConfig jwtConfig;

    @Override
    public Map<Object, Object> loginUser(LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!SecurityUtil.passwordMatches(loginRequest.getPassword(), user.getHashedPassword())) {
            log.info("Invalid password provided for request: {}", loginRequest);
            throw new BadRequestException("Invalid  email or password");
        }

        Store store = storeService.findByStoreId(user.getStoreId()).orElseThrow(() -> new NotFoundException("User does not belong to a store"));
        Map<Object, Object> loginResponse = new HashMap<>();
        if (!user.getRole().isGlobalAdmin()) {
            AuthToken token = createAuthTokenForUser(user);
            loginResponse.put("authToken", token);
            loginResponse.put("profile", user.getPublicProfile());
            loginResponse.put("store", store.getPublicInfo());
        }
        return loginResponse;
    }

    private AuthToken createAuthTokenForUser(final User user) {
        Claims claims = Jwts.claims();
        String role = user.getRole().name();
        claims.put("roles", role);
        Date creationDate = Calendar.getInstance().getTime();
        //expires after a day
        Date expiry = DateUtil.addDaysToDate(creationDate, 1);
        final String jwtToken = createJwtToken(user.getId(), creationDate, expiry,
                claims);
        return new AuthToken(jwtToken, expiry, role);
    }

    private String createJwtToken(final String subject, final Date issuedAt, final Date expiry,
                                  final Claims claims) {
        claims.setIssuedAt(issuedAt);
        claims.setSubject(subject);
        claims.setExpiration(expiry);
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256,
                jwtConfig.getJwtSigningKey()).compact();
    }
}

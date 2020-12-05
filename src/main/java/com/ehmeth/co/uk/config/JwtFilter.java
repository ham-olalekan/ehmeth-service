package com.ehmeth.co.uk.config;

import com.ehmeth.co.uk.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@Slf4j
public class JwtFilter extends GenericFilterBean {

    private final String jwtSigningKey;

    private static final List<AntPathRequestMatcher> authPathMatchers = Collections.unmodifiableList(
            Arrays.asList(new AntPathRequestMatcher("/admin/store/*/account"),
                    new AntPathRequestMatcher("/products/add-new"),
                    new AntPathRequestMatcher("/products/*/products"),
                    new AntPathRequestMatcher("/cart"),
                    new AntPathRequestMatcher("/order"))

    );

    public JwtFilter(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("authorization");
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        log.info("Path identified is: [{}], header is: [{}]", path, authHeader);
        boolean allowedPath = true;
        for (AntPathRequestMatcher matcher : authPathMatchers) {
            if (matcher.matches(request)) {
                allowedPath = false;
                break;
            }
        }

        if ("OPTIONS".equals(request.getMethod()) || allowedPath) {
            response.setStatus(HttpServletResponse.SC_OK);

            chain.doFilter(req, res);
        } else {
            if (StringUtil.isBlank(authHeader)) {
                response.sendError(403, "Missing Authorization header");
                return;
            }

            if (!authHeader.startsWith("Bearer ")) {
                sendError(request, response, 403, "Missing bearer token");
                return;
            }

            final String token = authHeader.substring(7);

            try {
                final Claims claims = Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);
                if (claims.getExpiration().before(Calendar.getInstance().getTime())) {
                    log.info("Token expired at: [{}]. Token is: [{}]", claims.getExpiration(), token);
                    sendError(request, response, 403, "Declined XP");
                    return;
                }

                setSecurityContext(claims);

            } catch (final SignatureException e) {
                sendError(request, response, 403, "Declined SG");
                return;
            } catch (Exception e) {
                sendError(request, response, 403, e.getMessage());
                return;
            }

            chain.doFilter(req, res);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void sendError(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final int statusCode,
                           final String message) {
        JSONObject object = new JSONObject();
        object.put("timestamp", Math.round(new Date().getTime() / 1000));
        object.put("status", statusCode);

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setStatus(statusCode);

        switch (statusCode) {
            case HttpServletResponse.SC_FORBIDDEN:
                object.put("error", "Forbidden");
                break;

            case HttpServletResponse.SC_BAD_REQUEST:
                object.put("error", "Bad Request");
                break;

            case HttpServletResponse.SC_NOT_FOUND:
                object.put("error", "Not Found");
                break;

            case HttpServletResponse.SC_UNAUTHORIZED:
                object.put("error", "Unauthorised");
                break;

            default:
                object.put("error", "Error, check status code for more information");
                break;
        }

        object.put("message", message);
        object.put(
                "path",
                request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", ""));

        try {
            object.write(response.getWriter());
        } catch (IOException e) {
            log.error("Failed to send JWT error back to client", e);
        }
    }

    /*
     * Added details of logged in user (via JWT) to security context so the identity of the current user
     * can be gotten from any part of the application e.g. via the Principal object
     * reference: https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/technical-overview.html#tech-intro-sec-context-persistence
     */
    private void setSecurityContext(final Claims claims) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + claims.getOrDefault("role", "AGENT"));
        authorities.add(authority);

        Authentication auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), "******", authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}

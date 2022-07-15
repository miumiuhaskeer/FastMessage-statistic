package com.miumiuhaskeer.fastmessage.statistic.util;

import com.miumiuhaskeer.fastmessage.statistic.properties.config.JWTokenProp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Class copied from FastMessage project
 */
@Component
@RequiredArgsConstructor
public class JWTokenUtil {

    public static final String BEARER_PREFIX = "Bearer ";

    private final JWTokenProp jwTokenProp;

    /**
     * Get email from token. You can use validateToken method to prevent any Runtime exceptions
     *
     * @param token jwt string token
     * @return user email
     * @throws UnsupportedJwtException if the token argument does not represent an Claims JWS
     * @throws MalformedJwtException if the token string is not a valid JWS
     * @throws SignatureException if the token JWS signature validation fails
     * @throws ExpiredJwtException if the specified JWT is a Claims JWT and the Claims has an expiration time
     *                             before the time this method is invoked.
     * @throws IllegalArgumentException if the token string is null or empty or only whitespace
     * @see JWTokenUtil#validateToken(String)
     */
    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getBody().getSubject();
    }

    /**
     * Return true if token is valid or false if not. If method getClaimsFromToken throw any exception,
     * it means that token is not valid
     *
     * @param token jwt string token
     * @return true - token is valid or
     *         false - token is not valid
     * @see JWTokenUtil#getClaimsFromToken(String)
     */
    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean headerIsToken(String header) {
        return header != null && header.startsWith(BEARER_PREFIX);
    }

    /**
     * Get token from header by removing bearer prefix
     *
     * @param header string bearer token
     * @return token without bearer prefix
     * @throws IndexOutOfBoundsException if bearer prefix is not in header, check this by headerIsToken method
     * @see JWTokenUtil#headerIsToken(String)
     */
    public String getTokenFromHeader(String header) {
        return header.substring(BEARER_PREFIX.length());
    }

    /**
     * Get info from token
     *
     * @param token jwt string token
     * @return info from token
     * @throws UnsupportedJwtException if the token argument does not represent an Claims JWS
     * @throws MalformedJwtException if the token string is not a valid JWS
     * @throws SignatureException if the token JWS signature validation fails
     * @throws ExpiredJwtException if the specified JWT is a Claims JWT and the Claims has an expiration time
     *                             before the time this method is invoked.
     * @throws IllegalArgumentException if the token string is null or empty or only whitespace
     */
    private Jws<Claims> getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwTokenProp.getFmsSecret()).parseClaimsJws(token);
    }
}


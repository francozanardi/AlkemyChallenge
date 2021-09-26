package com.alkemy.challenge.security.token.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtils implements TokenUtils {

    private static final int JWT_EXPIRES_TIME_IN_MILLIS =  14 * 24 * 60 * 60 * 1000; // 14 days
    private static final String JWT_ISSUER = "alkemy.challenge";
    private static final byte[] ALGORITHM_SECRET_KEY = "secret".getBytes();

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenUtils(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public String getTokenFromUser(UserDetails user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(getExpiresDate())
                .withIssuer(JWT_ISSUER)
                .sign(getAlgorithm());

    }

    private Algorithm getAlgorithm(){
        return Algorithm.HMAC256(ALGORITHM_SECRET_KEY);
    }

    private Date getExpiresDate(){
        return new Date(System.currentTimeMillis() + JWT_EXPIRES_TIME_IN_MILLIS);
    }

    @Override
    @Nullable
    public UserDetails getUserFromToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(getAlgorithm()).build();
        DecodedJWT decodedJWT;
        try {
            decodedJWT = jwtVerifier.verify(token);
            return userDetailsService.loadUserByUsername(decodedJWT.getSubject());
        } catch (JWTVerificationException | UsernameNotFoundException exception) {
            return null;
        }
    }
}

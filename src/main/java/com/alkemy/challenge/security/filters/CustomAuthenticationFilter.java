package com.alkemy.challenge.security.filters;

import com.alkemy.challenge.dto.LoginRequest;
import com.alkemy.challenge.security.token.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;


    public CustomAuthenticationFilter(TokenUtils tokenUtils, AuthenticationManager authenticationManager) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequest loginRequest = getCredentialsFromRequest(request);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        return authenticationManager.authenticate(authentication);
    }

    private LoginRequest getCredentialsFromRequest(HttpServletRequest request){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException("Credentials not found");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        try {
            String token = tokenUtils.getTokenFromUser((UserDetails) authentication.getPrincipal());
            response.setHeader(AUTHORIZATION, token);
        } catch (Exception exception) {
            response.setStatus(INTERNAL_SERVER_ERROR.value());
        }

        loginAuthentication(authentication);
    }

    private void loginAuthentication(Authentication authentication){
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

}

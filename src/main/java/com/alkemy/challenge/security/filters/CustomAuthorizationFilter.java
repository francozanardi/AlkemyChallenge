package com.alkemy.challenge.security.filters;

import com.alkemy.challenge.security.ApplicationEndpoint;
import com.alkemy.challenge.security.token.utils.TokenUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final TokenUtils tokenUtils;

    public CustomAuthorizationFilter(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws ServletException, IOException {
        if(!ApplicationEndpoint.isPublicEndpoint(request)){
            Optional<String> tokenOptional = getTokenFromRequest(request);
            if(tokenOptional.isPresent()){
                tokenOptional.map(tokenUtils::getUserFromToken)
                        .ifPresentOrElse(this::loginUser, () -> response.setStatus(UNAUTHORIZED.value()));
            }
        }

        chain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request){
        String authorizationType = "Bearer ";
        String authorizationHeaderValue = request.getHeader(AUTHORIZATION);

        if(authorizationHeaderValue != null && authorizationHeaderValue.startsWith(authorizationType)){
            return Optional.of(authorizationHeaderValue.replaceFirst(authorizationType, ""));
        }

        return Optional.empty();
    }

    private void loginUser(UserDetails user){
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), Collections.emptyList());
        securityContext.setAuthentication(authentication);

        SecurityContextHolder.setContext(securityContext);
    }
}

package com.alkemy.challenge.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class ApplicationEndpoint {
    public static final String LOGIN_ENDPOINT = "/auth/login";
    public static final String REGISTER_ENDPOINT = "/auth/register";

    public static final RequestMatcher ALL_PUBLIC_ENDPOINTS_MATCHER = new OrRequestMatcher(
            new AntPathRequestMatcher(LOGIN_ENDPOINT),
            new AntPathRequestMatcher(REGISTER_ENDPOINT));

    public static final RequestMatcher ALL_PRIVATE_ENDPOINTS_MATCHER =
            new NegatedRequestMatcher(ALL_PUBLIC_ENDPOINTS_MATCHER);

    public static boolean isPublicEndpoint(HttpServletRequest request){
        return ALL_PUBLIC_ENDPOINTS_MATCHER.matches(request);
    }


}

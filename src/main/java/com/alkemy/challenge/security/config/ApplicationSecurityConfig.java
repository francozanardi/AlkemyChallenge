package com.alkemy.challenge.security.config;

import com.alkemy.challenge.security.ApplicationEndpoint;
import com.alkemy.challenge.security.entrypoint.BearerTokenAuthenticationEntryPoint;
import com.alkemy.challenge.security.filters.CustomAuthenticationFilter;
import com.alkemy.challenge.security.filters.CustomAuthorizationFilter;
import com.alkemy.challenge.security.token.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtils tokenUtils;

    @Autowired
    public ApplicationSecurityConfig(UserDetailsService userDetailsService,
                                     PasswordEncoder passwordEncoder, TokenUtils tokenUtils) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtils = tokenUtils;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(ApplicationEndpoint.ALL_PUBLIC_ENDPOINTS_MATCHER).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(initCustomAuthenticationFilter())
                .addFilterBefore(new CustomAuthorizationFilter(tokenUtils), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .defaultAuthenticationEntryPointFor(new BearerTokenAuthenticationEntryPoint(),
                        ApplicationEndpoint.ALL_PRIVATE_ENDPOINTS_MATCHER);
    }

    private CustomAuthenticationFilter initCustomAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(tokenUtils, authenticationManager());

        customAuthenticationFilter.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher(ApplicationEndpoint.LOGIN_ENDPOINT, "POST"));

        return customAuthenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}

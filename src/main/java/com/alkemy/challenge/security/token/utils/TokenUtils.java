package com.alkemy.challenge.security.token.utils;

import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenUtils {
    String getTokenFromUser(UserDetails user);
    @Nullable
    UserDetails getUserFromToken(String token);
}

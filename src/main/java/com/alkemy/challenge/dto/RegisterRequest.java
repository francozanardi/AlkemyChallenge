package com.alkemy.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class RegisterRequest {
    @Email
    @NotNull
    @Size(max = 100)
    private final String email;
    @NotNull
    @Size(min = 8, max = 100)
    private final String password;
}

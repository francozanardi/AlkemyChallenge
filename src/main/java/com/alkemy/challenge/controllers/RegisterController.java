package com.alkemy.challenge.controllers;

import com.alkemy.challenge.data.entities.User;
import com.alkemy.challenge.dto.RegisterRequest;
import com.alkemy.challenge.dto.validator.ValidatorUtils;
import com.alkemy.challenge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth/register")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest,
                                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ValidatorUtils.buildDefaultErrorMessage(bindingResult));
        }

        User userToSave = new User(registerRequest.getEmail(), registerRequest.getPassword());
        if(userService.register(userToSave)){
            return ResponseEntity.status(HttpStatus.CREATED).body("User created");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already taken");
    }



}

package com.alkemy.challenge.dto.validator;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class ValidatorUtils {
    public static Map<String, Object> buildDefaultErrorMessage(BindingResult bindingResult){
        Map<String, Object> defaultErrorMessage = new HashMap<>();
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(fieldError ->
                    defaultErrorMessage.put(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return defaultErrorMessage;
    }
}

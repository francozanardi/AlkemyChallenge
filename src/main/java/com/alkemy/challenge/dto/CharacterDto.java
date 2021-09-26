package com.alkemy.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;
import java.util.List;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterDto {
    @Null
    private final Long id;
    @Pattern(regexp = "^(?:[A-Za-z\\d+/]{4})*(?:[A-Za-z\\d+/]{3}=|[A-Za-z\\d+/]{2}==)?$", message = "The image must be base64 encoded")
    @Size(min = 1)
    private final String image;
    @Size(min = 1, max = 100)
    @NotNull
    private final String name;
    @Positive
    @NotNull
    private final Short age;
    @Positive
    @NotNull
    private final Float weight;
    private final String history;
    @Null
    private final List<MovieDto> movies;

}

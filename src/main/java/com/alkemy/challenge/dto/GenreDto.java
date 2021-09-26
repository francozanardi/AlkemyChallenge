package com.alkemy.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreDto {
    @Null
    private Long id;
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @Pattern(regexp = "^(?:[A-Za-z\\d+/]{4})*(?:[A-Za-z\\d+/]{3}=|[A-Za-z\\d+/]{2}==)?$", message = "The image must be base64 encoded")
    @Size(min = 1)
    private String image;
    @Null
    private List<MovieDto> movies;
}

package com.alkemy.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDto {
    @Null
    private final Long id;
    @Pattern(regexp = "^(?:[A-Za-z\\d+/]{4})*(?:[A-Za-z\\d+/]{3}=|[A-Za-z\\d+/]{2}==)?$", message = "The image must be base64 encoded")
    @Size(min = 1)
    private final String image;
    @NotNull
    @Size(min = 1, max = 100)
    private final String title;
    @NotNull
    private final Date releaseDate;
    @NotNull
    @Min(1)
    @Max(5)
    private final Float ratingScore;
    @Null
    private final List<CharacterDto> characters;
    @Null
    private final GenreDto genre;
    @NotNull
    private final Long genreId;
}

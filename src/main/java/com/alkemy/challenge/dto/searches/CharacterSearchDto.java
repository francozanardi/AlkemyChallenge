package com.alkemy.challenge.dto.searches;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class CharacterSearchDto {
    @Size(min = 1, max = 100)
    private final String name;
    @Positive
    private final Short age;
    @Positive
    private final Float weight;
    private final Long movies;

    public boolean isEmpty(){
        return name == null && age == null && weight == null && movies == null;
    }
}

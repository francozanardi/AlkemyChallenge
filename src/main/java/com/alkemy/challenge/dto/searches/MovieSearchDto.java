package com.alkemy.challenge.dto.searches;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort.Direction;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class MovieSearchDto {
    @Size(min = 1, max = 100)
    private final String name;
    private final Long genre;
    private Direction order;

    public boolean isEmpty(){
        return name == null && genre == null && order == null;
    }

    public Direction getOrder(){
        return order == null ? Direction.DESC : order;
    }
}

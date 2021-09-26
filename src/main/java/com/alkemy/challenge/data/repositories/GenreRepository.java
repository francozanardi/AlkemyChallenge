package com.alkemy.challenge.data.repositories;

import com.alkemy.challenge.data.entities.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}

package com.alkemy.challenge.services;

import com.alkemy.challenge.data.entities.Genre;

import java.util.Optional;

public interface GenreService {
    Iterable<Genre> getAllGenres();
    Optional<Genre> getGenreById(Long id);
    boolean save(Genre genre);
    boolean updateById(Long id, Genre newGenre);
    boolean deleteById(Long id);
}

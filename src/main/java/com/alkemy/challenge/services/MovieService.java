package com.alkemy.challenge.services;

import com.alkemy.challenge.data.entities.Movie;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> getAllMovies();
    Optional<Movie> getMovieById(Long id);
    boolean save(Movie movie);
    boolean updateById(Long id, Movie newMovie);
    boolean deleteById(Long id);
    List<Movie> searchMovies(String title, Long genreId, Direction order);
}

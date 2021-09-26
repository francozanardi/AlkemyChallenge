package com.alkemy.challenge.services;

import com.alkemy.challenge.data.entities.Movie;
import com.alkemy.challenge.data.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    @Transactional
    public boolean save(Movie movie) {
        if(movie.getId() == null || getMovieById(movie.getId()).isEmpty()){
            movieRepository.save(movie);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean updateById(Long id, Movie newMovie) {
        newMovie.setId(id);

        Optional<Movie> movieFoundOptional = getMovieById(id);
        if(movieFoundOptional.isPresent()){
            movieRepository.save(newMovie);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Movie> movieFoundOptional = getMovieById(id);
        if(movieFoundOptional.isPresent()){
            movieRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> searchMovies(String title, Long genreId, Direction order) {
        return movieRepository.searchMovies(title, genreId, Sort.by(order, "releaseDate"));
    }
}

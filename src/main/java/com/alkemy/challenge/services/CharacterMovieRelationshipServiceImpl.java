package com.alkemy.challenge.services;

import com.alkemy.challenge.data.entities.Character;
import com.alkemy.challenge.data.entities.Movie;
import com.alkemy.challenge.data.repositories.CharacterRepository;
import com.alkemy.challenge.data.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CharacterMovieRelationshipServiceImpl implements CharacterMovieRelationshipService {
    private final CharacterRepository characterRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public CharacterMovieRelationshipServiceImpl(CharacterRepository characterRepository, MovieRepository movieRepository) {
        this.characterRepository = characterRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    @Transactional
    public boolean addRelationship(Long characterId, Long movieId) {
        Optional<Character> characterOptional = characterRepository.findById(characterId);
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        if(characterOptional.isPresent() && movieOptional.isPresent()){
            characterOptional.get().getMovies().add(movieOptional.get());
            characterRepository.save(characterOptional.get());
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteRelationship(Long characterId, Long movieId) {
        Optional<Character> characterOptional = characterRepository.findById(characterId);
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        if(characterOptional.isPresent() && movieOptional.isPresent()){
            characterOptional.get().getMovies().remove(movieOptional.get());
            characterRepository.save(characterOptional.get());
            return true;
        }

        return false;
    }
}

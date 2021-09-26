package com.alkemy.challenge.services;

import com.alkemy.challenge.data.entities.Genre;
import com.alkemy.challenge.data.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    @Transactional
    public boolean save(Genre genre) {
        if(genre.getId() == null || getGenreById(genre.getId()).isEmpty()){
            genreRepository.save(genre);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean updateById(Long id, Genre newGenre) {
        newGenre.setId(id);

        Optional<Genre> genreFoundOptional = getGenreById(id);
        if(genreFoundOptional.isPresent()){
            genreRepository.save(newGenre);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Genre> genreFoundOptional = getGenreById(id);
        if(genreFoundOptional.isPresent()){
            genreRepository.deleteById(id);
            return true;
        }

        return false;
    }
}

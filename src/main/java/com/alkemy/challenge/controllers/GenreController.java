package com.alkemy.challenge.controllers;

import com.alkemy.challenge.data.entities.Genre;
import com.alkemy.challenge.dto.GenreDto;
import com.alkemy.challenge.dto.mapper.GenreMapper;
import com.alkemy.challenge.dto.validator.ValidatorUtils;
import com.alkemy.challenge.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreDto> listGenres(){
        List<GenreDto> genresFiltered = new ArrayList<>();
        for(Genre genre: genreService.getAllGenres()){
            genresFiltered.add(GenreMapper.getFilteredDto(genre));
        }

        return genresFiltered;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getOneGenre(@PathVariable Long id){
        return genreService.getGenreById(id)
                .map(GenreMapper::getFullDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createGenre(@Valid @RequestBody GenreDto genreDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ValidatorUtils.buildDefaultErrorMessage(bindingResult));
        }

        Genre genreToSave = GenreMapper.createGenreFromDto(genreDto);

        if(genreService.save(genreToSave)){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.badRequest().body("The genre already exists");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenre(@PathVariable Long id,
                                         @Valid @RequestBody GenreDto genreDto,
                                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ValidatorUtils.buildDefaultErrorMessage(bindingResult));
        }

        Genre genreToSave = GenreMapper.createGenreFromDto(genreDto);

        if(genreService.updateById(id, genreToSave)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.badRequest().body(String.format("There is no genre with id %d", id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long id){
        if(genreService.deleteById(id)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body(String.format("There is no genre with id %d", id));
    }
}

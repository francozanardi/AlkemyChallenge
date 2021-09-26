package com.alkemy.challenge.controllers;

import com.alkemy.challenge.data.entities.Genre;
import com.alkemy.challenge.data.entities.Movie;
import com.alkemy.challenge.dto.MovieDto;
import com.alkemy.challenge.dto.mapper.MovieMapper;
import com.alkemy.challenge.dto.searches.MovieSearchDto;
import com.alkemy.challenge.dto.validator.ValidatorUtils;
import com.alkemy.challenge.services.GenreService;
import com.alkemy.challenge.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final GenreService genreService;

    @Autowired
    public MovieController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }


    @GetMapping
    public ResponseEntity<?> listMovies(@Valid MovieSearchDto movieSearchDto,
                                     BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ValidatorUtils.buildDefaultErrorMessage(bindingResult));
        }

        if(movieSearchDto.isEmpty()){
            return ResponseEntity.ok(listAllMovies());
        }

        return ResponseEntity.ok(searchMovies(movieSearchDto));

    }

    private List<MovieDto> listAllMovies(){
        return movieService.getAllMovies()
                .stream()
                .map(MovieMapper::getFilteredDto)
                .collect(Collectors.toList());
    }

    private List<MovieDto> searchMovies(MovieSearchDto movieSearchDto){
        return movieService
                .searchMovies(movieSearchDto.getName(),
                        movieSearchDto.getGenre(),
                        movieSearchDto.getOrder())
                .stream()
                .map(MovieMapper::getFilteredDto)
                .collect(Collectors.toList());

    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getOneMovie(@PathVariable Long id){
        return movieService.getMovieById(id)
                .map(MovieMapper::getFullDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieDto movieDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ValidatorUtils.buildDefaultErrorMessage(bindingResult));
        }

        Optional<Genre> genreOptional = genreService.getGenreById(movieDto.getGenreId());
        if(genreOptional.isPresent()){
            Movie movieToSave = MovieMapper.createMovieFromDtoAndGenre(movieDto, genreOptional.get());

            if(movieService.save(movieToSave)){
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }

            return ResponseEntity.badRequest().body("The movie already exists");
        }

        return ResponseEntity.badRequest().body(String.format("There is no genre with id %d", movieDto.getGenreId()));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id,
                                             @Valid @RequestBody MovieDto movieDto,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ValidatorUtils.buildDefaultErrorMessage(bindingResult));
        }

        Optional<Genre> genreOptional = genreService.getGenreById(movieDto.getGenreId());
        if(genreOptional.isPresent()){
            Movie movieToSave = MovieMapper.createMovieFromDtoAndGenre(movieDto, genreOptional.get());

            if(movieService.updateById(id, movieToSave)){
                return ResponseEntity.status(HttpStatus.OK).build();
            }

            return ResponseEntity.badRequest().body(String.format("There is no movie with id %d", id));
        }

        return ResponseEntity.badRequest().body(String.format("There is no genre with id %d", movieDto.getGenreId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id){
        if(movieService.deleteById(id)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body(String.format("There is no movie with id %d", id));
    }
}

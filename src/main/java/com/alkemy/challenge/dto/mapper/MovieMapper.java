package com.alkemy.challenge.dto.mapper;

import com.alkemy.challenge.data.entities.Character;
import com.alkemy.challenge.data.entities.Genre;
import com.alkemy.challenge.data.entities.Movie;
import com.alkemy.challenge.dto.CharacterDto;
import com.alkemy.challenge.dto.MovieDto;
import com.alkemy.challenge.dto.image.utils.ImageBase64Utils;

import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {
    public static MovieDto getFullDto(Movie movie){
        return new MovieDto(
                movie.getId(),
                ImageBase64Utils.encodeImageToBase64(movie.getImage()),
                movie.getTitle(),
                movie.getReleaseDate(),
                movie.getRatingScore(),
                characterListToCharacterDtoList(movie.getCharacters()),
                GenreMapper.getDtoWithoutMovies(movie.getGenre()),
                null
        );
    }

    private static List<CharacterDto> characterListToCharacterDtoList(List<Character> characterList) {
        return characterList
                .stream()
                .map(CharacterMapper::getDtoWithoutMovies)
                .collect(Collectors.toList());
    }

    public static MovieDto getDtoWithoutCharacters(Movie movie){
        return new MovieDto(
                movie.getId(),
                ImageBase64Utils.encodeImageToBase64(movie.getImage()),
                movie.getTitle(),
                movie.getReleaseDate(),
                movie.getRatingScore(),
                null,
                GenreMapper.getDtoWithoutMovies(movie.getGenre()),
                null
        );
    }

    public static MovieDto getDtoWithoutGenre(Movie movie){
        return new MovieDto(
                movie.getId(),
                ImageBase64Utils.encodeImageToBase64(movie.getImage()),
                movie.getTitle(),
                movie.getReleaseDate(),
                movie.getRatingScore(),
                characterListToCharacterDtoList(movie.getCharacters()),
                null,
                null
        );
    }

    public static MovieDto getFilteredDto(Movie movie) {
        return new MovieDto(
                null,
                ImageBase64Utils.encodeImageToBase64(movie.getImage()),
                movie.getTitle(),
                movie.getReleaseDate(),
                null,
                null,
                null,
                null
        );
    }


    public static Movie createMovieFromDtoAndGenre(MovieDto movieDto, Genre genre) {
        return new Movie(
                ImageBase64Utils.validateAndDecodeBase64Image(movieDto.getImage()),
                movieDto.getTitle(),
                movieDto.getReleaseDate(),
                movieDto.getRatingScore(),
                genre
        );
    }
}

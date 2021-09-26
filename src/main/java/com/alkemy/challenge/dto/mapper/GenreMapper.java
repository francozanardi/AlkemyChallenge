package com.alkemy.challenge.dto.mapper;

import com.alkemy.challenge.data.entities.Character;
import com.alkemy.challenge.data.entities.Genre;
import com.alkemy.challenge.data.entities.Movie;
import com.alkemy.challenge.dto.CharacterDto;
import com.alkemy.challenge.dto.GenreDto;
import com.alkemy.challenge.dto.MovieDto;
import com.alkemy.challenge.dto.image.utils.ImageBase64Utils;

import java.util.List;
import java.util.stream.Collectors;

public class GenreMapper {
    public static GenreDto getFilteredDto(Genre genre){
        return new GenreDto(
                null,
                genre.getName(),
                ImageBase64Utils.encodeImageToBase64(genre.getImage()),
                null
        );
    }

    public static GenreDto getFullDto(Genre genre){
        return new GenreDto(
                genre.getId(),
                genre.getName(),
                ImageBase64Utils.encodeImageToBase64(genre.getImage()),
                movieListToMovieDtoList(genre.getMovies())
        );
    }

    private static List<MovieDto> movieListToMovieDtoList(List<Movie> movieList){
        return movieList
                .stream()
                .map(MovieMapper::getDtoWithoutGenre)
                .collect(Collectors.toList());
    }

    public static GenreDto getDtoWithoutMovies(Genre genre){
        return new GenreDto(
                genre.getId(),
                genre.getName(),
                ImageBase64Utils.encodeImageToBase64(genre.getImage()),
                null
        );
    }

    public static Genre createGenreFromDto(GenreDto genreDto){
        return new Genre(
                genreDto.getName(),
                ImageBase64Utils.validateAndDecodeBase64Image(genreDto.getImage())
        );
    }


}

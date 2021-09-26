package com.alkemy.challenge.dto.mapper;

import com.alkemy.challenge.data.entities.Character;
import com.alkemy.challenge.data.entities.Movie;
import com.alkemy.challenge.dto.CharacterDto;
import com.alkemy.challenge.dto.MovieDto;
import com.alkemy.challenge.dto.image.utils.ImageBase64Utils;

import java.util.List;
import java.util.stream.Collectors;

public class CharacterMapper {
    public static CharacterDto getFilteredDto(Character character){
        return new CharacterDto(
                null,
                ImageBase64Utils.encodeImageToBase64(character.getImage()),
                character.getName(),
                null,
                null,
                null,
                null);
    }

    public static CharacterDto getFullDto(Character character){
        return new CharacterDto(
                character.getId(),
                ImageBase64Utils.encodeImageToBase64(character.getImage()),
                character.getName(),
                character.getAge(),
                character.getWeight(),
                character.getHistory(),
                movieListToMovieDtoList(character.getMovies()));
    }

    private static List<MovieDto> movieListToMovieDtoList(List<Movie> movieList){
        return movieList
                .stream()
                .map(MovieMapper::getDtoWithoutCharacters)
                .collect(Collectors.toList());
    }

    public static CharacterDto getDtoWithoutMovies(Character character) {
        return new CharacterDto(
                character.getId(),
                ImageBase64Utils.encodeImageToBase64(character.getImage()),
                character.getName(),
                character.getAge(),
                character.getWeight(),
                character.getHistory(),
                null);
    }

    public static Character createCharacterFromDto(CharacterDto characterDto) {
        return new Character(
                ImageBase64Utils.validateAndDecodeBase64Image(characterDto.getImage()),
                characterDto.getName(),
                characterDto.getAge(),
                characterDto.getWeight(),
                characterDto.getHistory()
        );
    }

}

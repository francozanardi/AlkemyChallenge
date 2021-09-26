package com.alkemy.challenge.services;

import com.alkemy.challenge.data.entities.Character;

import java.util.List;
import java.util.Optional;

public interface CharacterService {
    List<Character> getAllCharacters();
    boolean save(Character character);
    Optional<Character> getCharacterById(Long id);
    boolean updateById(Long id, Character newCharacter);
    boolean deleteById(Long id);
    List<Character> searchCharacters(String name, Short age, Float weight, Long idMovie);
}

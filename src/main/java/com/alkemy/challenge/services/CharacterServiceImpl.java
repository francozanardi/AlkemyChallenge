package com.alkemy.challenge.services;

import com.alkemy.challenge.data.entities.Character;
import com.alkemy.challenge.data.repositories.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }

    @Override
    @Transactional
    public boolean save(Character character) {
        if(character.getId() == null || getCharacterById(character.getId()).isEmpty()){
            characterRepository.save(character);
            return true;
        }

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Character> getCharacterById(Long id) {
        return characterRepository.findById(id);
    }

    @Override
    @Transactional
    public boolean updateById(Long id, Character newCharacter) {
        newCharacter.setId(id);

        Optional<Character> characterFoundOptional = getCharacterById(id);
        if(characterFoundOptional.isPresent()){
            newCharacter.setMovies(characterFoundOptional.get().getMovies());
            characterRepository.save(newCharacter);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Character> characterFoundOptional = getCharacterById(id);
        if(characterFoundOptional.isPresent()){
            characterRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> searchCharacters(String name, Short age, Float weight, Long idMovie) {
        return characterRepository.searchCharacters(name, age, weight, idMovie);
    }
}

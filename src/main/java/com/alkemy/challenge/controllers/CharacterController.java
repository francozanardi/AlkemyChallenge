package com.alkemy.challenge.controllers;

import com.alkemy.challenge.data.entities.Character;
import com.alkemy.challenge.dto.CharacterDto;
import com.alkemy.challenge.dto.mapper.CharacterMapper;
import com.alkemy.challenge.dto.searches.CharacterSearchDto;
import com.alkemy.challenge.dto.validator.ValidatorUtils;
import com.alkemy.challenge.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public ResponseEntity<?> listCharacters(@Valid CharacterSearchDto characterSearchDto,
                                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ValidatorUtils.buildDefaultErrorMessage(bindingResult));
        }

        if(characterSearchDto.isEmpty()){
            return ResponseEntity.ok(listAllCharacters());
        }

        return ResponseEntity.ok(searchCharacters(characterSearchDto));
    }

    private List<CharacterDto> listAllCharacters(){
        return characterService.getAllCharacters()
                .stream()
                .map(CharacterMapper::getFilteredDto)
                .collect(Collectors.toList());
    }

    private List<CharacterDto> searchCharacters(CharacterSearchDto characterSearchDto){
       return characterService
                .searchCharacters(characterSearchDto.getName(),
                        characterSearchDto.getAge(),
                        characterSearchDto.getWeight(),
                        characterSearchDto.getMovies())
                .stream()
                .map(CharacterMapper::getFilteredDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDto> getOneCharacter(@PathVariable Long id){
        return characterService.getCharacterById(id)
                .map(CharacterMapper::getFullDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCharacter(@Valid @RequestBody CharacterDto characterDto,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ValidatorUtils.buildDefaultErrorMessage(bindingResult));
        }

        Character characterToSave = CharacterMapper.createCharacterFromDto(characterDto);

        if(characterService.save(characterToSave)){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.badRequest().body("The character already exists");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCharacter(@PathVariable Long id,
                                             @Valid @RequestBody CharacterDto characterDto,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(ValidatorUtils.buildDefaultErrorMessage(bindingResult));
        }

        Character characterToSave = CharacterMapper.createCharacterFromDto(characterDto);

        if (characterService.updateById(id, characterToSave)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.badRequest().body(String.format("The character with id %d doesn't exist", id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCharacter(@PathVariable Long id){
        if(characterService.deleteById(id)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body(String.format("The character with id %d doesn't exist", id));
    }
}

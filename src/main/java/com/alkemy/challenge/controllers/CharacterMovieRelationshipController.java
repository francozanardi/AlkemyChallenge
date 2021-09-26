package com.alkemy.challenge.controllers;

import com.alkemy.challenge.services.CharacterMovieRelationshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CharacterMovieRelationshipController {

    private final CharacterMovieRelationshipService relationshipService;

    public CharacterMovieRelationshipController(CharacterMovieRelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @PostMapping("/characters/{idCharacter}/movies/{idMovie}")
    public ResponseEntity<?> addRelationshipCharacterWithMovie(@PathVariable Long idCharacter, @PathVariable Long idMovie){
        if(relationshipService.addRelationship(idCharacter, idMovie)){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.badRequest().body(String.format("The character with id %d or the movie with id %d don't exist", idCharacter, idMovie));
    }

    @DeleteMapping("/characters/{idCharacter}/movies/{idMovie}")
    public ResponseEntity<?> deleteRelationshipCharacterWithMovie(@PathVariable Long idCharacter, @PathVariable Long idMovie){
        if(relationshipService.deleteRelationship(idCharacter, idMovie)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body(String.format("The character with id %d or the movie with id %d don't exist", idCharacter, idMovie));
    }
}

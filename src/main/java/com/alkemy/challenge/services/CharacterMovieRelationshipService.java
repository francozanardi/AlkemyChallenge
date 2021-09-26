package com.alkemy.challenge.services;

public interface CharacterMovieRelationshipService {
    boolean addRelationship(Long characterId, Long movieId);
    boolean deleteRelationship(Long characterId, Long movieId);
}

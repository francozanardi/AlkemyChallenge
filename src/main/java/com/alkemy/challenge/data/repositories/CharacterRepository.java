package com.alkemy.challenge.data.repositories;

import com.alkemy.challenge.data.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    @Query("select c from Character c join c.movies as m where " +
            "(?1 is null or c.name like %?1) and" +
            "(?2 is null or c.age = ?2) and " +
            "(?3 is null or c.weight = ?3) and " +
            "(?4 is null or m.id = ?4)")
    List<Character> searchCharacters(String name, Short age, Float weight, Long idMovie);
}

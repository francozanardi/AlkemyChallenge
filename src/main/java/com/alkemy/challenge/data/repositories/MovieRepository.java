package com.alkemy.challenge.data.repositories;

import com.alkemy.challenge.data.entities.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("select m from Movie m where " +
            "(?1 is null or m.title like %?1) and" +
            "(?2 is null or m.genre.id = ?2)")
    List<Movie> searchMovies(String title, Long genreId, Sort sort);
}

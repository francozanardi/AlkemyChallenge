package com.alkemy.challenge;

import com.alkemy.challenge.data.entities.Character;
import com.alkemy.challenge.data.entities.Genre;
import com.alkemy.challenge.data.entities.Movie;
import com.alkemy.challenge.data.entities.User;
import com.alkemy.challenge.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class ChallengeApplication implements CommandLineRunner {

	@Autowired
	private MovieService movieService;
	@Autowired
	private CharacterService characterService;
	@Autowired
	private GenreService genreService;
	@Autowired
	private CharacterMovieRelationshipService characterMovieRelationshipService;
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Genre genreAnimacion = new Genre("Animación", null);
		Genre genreThriller = new Genre("Thriller", null);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		Movie movieShrek = new Movie(null, "Shrek", dateFormat.parse("26/07/2001"), 4.1f, genreAnimacion);
		Movie movieSplit = new Movie(null, "Split", dateFormat.parse("20/01/2017"), 3.8f, genreThriller);
		Movie movieShutterIsland = new Movie(null, "Shutter Island", dateFormat.parse("19/02/2010"), 4.5f, genreThriller);

		Character characterShrek = new Character(null, "Shrek", (short) 30, 124.4f, null);
		Character characterFiona = new Character(null, "Fiona", (short) 25, 84.4f, null);
		Character characterKevin = new Character(null, "Kevin", (short) 20, 70.3f, null);

		genreService.save(genreAnimacion);
		genreService.save(genreThriller);

		movieService.save(movieShrek);
		movieService.save(movieSplit);
		movieService.save(movieShutterIsland);

		characterService.save(characterShrek);
		characterService.save(characterFiona);
		characterService.save(characterKevin);

		characterMovieRelationshipService.addRelationship(1L, 1L);
		characterMovieRelationshipService.addRelationship(2L, 1L);
		characterMovieRelationshipService.addRelationship(3L, 2L);

		userService.register(new User("user@user.com", "password"));
	}
}

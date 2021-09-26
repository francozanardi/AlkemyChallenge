package com.alkemy.challenge.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "movies")
@Entity
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            nullable = false
    )
    private Long id;

    @Lob
    private byte[] image;

    @Column(
            name = "title",
            nullable = false,
            length = 100
    )
    private String title;

    @Column(
            name = "release_date",
            nullable = false
    )
    private Date releaseDate;

    @Column(
            name = "rating_score",
            nullable = false
    )
    private Float ratingScore;

    @ManyToMany(mappedBy = "movies")
    private List<Character> characters;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false, foreignKey = @ForeignKey(name = "FK_GENRE_ID"))
    private Genre genre;

    protected Movie() {
    }

    public Movie(byte[] image, String title, Date releaseDate, Float ratingScore, Genre genre) {
        this.image = image;
        this.title = title;
        this.releaseDate = releaseDate;
        this.ratingScore = ratingScore;
        this.genre = genre;
    }

    @PreRemove
    public void removeRelationShip(){
        characters.forEach(character -> character.getMovies().remove(this));
    }
}

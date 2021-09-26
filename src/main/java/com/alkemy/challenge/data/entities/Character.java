package com.alkemy.challenge.data.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "characters")
@Entity
@Getter
@Setter
public class Character {
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
            name = "name",
            nullable = false,
            length = 100
    )
    private String name;

    @Column(
            name = "age",
            nullable = false,
            columnDefinition = "SMALLINT UNSIGNED"
    )
    private Short age;
    /* se guarda la edad y no la fecha de nacimiento dado que se asume que
     la edad que se almacena es la edad que presenta el personaje en la película/serie
     (i.e.: no varía a través del tiempo) */

    @Column(
            name = "weight",
            nullable = false
    )
    private Float weight;

    @Column(
            name = "history",
            columnDefinition = "TEXT",
            length = 1023
    )
    private String history;

    @ManyToMany()
    @JoinTable(
            name = "character_movie",
            joinColumns = @JoinColumn(name = "character_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "movie_id", nullable = false)
    )
    private List<Movie> movies;

    protected Character(){

    }

    public Character(byte[] image, String name, Short age, Float weight, String history) {
        this.image = image;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.history = history;
    }

}

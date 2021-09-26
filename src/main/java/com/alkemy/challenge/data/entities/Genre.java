package com.alkemy.challenge.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "genres")
@Entity
@Getter
@Setter
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            nullable = false
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            unique = true,
            length = 100
    )
    private String name;

    @Lob
    private byte[] image;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movie> movies;

    protected Genre(){

    }

    public Genre(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }
}

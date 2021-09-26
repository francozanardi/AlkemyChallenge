package com.alkemy.challenge.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "users")
@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            nullable = false
    )
    private Long id;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            length = 100
    )
    private String email;

    @Column(
            name = "password",
            nullable = false,
            length = 100
    )
    private String password;

    protected User(){

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

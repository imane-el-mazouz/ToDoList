package com.ToDoList.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "_User")

public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom", length = 128)
    private String nom;

    @Column(name = "prenom", length = 128)
    private String prenom;


    @Column(name = "email",length = 30 ,unique = true)
    private String email;

//    @Enumerated(EnumType.STRING)
//    private Role role;
}
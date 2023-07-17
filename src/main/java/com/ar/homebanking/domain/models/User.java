package com.ar.homebanking.domain.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user id")
    private Long id;

    private String username;

    private String password;

    @OneToMany (mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;




}


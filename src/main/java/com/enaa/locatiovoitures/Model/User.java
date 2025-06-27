package com.enaa.locatiovoitures.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


}

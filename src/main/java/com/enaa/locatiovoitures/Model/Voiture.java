package com.enaa.locatiovoitures.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Voiture {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String marque;

    @Column(nullable = false)
    private String modele;

    @Column(unique = true, nullable = false)
    private String immatriculation;

    @Enumerated(EnumType.STRING)
    private Categorie categorie;

    @Column(nullable = false)
    private Double prixJournalier;

    private String imageUrl;
    private String description;

    private Boolean isDisponible = true;
    private LocalDate dateDebutDispo;
    private LocalDate dateFinDispo;

    @ManyToOne
    @JoinColumn(name = "proprietaire_id")
    private User proprietaire;

    @OneToMany(mappedBy = "voiture", cascade = CascadeType.ALL)
    private List<Reservation> reservations;


}

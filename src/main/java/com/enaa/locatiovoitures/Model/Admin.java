package com.enaa.locatiovoitures.Model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Admin extends User{
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voiture> voitures = new ArrayList<>();

    @OneToMany(mappedBy = "adminConfirmateur")
    private List<Reservation> reservationsConfirmees = new ArrayList<>();

    public List<Voiture> getVoitures() {
        return voitures;
    }

    public void setVoitures(List<Voiture> voitures) {
        this.voitures = voitures;
    }

    public List<Reservation> getReservationsConfirmees() {
        return reservationsConfirmees;
    }

    public void setReservationsConfirmees(List<Reservation> reservationsConfirmees) {
        this.reservationsConfirmees = reservationsConfirmees;
    }
}

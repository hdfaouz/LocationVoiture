package com.enaa.locatiovoitures.Repositories;

import com.enaa.locatiovoitures.Model.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoitureRepository extends JpaRepository<Voiture,Long> {
}

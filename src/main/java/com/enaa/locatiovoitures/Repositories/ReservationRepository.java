package com.enaa.locatiovoitures.Repositories;

import com.enaa.locatiovoitures.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}

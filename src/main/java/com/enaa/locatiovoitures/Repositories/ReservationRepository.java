package com.enaa.locatiovoitures.Repositories;

import com.enaa.locatiovoitures.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationByClient_Id(Long id);
}



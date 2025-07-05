package com.enaa.locatiovoitures.Services;

import com.enaa.locatiovoitures.Dto.ReservationDto;
import com.enaa.locatiovoitures.Mappers.ReservationMap;
import com.enaa.locatiovoitures.Model.Reservation;
import com.enaa.locatiovoitures.Repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMap reservationMap;

    public ReservationService(ReservationRepository reservationRepository, ReservationMap reservationMap) {
        this.reservationRepository = reservationRepository;
        this.reservationMap = reservationMap;
    }

    public ReservationDto ajouter(ReservationDto reservationDto){
        Reservation reservation = reservationMap.toEntity(reservationDto);
        Reservation saveReservation = reservationRepository.save(reservation);
        return reservationMap.toDto(saveReservation);
    }

    public List<ReservationDto> getAll(){
        List<Reservation> reservation = reservationRepository.findAll();
        return reservationMap.toDTOs(reservation);
    }
}

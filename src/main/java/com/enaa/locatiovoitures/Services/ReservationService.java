package com.enaa.locatiovoitures.Services;

import com.enaa.locatiovoitures.Dto.ReservationDto;
import com.enaa.locatiovoitures.Dto.VoitureDto;
import com.enaa.locatiovoitures.Mappers.ReservationMap;
import com.enaa.locatiovoitures.Model.Client;
import com.enaa.locatiovoitures.Model.Reservation;
import com.enaa.locatiovoitures.Model.User;
import com.enaa.locatiovoitures.Model.Voiture;
import com.enaa.locatiovoitures.Repositories.ReservationRepository;
import com.enaa.locatiovoitures.Repositories.UserRepository;
import com.enaa.locatiovoitures.Repositories.VoitureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMap reservationMap;
    private final UserRepository userRepository;
    private final VoitureRepository voitureRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationMap reservationMap, UserRepository userRepository, VoitureRepository voitureRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMap = reservationMap;
        this.userRepository = userRepository;
        this.voitureRepository = voitureRepository;
    }

    public  ReservationDto ajouter(ReservationDto reservationDto){
        Reservation reservation = reservationMap.toEntity(reservationDto);
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = (Client) userRepository.findById(userRepository.findByEmail(user.getUsername()).getId())
                    .orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));
        reservation.setClient(client);
        Voiture voiture = voitureRepository.findById(reservationDto.getVoitureId()).orElseThrow(() -> new EntityNotFoundException("Voiture non trouvé"));
        reservation.setVoiture(voiture);
        Reservation saveReservation = reservationRepository.save(reservation);
        return reservationMap.toDto(saveReservation);
    }

    public List<ReservationDto> getAll(){
        List<Reservation> reservation = reservationRepository.findAll();
        return reservationMap.toDTOs(reservation);
    }

    public void deletReservation(Long id){

        reservationRepository.deleteById(id);
    }
    public ReservationDto update(Long id, ReservationDto dto){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("reservation not found"));


        reservation.setEndDate(dto.getEndDate());
        reservation.setTotalPrice(dto.getTotalPrice());


       Reservation savedReservation = reservationRepository.save(reservation);

        return reservationMap.toDto(savedReservation);
    }

    public ReservationDto getById(Long id){
        Reservation foundCompetence =reservationRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("book not found"));
        return reservationMap.toDto(foundCompetence);
    }
}

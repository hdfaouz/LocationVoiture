package com.enaa.locatiovoitures.Services;

import com.enaa.locatiovoitures.Dto.ReservationDto;
import com.enaa.locatiovoitures.Mappers.ReservationMap;
import com.enaa.locatiovoitures.Model.Client;
import com.enaa.locatiovoitures.Model.Reservation;
import com.enaa.locatiovoitures.Repositories.ReservationRepository;
import com.enaa.locatiovoitures.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMap reservationMap;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationMap reservationMap, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMap = reservationMap;
        this.userRepository = userRepository;
    }

    public  ReservationDto ajouter(ReservationDto reservationDto){
        // Validation
        if (reservationDto.getClientId() == null) {
            throw new IllegalArgumentException("L'ID du client est requis");
        }

        Reservation reservation = reservationMap.toEntity(reservationDto);

        // Vérifier que le client n'est pas null après mapping
        if (reservation.getClient() == null) {
            Client client = (Client) userRepository.findById(reservationDto.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));
            reservation.setClient(client);
        }

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
}

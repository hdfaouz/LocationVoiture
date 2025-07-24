package com.enaa.locatiovoitures;

import com.enaa.locatiovoitures.Dto.ReservationDto;
import com.enaa.locatiovoitures.Mappers.ReservationMap;
import com.enaa.locatiovoitures.Model.Client;
import com.enaa.locatiovoitures.Model.Reservation;
import com.enaa.locatiovoitures.Model.Voiture;
import com.enaa.locatiovoitures.Repositories.ReservationRepository;
import com.enaa.locatiovoitures.Repositories.UserRepository;
import com.enaa.locatiovoitures.Repositories.VoitureRepository;
import com.enaa.locatiovoitures.Services.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationTest {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VoitureRepository voitureRepository;

    @Mock
    private ReservationMap reservationMap;

    @InjectMocks
    private ReservationService reservationService; // Remplacez par le nom de votre service

    @Test
    void AjouterReservation() {
        // Given
        Long clientId = 1L;
        Long voitureId = 2L;
        String userEmail = "test@example.com";

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setVoitureId(voitureId);

        Reservation reservation = new Reservation();

        Client client = new Client();
        client.setId(clientId);

        Voiture voiture = new Voiture();
        voiture.setId(voitureId);

        Reservation savedReservation = new Reservation();
        savedReservation.setClient(client);
        savedReservation.setVoiture(voiture);

        ReservationDto expectedDto = new ReservationDto();
        expectedDto.setClientId(clientId);
        expectedDto.setVoitureId(voitureId);

        // Mocking UserDetails
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(userEmail);

        // Mocking SecurityContext
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Mocking static SecurityContextHolder
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            // Mocking repository calls
            when(reservationMap.toEntity(reservationDto)).thenReturn(reservation);
            when(userRepository.findByEmail(userEmail)).thenReturn(client);
            when(userRepository.findById(clientId)).thenReturn(Optional.of(client));
            when(voitureRepository.findById(voitureId)).thenReturn(Optional.of(voiture));
            when(reservationRepository.save(reservation)).thenReturn(savedReservation);
            when(reservationMap.toDto(savedReservation)).thenReturn(expectedDto);

            // When
            ReservationDto result = reservationService.ajouter(reservationDto);

            // Then
            assertNotNull(result);
            assertEquals(expectedDto, result);

            // Verify interactions
            verify(reservationMap).toEntity(reservationDto);
            verify(userRepository).findByEmail(userEmail);
            verify(userRepository).findById(clientId);
            verify(voitureRepository).findById(voitureId);
            verify(reservationRepository).save(reservation);
            verify(reservationMap).toDto(savedReservation);

            // Verify that client and voiture are set on reservation
            assertEquals(client, reservation.getClient());
            assertEquals(voiture, reservation.getVoiture());
        }
    }
}

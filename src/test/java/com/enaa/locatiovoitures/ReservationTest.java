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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(userEmail);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            when(reservationMap.toEntity(reservationDto)).thenReturn(reservation);
            when(userRepository.findByEmail(userEmail)).thenReturn(client);
            when(userRepository.findById(clientId)).thenReturn(Optional.of(client));
            when(voitureRepository.findById(voitureId)).thenReturn(Optional.of(voiture));
            when(reservationRepository.save(reservation)).thenReturn(savedReservation);
            when(reservationMap.toDto(savedReservation)).thenReturn(expectedDto);

            ReservationDto result = reservationService.ajouter(reservationDto);

            assertNotNull(result);
            assertEquals(expectedDto, result);

            verify(reservationMap).toEntity(reservationDto);
            verify(userRepository).findByEmail(userEmail);
            verify(userRepository).findById(clientId);
            verify(voitureRepository).findById(voitureId);
            verify(reservationRepository).save(reservation);
            verify(reservationMap).toDto(savedReservation);

            assertEquals(client, reservation.getClient());
            assertEquals(voiture, reservation.getVoiture());
        }
    }
    @Test
    void testGetAll() {
        Client client1 = new Client();
        client1.setId(1L);

        Client client2 = new Client();
        client2.setId(2L);

        Voiture voiture1 = new Voiture();
        voiture1.setId(1L);
        voiture1.setCategory("Toyota");

        Voiture voiture2 = new Voiture();
        voiture2.setId(2L);
        voiture2.setCategory("Renault");

        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setClient(client1);
        reservation1.setVoiture(voiture1);
        reservation1.getStartDate(LocalDateTime.now());
        reservation1.setEndDate(LocalDate.from(LocalDateTime.now().plusDays(3)));

        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setClient(client2);
        reservation2.setVoiture(voiture2);
        reservation2.setStartDate(LocalDate.from(LocalDateTime.now().plusDays(1)));
        reservation2.setEndDate(LocalDate.from(LocalDateTime.now().plusDays(5)));

        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

        ReservationDto reservationDto1 = new ReservationDto();
        reservationDto1.setClientId(1L);
        reservationDto1.setVoitureId(1L);
        reservationDto1.setStartDate(reservation1.getStartDate(LocalDateTime.now()));
        reservationDto1.setEndDate(reservation1.getEndDate());

        ReservationDto reservationDto2 = new ReservationDto();
        reservationDto2.setClientId(2L);
        reservationDto2.setVoitureId(2L);
        reservationDto2.setStartDate(reservation2.getStartDate(LocalDateTime.now()));
        reservationDto2.setEndDate(reservation2.getEndDate());

        List<ReservationDto> expectedDtos = Arrays.asList(reservationDto1, reservationDto2);

        when(reservationRepository.findAll()).thenReturn(reservations);
        when(reservationMap.toDTOs(reservations)).thenReturn(expectedDtos);

        List<ReservationDto> result = reservationService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedDtos, result);
        assertEquals(1L, result.get(0).getClientId());
        assertEquals(2L, result.get(1).getClientId());
        assertEquals(1L, result.get(0).getVoitureId());
        assertEquals(2L, result.get(1).getVoitureId());

        verify(reservationRepository, times(1)).findAll();
        verify(reservationMap, times(1)).toDTOs(reservations);
    }
    @Test
    void testDeleteReservation() {
        // Given
        Long reservationId = 1L;

        // When
        reservationService.deletReservation(reservationId);

        // Then
        // Vérifier que la méthode deleteById a été appelée exactement une fois avec le bon ID
        verify(reservationRepository, times(1)).deleteById(reservationId);

        // Vérifier qu'aucune autre interaction n'a eu lieu
        verifyNoMoreInteractions(reservationRepository);
    }
}

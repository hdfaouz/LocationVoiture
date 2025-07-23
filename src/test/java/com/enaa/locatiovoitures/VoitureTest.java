package com.enaa.locatiovoitures;

import com.enaa.locatiovoitures.Dto.VoitureDto;
import com.enaa.locatiovoitures.Mappers.VoitureMap;
import com.enaa.locatiovoitures.Model.Admin;
import com.enaa.locatiovoitures.Model.Voiture;
import com.enaa.locatiovoitures.Repositories.VoitureRepository;
import com.enaa.locatiovoitures.Services.VoitureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoitureTest {
    @Mock
    private VoitureRepository voitureRepository;

    @Mock
    private VoitureMap voitureMap;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private VoitureService voitureService;
    @Test
    void ajouterVoiture() {

        Admin admin = new Admin();
        admin.setId(1L);

        VoitureDto voitureDto = new VoitureDto();
        voitureDto.setBrand("Toyota");
        voitureDto.setCategory("Camry");

        Voiture voiture = new Voiture();
        voiture.setModel("Toyota");
        voiture.setCategory("Camry");

        Voiture savedVoiture = new Voiture();
        savedVoiture.setId(1L);
        savedVoiture.setModel("Toyota");
        savedVoiture.setCategory("Camry");
        savedVoiture.setAdmin(admin);

        VoitureDto expectedDto = new VoitureDto();
        expectedDto.setBrand("Toyota");
        expectedDto.setCategory("Camry");

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder =
                     mockStatic(SecurityContextHolder.class)) {

            mockedSecurityContextHolder.when(SecurityContextHolder::getContext)
                    .thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(admin);

            when(voitureMap.toEntity(voitureDto)).thenReturn(voiture);
            when(voitureRepository.save(voiture)).thenReturn(savedVoiture);
            when(voitureMap.toDto(savedVoiture)).thenReturn(expectedDto);


            VoitureDto result = voitureService.ajouterVoiture(voitureDto);


            assertNotNull(result);
            assertEquals("Toyota", result.getBrand());
            assertEquals("Camry", result.getCategory());
        }
    }

        @Test
        void getAllVoitures() {

            Voiture voiture1 = new Voiture();
            voiture1.setId(1L);
            voiture1.setModel("Toyota");
            voiture1.setCategory("Sedan");

            Voiture voiture2 = new Voiture();
            voiture2.setId(2L);
            voiture2.setModel("Honda");
            voiture2.setCategory("SUV");

            List<Voiture> voitures = Arrays.asList(voiture1, voiture2);

            VoitureDto voitureDto1 = new VoitureDto();
            voitureDto1.setBrand("Toyota");
            voitureDto1.setCategory("Sedan");

            VoitureDto voitureDto2 = new VoitureDto();
            voitureDto2.setBrand("Honda");
            voitureDto2.setCategory("SUV");

            List<VoitureDto> expectedDtos = Arrays.asList(voitureDto1, voitureDto2);

           //Configuration
            when(voitureRepository.findAll()).thenReturn(voitures);
            when(voitureMap.toDTOs(voitures)).thenReturn(expectedDtos);

            // Exécution
            List<VoitureDto> result = voitureService.getAllVoitures();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("Toyota", result.get(0).getBrand());
            assertEquals("Sedan", result.get(0).getCategory());
            assertEquals("Honda", result.get(1).getBrand());
            assertEquals("SUV", result.get(1).getCategory());
        }

    @Test
    void deletVoiture() {

        Long voitureId = 1L;
        voitureService.deletVoiture(voitureId);

        verify(voitureRepository).deleteById(voitureId);
    }

    }


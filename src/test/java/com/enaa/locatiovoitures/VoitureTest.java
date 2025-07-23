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

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

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
    void ajouterVoiture_DevraitRetournerVoitureDto() {
        // Données de test
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

            // Configuration des mocks - C'EST OBLIGATOIRE !
            when(voitureMap.toEntity(voitureDto)).thenReturn(voiture);
            when(voitureRepository.save(voiture)).thenReturn(savedVoiture);
            when(voitureMap.toDto(savedVoiture)).thenReturn(expectedDto);

            // Exécution
            VoitureDto result = voitureService.ajouterVoiture(voitureDto);

            // Vérifications
            assertNotNull(result);
            assertEquals("Toyota", result.getBrand());
            assertEquals("Camry", result.getCategory());
        }
    }
}

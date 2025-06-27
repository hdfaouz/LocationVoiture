package com.enaa.locatiovoitures.Services;

import com.enaa.locatiovoitures.Dto.VoitureDto;
import com.enaa.locatiovoitures.Mappers.VoitureMap;
import com.enaa.locatiovoitures.Model.Voiture;
import com.enaa.locatiovoitures.Repositories.VoitureRepository;
import org.springframework.stereotype.Service;

@Service
public class VoitureService {
    private final VoitureMap voitureMap;
    private final VoitureRepository voitureRepository;

    public VoitureService(VoitureMap voitureMap, VoitureRepository voitureRepository) {
        this.voitureMap = voitureMap;
        this.voitureRepository = voitureRepository;
    }

    public VoitureDto ajouterVoiture(VoitureDto voitureDto){
        Voiture voiture = voitureMap.toEntity(voitureDto);
        Voiture saveVoiture = voitureRepository.save(voiture);
        return voitureMap.toDto(saveVoiture);

    }
}

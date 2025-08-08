package com.enaa.locatiovoitures.Services;

import com.enaa.locatiovoitures.Dto.UserDto;
import com.enaa.locatiovoitures.Dto.VoitureDto;
import com.enaa.locatiovoitures.Mappers.VoitureMap;
import com.enaa.locatiovoitures.Model.Admin;
import com.enaa.locatiovoitures.Model.User;
import com.enaa.locatiovoitures.Model.Voiture;
import com.enaa.locatiovoitures.Repositories.VoitureRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoitureService {
    private final VoitureMap voitureMap;
    private final VoitureRepository voitureRepository;

    public VoitureService(VoitureMap voitureMap, VoitureRepository voitureRepository) {
        this.voitureMap = voitureMap;
        this.voitureRepository = voitureRepository;
    }

    public VoitureDto ajouterVoiture(VoitureDto voitureDto){
        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Voiture voiture = voitureMap.toEntity(voitureDto);
        voiture.setAdmin(admin);
        Voiture saveVoiture = voitureRepository.save(voiture);
        return voitureMap.toDto(saveVoiture);

    }

    public List<VoitureDto> getAllVoitures(){
    List<Voiture> voitures = voitureRepository.findAll();
    return voitureMap.toDTOs(voitures);
    }

    public void deletVoiture(Long id){
        voitureRepository.deleteById(id);
    }

    public VoitureDto update(Long id, VoitureDto dto){
       Voiture voiture = voitureRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Voiture not found"));

        voiture.isAvailable(dto.isAvailable());
        voiture.setCategory(dto.getCategory());
        voiture.setBrand(dto.getBrand());
        voiture.setModel(dto.getModel());
        voiture.setPricePerDay(dto.getPricePerDay());
        voiture.setImageUrl(dto.getImageUrl());

       Voiture savedVoiture = voitureRepository.save(voiture);

        return voitureMap.toDto(savedVoiture);
    }

    public VoitureDto getById(Long id){
        Voiture foundCompetence =voitureRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("book not found"));
        return voitureMap.toDto(foundCompetence);
    }
}

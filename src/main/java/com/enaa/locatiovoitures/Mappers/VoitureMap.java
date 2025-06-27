package com.enaa.locatiovoitures.Mappers;

import com.enaa.locatiovoitures.Dto.VoitureDto;
import com.enaa.locatiovoitures.Model.Voiture;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoitureMap {
    Voiture toEntity (VoitureDto dto);
    VoitureDto toDto (Voiture voiture);
    List<VoitureDto> toDTOs (List<Voiture> voitures);

}

package com.enaa.locatiovoitures.Mappers;

import com.enaa.locatiovoitures.Dto.ReservationDto;
import com.enaa.locatiovoitures.Model.Reservation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMap {

    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "voitureId", target = "voiture.id")
    Reservation toEntity (ReservationDto dto);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "voitureId", source = "voiture.id")
    ReservationDto toDto (Reservation reservation);


    List<ReservationDto> toDTOs (List<Reservation> reservationList);
}

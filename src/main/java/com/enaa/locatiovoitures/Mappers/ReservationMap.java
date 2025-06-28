package com.enaa.locatiovoitures.Mappers;

import com.enaa.locatiovoitures.Dto.ReservationDto;
import com.enaa.locatiovoitures.Model.Reservation;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMap {
    Reservation toEntity (ReservationDto dto);
    ReservationDto toDto (Reservation reservation);
    List<ReservationDto> toDTOs (List<Reservation> reservationList);
}

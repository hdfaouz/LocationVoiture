package com.enaa.locatiovoitures.Mappers;

import com.enaa.locatiovoitures.Dto.UserDto;
import com.enaa.locatiovoitures.Dto.VoitureDto;
import com.enaa.locatiovoitures.Model.User;
import com.enaa.locatiovoitures.Model.Voiture;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMap {
    User toEntity (UserDto dto);
    UserDto toDto (User user);
    List<UserDto> toDTOs (List<User> users);
}

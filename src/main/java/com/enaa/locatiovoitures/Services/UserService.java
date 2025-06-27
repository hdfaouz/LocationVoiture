package com.enaa.locatiovoitures.Services;

import com.enaa.locatiovoitures.Dto.UserDto;
import com.enaa.locatiovoitures.Mappers.UserMap;
import com.enaa.locatiovoitures.Model.User;
import com.enaa.locatiovoitures.Model.Voiture;
import com.enaa.locatiovoitures.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMap userMap;


    public UserService(UserRepository userRepository, UserMap userMap) {
        this.userRepository = userRepository;
        this.userMap = userMap;
    }

    public UserDto ajouter(UserDto dto){
        User user = userMap.toEntity(dto);
        User save = userRepository.save(user);
        return userMap.toDto(save);
    }

    public List<UserDto> getAll(){
        List<User> users = userRepository.findAll();
        return userMap.toDTOs(users);
    }

    public void delet(Long id){
        userRepository.deleteById(id);
    }

    public UserDto update(Long id, UserDto dto){
       User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));

        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());


        User savedUser = userRepository.save(user);

        return userMap.toDto(savedUser);
    }
}

package com.enaa.locatiovoitures.Controllers;

import com.enaa.locatiovoitures.Dto.UserDto;
import com.enaa.locatiovoitures.Services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto ajouter(@RequestBody UserDto dto){
        return userService.ajouter(dto);
    }
    @GetMapping
    public List<UserDto> getAll(){
        return userService.getAll();
    }
}

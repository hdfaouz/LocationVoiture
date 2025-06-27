package com.enaa.locatiovoitures.Controllers;

import com.enaa.locatiovoitures.Dto.UserDto;
import com.enaa.locatiovoitures.Services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

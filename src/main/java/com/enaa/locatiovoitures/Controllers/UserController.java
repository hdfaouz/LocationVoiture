package com.enaa.locatiovoitures.Controllers;

import com.enaa.locatiovoitures.Dto.UserDto;
import com.enaa.locatiovoitures.Services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
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
    @DeleteMapping("/{id}")
    public void delet(@PathVariable Long id){

        userService.delet(id);
    }
    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id,@RequestBody UserDto userDto){
        return userService.update(id, userDto);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id){
        return userService.getById(id);
    }
}

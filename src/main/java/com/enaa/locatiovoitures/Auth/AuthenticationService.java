package com.enaa.locatiovoitures.Auth;

import com.enaa.locatiovoitures.Configuration.JwtService;
import com.enaa.locatiovoitures.Dto.UserDto;
import com.enaa.locatiovoitures.Model.Admin;
import com.enaa.locatiovoitures.Model.Client;
import com.enaa.locatiovoitures.Model.User;
import com.enaa.locatiovoitures.Repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    public AuthenticationResponse register(RegisterRequest request) {
        User user;


        switch (request.getRole()) {
            case ADMIN -> user = new Admin();
            case CLIENT-> user = new Client();


            default -> throw new IllegalArgumentException("Invalid role: " + request.getRole());
        }

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwtToken);
        return response;
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user);

        UserDto userDto = new UserDto(
                user.getEmail(),
                user.getPassword(),
                user.getRole()

        );

        return  new AuthenticationResponse(jwtToken , userDto);

//        AuthenticationResponse response = new AuthenticationResponse();
//        response.setToken(jwtToken);
//        return response;
    }
}

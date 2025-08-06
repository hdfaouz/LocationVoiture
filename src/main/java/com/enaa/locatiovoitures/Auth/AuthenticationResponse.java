package com.enaa.locatiovoitures.Auth;


import com.enaa.locatiovoitures.Dto.UserDto;

public class AuthenticationResponse {

    private String token;
    private UserDto user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }
    public AuthenticationResponse(String token , UserDto user) {
        this.token = token;
        this.user = user;
    }

    public AuthenticationResponse() {
    }


}

package com.backend.evenhi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LoginResponse {

    private String jwtToken;
    private String username;
    private String email;
    private List<String> roles;

    public LoginResponse(String username, String email, String jwtToken, List<String> roles) {
        this.username = username;
        this.email = email;
        this.jwtToken = jwtToken;
        this.roles = roles;
    }
}

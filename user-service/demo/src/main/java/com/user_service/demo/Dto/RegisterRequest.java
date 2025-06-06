package com.user_service.demo.Dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;

public class RegisterRequest {


    private String email;
    private String username;
    private String password;

    public RegisterRequest(String email, String username, String password){

        this.email = email;
        this.username = username;
        this.password = password;
    }
    public RegisterRequest(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

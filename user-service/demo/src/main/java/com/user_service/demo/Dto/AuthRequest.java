package com.user_service.demo.Dto;

public class AuthRequest {


    private String password;
    private String email;
    public AuthRequest(String email, String password) {

        this.password = password;
        this.email = email;
    }
    public AuthRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

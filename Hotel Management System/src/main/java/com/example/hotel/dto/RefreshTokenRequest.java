package com.example.hotel.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

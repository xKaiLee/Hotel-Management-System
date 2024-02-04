package com.example.hotel.service;

import com.example.hotel.dto.JwtAuthenticationResponse;
import com.example.hotel.dto.RefreshTokenRequest;
import com.example.hotel.dto.SignInRequest;
import com.example.hotel.dto.SignUpRequest;
import com.example.hotel.model.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserEntity signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signinRequest, HttpServletResponse response);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}

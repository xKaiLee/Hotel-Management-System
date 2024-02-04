package com.example.hotel.service.imp;

import com.example.hotel.dto.JwtAuthenticationResponse;
import com.example.hotel.dto.RefreshTokenRequest;
import com.example.hotel.dto.SignInRequest;
import com.example.hotel.dto.SignUpRequest;
import com.example.hotel.model.Role;
import com.example.hotel.model.UserEntity;
import com.example.hotel.repository.UserRepository;
import com.example.hotel.service.AuthenticationService;
import com.example.hotel.service.JWTService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;


    public UserEntity signup(SignUpRequest signUpRequest){
        if (StringUtils.isEmpty(signUpRequest.getRawPassword())) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("An account with this email already exists");
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new IllegalArgumentException("An account with this username already exists");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(signUpRequest.getFirstName());
        userEntity.setLastName(signUpRequest.getLastName());
        userEntity.setEmail(signUpRequest.getEmail());
        userEntity.setRole(Role.USER);
        userEntity.setRawPassword(signUpRequest.getRawPassword());
        userEntity.setPassword(passwordEncoder.encode(signUpRequest.getRawPassword()));
        userEntity.setUsername(signUpRequest.getUsername());
        userEntity.setPhoneNumber(signUpRequest.getPhoneNumber());

        UserEntity savedUser = userRepository.save(userEntity);


        return savedUser;
    }

    public JwtAuthenticationResponse signin(SignInRequest signinRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var user = userRepository.findByUsername(signinRequest.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

            Cookie tokenCookie = new Cookie("token", jwt);
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(3600);
            response.addCookie(tokenCookie);

            String roleInfo = user.getRole().name();
            Cookie roleCookie = new Cookie("userRole", roleInfo);
            roleCookie.setPath("/");
            roleCookie.setMaxAge(3600);
            response.addCookie(roleCookie);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            return jwtAuthenticationResponse;

        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Invalid username or password", e);
        }
    }


    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String username = jwtService.extractUserName(refreshTokenRequest.getToken());
        UserEntity user = userRepository.findByUsername(username).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}

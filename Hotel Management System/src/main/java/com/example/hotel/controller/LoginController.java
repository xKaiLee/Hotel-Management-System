package com.example.hotel.controller;

import com.example.hotel.dto.JwtAuthenticationResponse;
import com.example.hotel.dto.SignInRequest;
import com.example.hotel.dto.SignUpRequest;
import com.example.hotel.model.Role;
import com.example.hotel.model.UserEntity;
import com.example.hotel.service.AuthenticationService;
import com.example.hotel.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        SignUpRequest signUpRequest = new SignUpRequest();
        model.addAttribute("signUpRequest", signUpRequest);
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignUpRequest signUpRequest, Model model) {
        try {
            authenticationService.signup(signUpRequest);
            return "redirect:/signin";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }
    @GetMapping("/signin")
    public String showSignInForm(Model model) {
        SignInRequest signInRequest = new SignInRequest();
        model.addAttribute("signInRequest", signInRequest);
        return "signin";
    }

    @PostMapping("/signin")
    public String signin(@ModelAttribute SignInRequest signInRequest, HttpServletResponse response, Model model) {
        try {
            authenticationService.signin(signInRequest, response);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
                if (!authorities.isEmpty()) {
                    String role = authorities.get(0).getAuthority();
                }
            }
            return "redirect:/choose-panel";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signin";
        }
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    cookie.setValue(null);
                }
            }
            return "redirect:/signin";
        }
        return "redirect:/signin";
    }
}
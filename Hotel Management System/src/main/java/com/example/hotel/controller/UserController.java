package com.example.hotel.controller;

import com.example.hotel.model.Role;
import com.example.hotel.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.hotel.service.imp.UserServiceImp;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController implements WebMvcConfigurer {
    @Autowired
    UserServiceImp userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/list")
    public String showAllPerson(Model model){
        model.addAttribute("user", userService.getAllGuests());
        return "list_user";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        UserEntity userEntity = userService.getGuest(id);
        model.addAttribute("user", userEntity);
        return "edit_user";
    }


    @PostMapping("/update/{id}")
    public String updateGuest(@PathVariable Long id, @Valid @ModelAttribute("guest") UserEntity userEntity,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "edit_guest";
        }

        userService.updateGuest(id, userEntity);
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteGuest(@PathVariable Long id) {
        userService.deleteGuest(id);
        return "redirect:/guest/list";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserEntity userEntity = new UserEntity();
        model.addAttribute("user", userEntity);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserEntity userEntity,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getRawPassword()));
        userEntity.setRole(Role.USER);
        userService.addGuest(userEntity);
        return "redirect:/login"; // Redirect to login page after successful registration
    }



}

package com.example.hotel.service;

import java.util.List;
import java.util.Optional;

import com.example.hotel.model.Role;
import com.example.hotel.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    
    List<UserEntity> getAllGuests();
    UserEntity getGuest(long id);
    UserEntity addGuest(UserEntity g);


    void updateGuest(long id, UserEntity updatedUserEntity);

    void deleteGuest(long id);
    UserDetailsService userDetailsService();

    Role getUserRole(UserEntity user);



}

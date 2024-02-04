package com.example.hotel.service.imp;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.hotel.model.Role;
import com.example.hotel.model.UserEntity;
import com.example.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.hotel.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserEntity> getAllGuests() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getGuest(long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public UserEntity addGuest(UserEntity g) {
        return userRepository.save(g);
    }

    @Override
    public void updateGuest(long id, UserEntity updatedUserEntity) {
        UserEntity existingUserEntity = userRepository.findById(id).orElseThrow();
        existingUserEntity.setFirstName(updatedUserEntity.getFirstName());
        existingUserEntity.setLastName(updatedUserEntity.getLastName());
        existingUserEntity.setPhoneNumber(updatedUserEntity.getPhoneNumber());
        userRepository.save(existingUserEntity);
    }

    @Override
    public void deleteGuest(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity userEntity = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                Set<GrantedAuthority> authorities = Collections.singleton(
                        new SimpleGrantedAuthority(userEntity.getRole().name())
                );

                return new org.springframework.security.core.userdetails.User(
                        userEntity.getUsername(),
                        userEntity.getPassword(),
                        authorities
                );
            }
        };
    }
    @Override
    public Role getUserRole(UserEntity user){
        return user.getRole();
    }
}

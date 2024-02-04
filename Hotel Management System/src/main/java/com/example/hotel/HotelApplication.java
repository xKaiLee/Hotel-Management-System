package com.example.hotel;

import com.example.hotel.model.Role;
import com.example.hotel.model.UserEntity;
import com.example.hotel.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class HotelApplication implements CommandLineRunner{
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

	public void run(String... args) {
		UserEntity adminAccount = userRepository.findByRole(Role.ADMIN);
		if (null == adminAccount) {
			UserEntity userEntity = new UserEntity();
			userEntity.setUsername("admin");
			userEntity.setEmail("admin@gmail.com");
			userEntity.setFirstName("Dawid");
			userEntity.setLastName("Przytula");
			userEntity.setRole(Role.ADMIN);
			userEntity.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(userEntity);
		}
	}
}

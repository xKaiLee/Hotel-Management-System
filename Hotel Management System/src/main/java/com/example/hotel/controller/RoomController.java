package com.example.hotel.controller;

import com.example.hotel.model.UserEntity;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.repository.UserRepository;
import com.example.hotel.service.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.hotel.model.Room;
import com.example.hotel.service.RoomService;

import jakarta.validation.Valid;
import org.springframework.web.util.WebUtils;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/room")
public class RoomController implements WebMvcConfigurer {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/list")
    public String showAllRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "list_room";
    }

    @GetMapping("/new")
    public String newRoom(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        return "new_room";
    }

    @PostMapping("/add")
    public String addRoom(@Valid @ModelAttribute("room") Room room,
                          BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "new_room";
        }
        try {
            roomService.addRoom(room);
            return "redirect:/room/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "new_room";
        }
    }

    @GetMapping("/edit/{id}")
    public String editRoom(@PathVariable Long id, Model model) {
        Room room = roomService.getRoom(id);
        model.addAttribute("room", room);
        return "edit_room";
    }

    @PostMapping("/update/{id}")
    public String updateRoom(@PathVariable Long id, @Valid @ModelAttribute("room") Room room,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "edit_room";
        }

        roomService.updateRoom(id, room);
        return "redirect:/room/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/room/list";
    }

    @GetMapping("/available")
    public String showRooms(Model model) {
        List<Room> rooms = roomService.getAvailableRooms();
        model.addAttribute("rooms", rooms);
        return "available_rooms";
    }

    @PostMapping("/reserve")
    public String reserveRoom(@RequestParam("roomId") Long roomId, HttpServletRequest request, HttpServletResponse response) {
        Cookie tokenCookie = WebUtils.getCookie(request, "token");
        if(tokenCookie != null) {
            String token = tokenCookie.getValue();
            String username = jwtService.extractUserName(token);
            Long userID = userRepository.findByUsername(username).get().getId();
            Room room = roomRepository.findById(roomId).get();
            room.setCurrentGuestId(userID);
            room.setOccupied(true);
            roomRepository.save(room);
        }
        return "redirect:/room/rooms";
    }

    @GetMapping("/rooms")
    public String displayRoomsForCurrentUser(HttpServletRequest request, Model model) {
        Cookie tokenCookie = WebUtils.getCookie(request, "token");
        if (tokenCookie != null) {
            String token = tokenCookie.getValue();
            String username = jwtService.extractUserName(token);

            UserEntity userEntity = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            List<Room> roomsForCurrentUser = roomRepository.findByCurrentGuestId(userEntity.getId());
            model.addAttribute("rooms", roomsForCurrentUser);
        }
        return "rooms";
    }

    @PostMapping("/cancel-reservation")
    public String cancelReservation(@RequestParam("roomId") Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room.setCurrentGuestId(null);
        room.setOccupied(false);
        roomRepository.save(room);
        return "redirect:/user-panel";
    }
}



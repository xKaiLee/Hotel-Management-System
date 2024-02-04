package com.example.hotel.service;

import com.example.hotel.model.Room;

import java.util.List;

public interface RoomService {
    List<Room> getAllRooms();
    Room getRoom(long id);
    Room addRoom(Room room);
    void updateRoom(long id, Room updatedRoom);
    void deleteRoom(long id);
    List<Room> getAvailableRooms();
    void reserveRoom(long roomID, long userID);
}

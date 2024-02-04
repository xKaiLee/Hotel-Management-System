package com.example.hotel.service.imp;

import java.util.List;

import com.example.hotel.repository.UserRepository;
import com.example.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotel.model.Room;
import com.example.hotel.repository.RoomRepository;

@Service
public class RoomServiceImp implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoom(long id) {
        return roomRepository.findById(id).orElseThrow();
    }

    @Override
    public Room addRoom(Room room) {
        int roomNumber = room.getRoomNumber();

        if (roomRepository.existsByRoomNumber(roomNumber)) {
            throw new IllegalArgumentException("Room already exists");}
            return roomRepository.save(room);
    }

    @Override
    public void updateRoom(long id, Room updatedRoom) {
        Room existingRoom = roomRepository.findById(id).orElseThrow();
        existingRoom.setRoomNumber(updatedRoom.getRoomNumber());
        existingRoom.setFloorNumber(updatedRoom.getFloorNumber());
        existingRoom.setPrice(updatedRoom.getPrice());
        existingRoom.setBeds(updatedRoom.getBeds());
        if(userRepository.existsById(updatedRoom.getCurrentGuestId())){
            existingRoom.setCurrentGuestId(updatedRoom.getCurrentGuestId());
            if(existingRoom.getCurrentGuestId()!=null){
                existingRoom.setOccupied(true);
            }
            else{
                existingRoom.setOccupied(false);
            }
        } else if (updatedRoom.getCurrentGuestId() == 0) {
            existingRoom.setCurrentGuestId(null);
            existingRoom.setOccupied(false);
        } else{
            existingRoom.setCurrentGuestId(null);
            existingRoom.setOccupied(false);
        }
        roomRepository.save(existingRoom);
    }

    @Override
    public void reserveRoom(long roomID, long userID){
        Room existingRoom = roomRepository.findById(roomID).orElseThrow();
        existingRoom.setCurrentGuestId(userID);
        existingRoom.setOccupied(true);
        roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> getAvailableRooms() {
        return roomRepository.findByIsOccupiedFalse();
    }
}

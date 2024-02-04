package com.example.hotel.repository;

import com.example.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByRoomNumber(int roomNumber);
    List<Room> findByIsOccupiedFalse();
    Optional<Room> findById(Long id);
    List<Room> findByCurrentGuestId(Long guestId);
}

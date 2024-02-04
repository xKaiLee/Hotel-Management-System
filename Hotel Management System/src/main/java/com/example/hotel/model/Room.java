package com.example.hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "room_number")
    @NotNull(message = "Numer pokoju nie może być pusty.")
    @Min(value = 1, message = "Numer pokoju musi być większy niż 0")
    private int roomNumber;

    @Column(name = "beds")
    @NotNull(message = "Łóżka nie mogą być puste")
    @Min(value = 1, message = "Łóżek musi być więcej niż 0")
    private int beds;

    @Column(name = "price")
    @NotNull(message = "Cena nie może być pusta")
    @Min(value = 1, message = "Cena musi być większa niż 0")
    private double price;

    @Column(name = "floor_number")
    @NotNull(message = "Numer pokoju nie może być pusty.")
    @Min(value = 1, message = "Numer piętra musi być większy niż 1")
    private int floorNumber;

    @Column(name = "is_occupied")
    private boolean isOccupied;

    @Column(name = "current_guest_id")
    private Long currentGuestId;

    public Room(){}

    public Room(long id, int roomNumber, int beds, double price, int floorNumber) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.beds = beds;
        this.price = price;
        this.floorNumber = floorNumber;
        this.isOccupied = false;
        this.currentGuestId = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public boolean getIsOccupied() {return isOccupied;}

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
    public Long getCurrentGuestId() {
        return currentGuestId;
    }

    public void setCurrentGuestId(Long currentGuestId) {
        this.currentGuestId = currentGuestId;
    }
}

package com.upgrad.hotel_room_booking.Booking.repo;

import com.upgrad.hotel_room_booking.Booking.entity.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingInfoEntity, Integer> {
}
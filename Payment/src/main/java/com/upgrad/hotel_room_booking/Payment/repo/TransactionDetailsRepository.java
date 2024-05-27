package com.upgrad.hotel_room_booking.Payment.repo;

import com.upgrad.hotel_room_booking.Payment.entity.TransactionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetailsEntity, Integer> {
}

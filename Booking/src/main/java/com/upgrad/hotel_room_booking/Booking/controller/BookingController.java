package com.upgrad.hotel_room_booking.Booking.controller;

import com.upgrad.hotel_room_booking.Booking.dto.BookingRequestDTO;
import com.upgrad.hotel_room_booking.Booking.dto.ErrorResponse;
import com.upgrad.hotel_room_booking.Booking.dto.PaymentRequestDTO;
import com.upgrad.hotel_room_booking.Booking.entity.BookingInfoEntity;
import com.upgrad.hotel_room_booking.Booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hotel/booking")
@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingInfoEntity> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        BookingInfoEntity bookingInfoEntity = bookingService.createBooking(bookingRequestDTO);
        return new ResponseEntity<BookingInfoEntity>(bookingInfoEntity, HttpStatus.CREATED);
    }

    @PostMapping("/{bookingId}/transaction")
    public ResponseEntity<?> processPayment(
            @PathVariable int bookingId,
            @RequestBody PaymentRequestDTO paymentRequestDTO) {
        if (!"UPI".equalsIgnoreCase(paymentRequestDTO.getPaymentMode()) &&
                !"CARD".equalsIgnoreCase(paymentRequestDTO.getPaymentMode())) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid mode of payment", 400);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (bookingId != paymentRequestDTO.getBookingId()) {
            ErrorResponse errorResponse = new ErrorResponse("Invalid Booking Id ", 400);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            BookingInfoEntity updatedBooking = bookingService.processPayment(bookingId, paymentRequestDTO);
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

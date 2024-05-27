package com.upgrad.hotel_room_booking.Booking.service;

import com.upgrad.hotel_room_booking.Booking.dto.BookingRequestDTO;
import com.upgrad.hotel_room_booking.Booking.dto.PaymentRequestDTO;
import com.upgrad.hotel_room_booking.Booking.entity.BookingInfoEntity;
import com.upgrad.hotel_room_booking.Booking.repo.BookingRepository;
import com.upgrad.hotel_room_booking.Booking.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class BookingService {

    private final RestTemplate restTemplate;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    private Utility utility;

    public BookingInfoEntity createBooking(BookingRequestDTO bookingRequestDTO) {
        BookingInfoEntity booking = new BookingInfoEntity();
        booking.setFromDate(bookingRequestDTO.getFromDate());
        booking.setToDate(bookingRequestDTO.getToDate());
        booking.setAadharNumber(bookingRequestDTO.getAadharNumber());
        booking.setNumOfRooms(bookingRequestDTO.getNumOfRooms());
        int roomPrice = 1000*bookingRequestDTO.getNumOfRooms();
        ArrayList<String> roomNumbers=utility.getRandomNumbers(bookingRequestDTO.getNumOfRooms());
        booking.setRoomNumbers(roomNumbers.toString());
        booking.setRoomPrice(roomPrice);
        booking.setTransactionId(0);
        booking.setBookedOn(new Date());

        return bookingRepository.save(booking);
    }

    public BookingInfoEntity processPayment(int bookingId, PaymentRequestDTO paymentRequestDTO) {
        Optional<BookingInfoEntity> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PaymentRequestDTO> requestEntity = new HttpEntity<>(paymentRequestDTO, headers);
            BookingInfoEntity booking = optionalBooking.get();

            ResponseEntity<Integer> responseEntity = restTemplate.postForEntity(
                    paymentServiceUrl + "/payment/transaction",
                    requestEntity,
                    Integer.class);
            booking.setTransactionId(responseEntity.getBody());
            return bookingRepository.save(booking);
        } else {
            throw new IllegalArgumentException("Booking not found with id: " + bookingId);
        }
    }
}

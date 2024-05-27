package com.upgrad.hotel_room_booking.Payment.controller;

import com.upgrad.hotel_room_booking.Payment.dto.PaymentRequestDTO;
import com.upgrad.hotel_room_booking.Payment.entity.TransactionDetailsEntity;
import com.upgrad.hotel_room_booking.Payment.service.TransactionDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/payment/transaction")
public class TransactionController {

    @Autowired
    private TransactionDetailsService transactionService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        TransactionDetailsEntity transaction = transactionService.createTransaction(paymentRequestDTO);
        return new ResponseEntity<>(transaction.getTransactionId(), HttpStatus.CREATED);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDetailsEntity> getTransactionById(@PathVariable int transactionId) {
        Optional<TransactionDetailsEntity> transactionDetails = transactionService.getTransactionById(transactionId);

        if (transactionDetails.isPresent()) {
            return new ResponseEntity<>(transactionDetails.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

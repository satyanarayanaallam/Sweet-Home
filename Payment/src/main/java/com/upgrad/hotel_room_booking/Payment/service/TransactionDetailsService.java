package com.upgrad.hotel_room_booking.Payment.service;

import com.upgrad.hotel_room_booking.Payment.dto.PaymentRequestDTO;
import com.upgrad.hotel_room_booking.Payment.entity.TransactionDetailsEntity;
import com.upgrad.hotel_room_booking.Payment.repo.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionDetailsService {

    @Autowired
    private TransactionDetailsRepository transactionRepository;

    public TransactionDetailsEntity createTransaction(PaymentRequestDTO paymentRequestDTO) {
        TransactionDetailsEntity transaction = new TransactionDetailsEntity();
        transaction.setPaymentMode(paymentRequestDTO.getPaymentMode());
        transaction.setBookingId(paymentRequestDTO.getBookingId());
        transaction.setUpiId(paymentRequestDTO.getUpiId());
        transaction.setCardNumber(paymentRequestDTO.getCardNumber());

        return transactionRepository.save(transaction);
    }

    public Optional<TransactionDetailsEntity> getTransactionById(int transactionId) {
        return transactionRepository.findById(transactionId);
    }
}

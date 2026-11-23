package com.transaction.service;

import com.transaction.dto.TransactionRequest;
import com.transaction.dto.TransactionResponse;
import com.transaction.entity.Transaction;
import com.transaction.enums.TransactionStatus;
import com.transaction.repository.TransactionRepository;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Transient
    public TransactionResponse createTransaction(TransactionRequest request) {
        if(!transactionRepository.existsById(request.getFromWalletId())){
            throw new RuntimeException("WalletId" +request.getFromWalletId()+ "not found");
        }

        if(!transactionRepository.existsById(request.getToWalletId())){
            throw new RuntimeException("WalletId" +request.getToWalletId()+ "not found");
        }
        if(request.getAmount()<=0){
            throw new RuntimeException("Amount must be greater than 0");
        }

        Transaction transaction = Transaction.builder()
                .fromWalletId(request.getFromWalletId())
                .toWalletId(request.getToWalletId())
                .amount(request.getAmount())
                .transactionType(request.getTransactionType())
                .transactionStatus(TransactionStatus.SUCCESS)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponse(savedTransaction);
    }

    public List<TransactionResponse> getAllTransactions(){
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TransactionResponse getTransactionById(Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction with id" +transactionId+" not found"));
        return mapToResponse(transaction);

    }

    public  List<TransactionResponse> getWalletTransaction(Long fromWalletId){
        return transactionRepository.findById(fromWalletId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TransactionResponse mapToResponse(Transaction transaction){
        TransactionResponse response = TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .fromWalletId(transaction.getFromWalletId())
                .toWalletId(transaction.getToWalletId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .createdAt(transaction.getCreatedAt())
                .build();
        return response;
    }
}

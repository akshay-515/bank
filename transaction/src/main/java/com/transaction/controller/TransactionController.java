package com.transaction.controller;

import com.transaction.dto.TransactionRequest;
import com.transaction.dto.TransactionResponse;
import com.transaction.entity.Transaction;
import com.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody
                                                 TransactionRequest request){
        TransactionResponse createTransaction = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTransaction);
    }

    @GetMapping("transaction")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(){
        List<TransactionResponse> transactions = transactionService.getAllTransactions();
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

    @GetMapping("transactionid/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long transactionId){
        TransactionResponse transactionResponse = transactionService.getTransactionById(transactionId);
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }

    @GetMapping("walletid/{id}")
    public ResponseEntity<List<TransactionResponse>> getWalletTransaction(@PathVariable Long fromWalletId){
        List<TransactionResponse> transactionResponse = transactionService.getWalletTransaction(fromWalletId);
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }
}

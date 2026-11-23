package com.transaction.dto;

import com.transaction.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private Long fromWalletId;
    private Long toWalletId;
    private Double amount;
    private TransactionType transactionType;
}

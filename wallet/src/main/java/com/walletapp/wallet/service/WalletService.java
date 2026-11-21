package com.walletapp.wallet.service;

import com.walletapp.wallet.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public String testing() {
        return "hai this is working";
    }
}

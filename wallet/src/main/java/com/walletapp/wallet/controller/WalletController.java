package com.walletapp.wallet.controller;

import com.walletapp.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/get")
    public String testing() {
        return walletService.testing();
    }
}

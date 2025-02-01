package com.walletApp.wallet_application.controller;


import com.walletApp.wallet_application.entity.Account;
import com.walletApp.wallet_application.service.AccountDto;
import com.walletApp.wallet_application.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(
            @RequestHeader("Authorization") String token
    ) {
        String jwtToken = token.replace("Bearer ", "");
        return ResponseEntity.ok(accountService.getAllAccounts(jwtToken));
    }

    @PostMapping("/add")
    public ResponseEntity<?> createAccount(
            @RequestHeader("Authorization") String token,
            @RequestBody AccountDto accountDto) {
        String jwtToken = token.replace("Bearer ", "");
        Account newAccount = accountService.createAccount(jwtToken, accountDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
    }
}

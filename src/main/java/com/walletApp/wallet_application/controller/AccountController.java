package com.walletApp.wallet_application.controller;


import com.walletApp.wallet_application.entity.Account;
import com.walletApp.wallet_application.service.dto.AccUpdateDto;
import com.walletApp.wallet_application.service.dto.AccountDto;
import com.walletApp.wallet_application.service.AccountService;
import com.walletApp.wallet_application.service.dto.AccountResponseBody;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private String jwtToken;
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(@NotNull @RequestHeader("Authorization") String token) {
         jwtToken = token.replace("Bearer ", "");
        return ResponseEntity.ok(accountService.getAllAccounts(jwtToken));
    }

    @PostMapping("/add")
    public ResponseEntity<?> createAccount(@NotNull @RequestHeader("Authorization") String token, @RequestBody AccountDto accountDto) {
         jwtToken = token.replace("Bearer ", "");
        Account newAccount = accountService.createAccount(jwtToken, accountDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@NotNull @RequestHeader("Authorization") String token, @PathVariable Long id) {
         jwtToken = token.replace("Bearer ","");

         Account details = accountService.getAccountDetails(jwtToken, id);
        AccountResponseBody responseBody = new AccountResponseBody(details.getType(), details.getAccountNumber(), details.getBalance(), details.getCreatedAt());
         return ResponseEntity.status(HttpStatus.FOUND).body(responseBody);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccountByAccountId(@NotNull @RequestHeader("Authorization") String token, @PathVariable String accountNumber){

        jwtToken = token.replace("Bearer ","");
        Account details = accountService.getAccountDetailsByAccountNumber(jwtToken, accountNumber);
        AccountResponseBody responseBody = new AccountResponseBody(details.getType(), details.getAccountNumber(), details.getBalance(), details.getCreatedAt());
        return ResponseEntity.status(HttpStatus.FOUND).body(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@NotNull @RequestHeader("Authorization") String token,
                                           @PathVariable Long id,
                                           @RequestBody AccUpdateDto dto) {
        jwtToken = token.replace("Bearer ", "");
        Account updated = accountService.updateAccount(jwtToken, id, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@NotNull @RequestHeader("Authorization") String token,
                                           @PathVariable Long id) {

        jwtToken = token.replace("Bearer ", "");
        accountService.deleteAccount(jwtToken, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Delete Successfully");
    }

}

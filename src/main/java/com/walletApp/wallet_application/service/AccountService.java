package com.walletApp.wallet_application.service;

import com.walletApp.wallet_application.com.jwtAuthentication.AuthenticationConfig.JwtService;
import com.walletApp.wallet_application.com.jwtAuthentication.User;
import com.walletApp.wallet_application.com.jwtAuthentication.dao.UserRepository;
import com.walletApp.wallet_application.entity.Account;
import com.walletApp.wallet_application.entity.AccountType;
import com.walletApp.wallet_application.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import org.jetbrains.annotations.*;
import static com.walletApp.wallet_application.service.ServiceUtilities.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    public List<Account> getAllAccounts(String token) {
        // extract username from token

         String username = getUsername(token,jwtService);

//         retrieve user by username/email
        User user = userRepository.findByEmail(username)
                .orElseThrow(
                        ()->new UsernameNotFoundException("username not found")
                );

        return repository.findByUserId(user.getId());
    }

    public Account createAccount(String token, @NotNull AccountDto accountDto) {
        String username = getUsername(token, jwtService);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        // Ensure accountNumber does not exist already
        repository.findByAccountNumber(accountDto.getAccountNumber())
                .ifPresent(account -> {
                    throw new RuntimeException("Account Number already exists");
                });

        // Check if accountType is valid
        String accountTypeString = accountDto.getAccountType();
        if (accountTypeString == null || accountTypeString.isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be null or empty");
        }

        // Convert to uppercase to match the enum and validate it
        AccountType accountType = AccountType.valueOf(accountTypeString.toUpperCase());

        // Create the account
        Account account = Account.builder()
                .accountNumber(accountDto.getAccountNumber())
                .type(accountType) // Set the valid AccountType enum
                .user(user) // Link the account to the user
                .balance(accountDto.getBalance())
                .status(true)
                .build();

        return repository.save(account); // Save the new account
    }

    public Account getAccountDetails(String token, Long id) {
        String username = getUsername(token, jwtService);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Account getAccount = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Account not found!"));
        if (user.getId() != getAccount.getUser().getId()){
            throw new IllegalArgumentException("Account not associated with this user");
        }
        return getAccount;
    }

    public Account updateAccount(String token, Long id,@NotNull AccUpdateDto dto) {
        String username = getUsername(token, jwtService);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Account acc = repository.findById(id)
                .orElseThrow(()->new RuntimeException("Account not found"));


        acc.setBalance(dto.getBalance());

        return repository.save(acc);
    }

    public void deleteAccount(String token, Long id) {
        String username = getUsername(token, jwtService);
        User user = userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not found"));

        repository.deleteById(id);
    }
}

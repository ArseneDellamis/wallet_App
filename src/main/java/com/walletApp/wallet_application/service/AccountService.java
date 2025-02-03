package com.walletApp.wallet_application.service;

import com.walletApp.wallet_application.com.jwtAuthentication.AuthenticationConfig.JwtService;
import com.walletApp.wallet_application.com.jwtAuthentication.User;
import com.walletApp.wallet_application.com.jwtAuthentication.dao.UserRepository;
import com.walletApp.wallet_application.entity.Account;
import com.walletApp.wallet_application.entity.AccountType;
import com.walletApp.wallet_application.repository.AccountRepository;
import com.walletApp.wallet_application.dto.AccUpdateDto;
import com.walletApp.wallet_application.dto.AccountDto;
import lombok.RequiredArgsConstructor;
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
    private String username;

    public List<Account> getAllAccounts(String token) {
        // extract username from token
        username = getUsername(token,jwtService);
//         retrieve user by username/email
        User user = checkIfUserExists(username, userRepository);
        return repository.findByUserId(user.getId());
    }

    public Account createAccount(String token, @NotNull AccountDto accountDto) {
         username = getUsername(token, jwtService);
        User user = checkIfUserExists(username, userRepository);

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
        username = getUsername(token, jwtService);
        User user = checkIfUserExists(username, userRepository);
        Account getAccount = checkIfAccountExistsById(id, repository);
//        this is to prevent user to access irrelevant accounts
        checkIfUserId_is_same_as_UserIdRegisteredOnTheAccount(user.getId(), getAccount.getUser().getId());
        return getAccount;
    }

    public Account updateAccount(String token, Long id,@NotNull AccUpdateDto dto) {
        username = getUsername(token, jwtService);
        User user = checkIfUserExists(username, userRepository);

        Account acc = checkIfAccountExistsById(id, repository);

//        this is to avoid user update other accounts not for him/her
        checkIfUserId_is_same_as_UserIdRegisteredOnTheAccount(user.getId(), acc.getUser().getId());
        acc.setBalance(dto.getBalance());

        return repository.save(acc);
    }

    public void deleteAccount(String token, Long id) {
        username = getUsername(token, jwtService);
        User user = checkIfUserExists(username, userRepository);

        Account acc = repository.findById(id)
                .orElseThrow(()->new RuntimeException("Account not found"));

        if (acc.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("Account does not belong to this user");
        }

        repository.deleteById(id);
    }

    public Account getAccountDetailsByAccountNumber(String token, String accountNumber) {

        username = getUsername(token, jwtService);
        User user = checkIfUserExists(username, userRepository);

        Account checked = checkIfAccountExistsByAccountNumber(accountNumber, repository);

//        now check if account belongs to this user

        checkIfUserId_is_same_as_UserIdRegisteredOnTheAccount(user.getId(), checked.getUser().getId());
        return checked;
    }
}

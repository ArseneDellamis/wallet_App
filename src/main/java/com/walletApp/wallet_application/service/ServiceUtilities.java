package com.walletApp.wallet_application.service;

import com.walletApp.wallet_application.com.jwtAuthentication.AuthenticationConfig.JwtService;
import com.walletApp.wallet_application.com.jwtAuthentication.User;
import com.walletApp.wallet_application.com.jwtAuthentication.dao.UserRepository;
import com.walletApp.wallet_application.entity.Account;
import com.walletApp.wallet_application.entity.Budget;
import com.walletApp.wallet_application.exceptions.AccountNotFoundException;
import com.walletApp.wallet_application.repository.AccountRepository;
import com.walletApp.wallet_application.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RequiredArgsConstructor
public class ServiceUtilities {



    public static String getUsername (String token, @NotNull JwtService service) {

          return service.extractUsername(token);
    }

    public static User checkIfUserExists(String username, @NotNull UserRepository repo){

        return repo.findByEmail(username).orElseThrow(
                ()-> new UsernameNotFoundException("Username not found")
        );
    }

    public static Account checkIfAccountExistsById(Long id,@NotNull AccountRepository accountRepository){

        return accountRepository.findById(id).orElseThrow(
                ()-> new AccountNotFoundException("Account Not Found")
        );
    }

    public static Account checkIfAccountExistsByAccountNumber(String accountNumber, @NotNull AccountRepository accountRepository){

        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new AccountNotFoundException("Account Not Found"));
    }

    public static void checkIfUserId_is_same_as_UserIdRegisteredOnTheAccount(Long userId, Long accountUserId) {
        if (userId .equals(accountUserId)){
            throw new IllegalArgumentException("Account not associated with this user");
        }
    }
    public static void checkIfUserId_is_same_as_UserIdRegisteredOnTheBudget(Long userId, Long budgetUserId) {
        if (userId .equals(budgetUserId)){
            throw new IllegalArgumentException("Account not associated with this user");
        }
    }

    public static Budget checkIfBudgetExists(Long id, BudgetRepository repository) {

        return repository.findById(id).orElseThrow(
                ()-> new RuntimeException("Budget Not Found")
        );
    }
}

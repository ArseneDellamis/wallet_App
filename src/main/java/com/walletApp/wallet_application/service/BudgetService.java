package com.walletApp.wallet_application.service;

import com.walletApp.wallet_application.com.jwtAuthentication.AuthenticationConfig.JwtService;
import com.walletApp.wallet_application.com.jwtAuthentication.User;
import com.walletApp.wallet_application.com.jwtAuthentication.dao.UserRepository;
import com.walletApp.wallet_application.entity.Budget;
import com.walletApp.wallet_application.repository.AccountRepository;
import com.walletApp.wallet_application.repository.BudgetRepository;
import com.walletApp.wallet_application.service.dto.BudgetDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.walletApp.wallet_application.service.ServiceUtilities.*;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BudgetRepository repository;

    private String username;

    public Budget createBudget(String token, @NotNull BudgetDto dto) {

        username = getUsername(token,jwtService);

        User user = checkIfUserExists(username, userRepository);

        var budget = Budget.builder()
                .user(user)
                .limitAmount(dto.getLimit_amount())
                .build();

        return repository.save(budget);
    }

    public List<Budget> budgetListByUser(String token) {
        username = getUsername(token, jwtService);
        User user = checkIfUserExists(username, userRepository);

        return repository.findAllByUserId(user.getId());
    }

    public Budget getBudgetDetails(String token, Long id) {

        username = getUsername(token, jwtService);
        User user = checkIfUserExists(username, userRepository);

        Budget getBudget =repository.findById(id).orElseThrow(
                ()-> new RuntimeException("Budget Not Found")
        );

        if (user.getId() != getBudget.getUser().getId()) {
            throw new IllegalArgumentException("Budget not associated with this user");
        }

        return getBudget;
    }

    public Budget updateBudget(String token, Long id, @NotNull BudgetDto dto) {
        username = getUsername(token,jwtService);
        User user = checkIfUserExists(username, userRepository);

        Budget getBudget =repository.findById(id).orElseThrow(
                ()-> new RuntimeException("Budget Not Found")
        );

        if (user.getId() != getBudget.getUser().getId()) {
            throw new IllegalArgumentException("Budget not associated with this user");
        }

        getBudget.setLimitAmount(dto.getLimit_amount());

        return repository.save(getBudget);
    }

    public void deleteBudget(String token, Long id) {
//        get username/email from token
        username = getUsername(token,jwtService);
//        check if the user exists using username/email
        User user = checkIfUserExists(username, userRepository);
//        checks if budget exists
        Budget getBudget =repository.findById(id).orElseThrow(
                ()-> new RuntimeException("Budget Not Found")
        );
//        check if the budget is for that user
        if (user.getId() != getBudget.getUser().getId()) {
            throw new IllegalArgumentException("Budget not associated with this user");
        }

        repository.deleteById(id);
    }

}

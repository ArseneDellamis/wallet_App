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

        User user = userRepository.findByEmail(username)
                .orElseThrow(
                        ()-> new UsernameNotFoundException("user not found")
                );

        var budget = Budget.builder()
                .user(user)
                .limitAmount(dto.getLimit_amount())
                .build();

        return repository.save(budget);
    }


}

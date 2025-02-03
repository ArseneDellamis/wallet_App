package com.walletApp.wallet_application.dto;

import com.walletApp.wallet_application.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseBody {
    private AccountType account_type ;
    private String account_number;
    private Double balance;
    private LocalDateTime created_at;

}

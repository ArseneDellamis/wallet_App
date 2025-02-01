package com.walletApp.wallet_application.entity;

import com.walletApp.wallet_application.com.jwtAuthentication.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "budget")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double limitAmount;
    private LocalDate startDate;
    private LocalDate endDate;

    // Many budgets belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}


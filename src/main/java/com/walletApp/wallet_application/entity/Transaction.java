package com.walletApp.wallet_application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private LocalDateTime transactionDate;
    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // incomes or expenses

    // Many transactions belong to one account
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


    // Optional association to Category or Subcategory
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}

package com.walletApp.wallet_application.entity;


import com.walletApp.wallet_application.com.jwtAuthentication.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType type; // saying account , debit

    @Column(nullable = false, unique = true)
    private String accountNumber;

//    Many accounts belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    One account has many transactions
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    private Double balance;

    private boolean status; // account is active/ inactive or closed.

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;// any change made to an account

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }



}

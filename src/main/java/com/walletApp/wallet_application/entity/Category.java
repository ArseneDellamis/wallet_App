package com.walletApp.wallet_application.entity;

import com.walletApp.wallet_application.com.jwtAuthentication.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Many categories belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // A category can have multiple subcategories
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Subcategory> subcategories = new ArrayList<>();

    // Optionally, transactions can be directly linked to a category
    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions = new ArrayList<>();

    // getters and setters
}


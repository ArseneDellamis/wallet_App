package com.walletApp.wallet_application.repository;

import com.walletApp.wallet_application.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByUserId(Long id);
    List<Budget> findAllByUserId(Long id);

}

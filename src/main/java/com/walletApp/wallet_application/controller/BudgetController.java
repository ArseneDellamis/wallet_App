package com.walletApp.wallet_application.controller;

import com.walletApp.wallet_application.entity.Budget;
import com.walletApp.wallet_application.service.BudgetService;
import com.walletApp.wallet_application.dto.BudgetDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService service;
    private String jwtToken;

    @GetMapping
    public ResponseEntity<?> getAllBudget(
            @NotNull @RequestHeader("Authorization") String token
    ) {
        jwtToken = token.replace("Bearer ", "");
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(service.budgetListByUser(jwtToken));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(
            @NotNull @RequestHeader("Authorization") String token,
            @PathVariable Long id) {
        jwtToken = token.replace("Bearer ","");
        Budget getBudgetDetailsById = service.getBudgetDetails(jwtToken, id);
        return ResponseEntity.status(HttpStatus.FOUND).body(getBudgetDetailsById);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createBudget(
            @NotNull @RequestHeader("Authorization") String token,
            @RequestBody BudgetDto dto) {
        jwtToken = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createBudget(jwtToken, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@NotNull @RequestHeader("Authorization") String token,
                                           @PathVariable Long id,
                                           @RequestBody BudgetDto updateDto) {
        jwtToken = token.replace("Bearer ", "");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.updateBudget(jwtToken, id, updateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBudget(@NotNull @RequestHeader("Authorization") String token,
                                           @PathVariable Long id) {
        jwtToken = token.replace("Bearer ", "");
        service.deleteBudget(jwtToken, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Delete Successfully");
    }

}

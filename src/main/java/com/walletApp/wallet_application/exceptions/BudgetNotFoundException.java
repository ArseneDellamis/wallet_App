package com.walletApp.wallet_application.exceptions;

public class BudgetNotFoundException extends RuntimeException{

    public BudgetNotFoundException(String message) {
        super(message);
    }
}

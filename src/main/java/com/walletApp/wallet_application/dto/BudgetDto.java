package com.walletApp.wallet_application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetDto {

    private Double limit_amount;
    private String startDate;
    private String endDate;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    // Convert startDate from String to LocalDate
    public LocalDate getStartDateAsLocalDate() {
        return (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate, formatter) : null;
    }

    // Convert endDate from String to LocalDate
    public LocalDate getEndDateAsLocalDate() {
        return (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate, formatter) : null;
    }


}

package com.walletApp.wallet_application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponseBody {

    private Double limit_amount;
    private String startDate;
    private String endDate;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Convert startDate to LocalDateTime
    public LocalDateTime getStartDateAsLocalDateTime() {
        return (startDate != null && !startDate.isEmpty()) ? LocalDateTime.parse(startDate, formatter) : null;
    }

    // Convert endDate to LocalDateTime
    public LocalDateTime getEndDateAsLocalDateTime() {
        return (endDate != null && !endDate.isEmpty()) ? LocalDateTime.parse(endDate, formatter) : null;
    }
}

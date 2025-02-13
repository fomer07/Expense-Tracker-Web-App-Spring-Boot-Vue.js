package com.example.expensetrackerwebapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseDTO {

    private String title;
    private BigDecimal amount;
    private LocalDate date;

}

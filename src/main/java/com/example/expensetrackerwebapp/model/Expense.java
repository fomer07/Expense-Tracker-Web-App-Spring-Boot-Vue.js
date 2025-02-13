package com.example.expensetrackerwebapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private BigDecimal amount;
    private LocalDate date; // TODO use LocalDateTime for store time, as hours and seconds.

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

package com.example.expensetrackerwebapp.repository;

import com.example.expensetrackerwebapp.model.Expense;
import com.example.expensetrackerwebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
}

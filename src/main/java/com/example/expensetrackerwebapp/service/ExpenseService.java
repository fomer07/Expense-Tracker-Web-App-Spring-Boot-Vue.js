package com.example.expensetrackerwebapp.service;

import com.example.expensetrackerwebapp.model.Expense;
import com.example.expensetrackerwebapp.model.User;
import com.example.expensetrackerwebapp.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(Expense expense) {
        expense.setDate(LocalDate.now()); // TODO may be found another good way to assign date for expense
        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByUser(User user) {
        return expenseRepository.findByUser(user);
    }

    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    public Expense updateExpense(Expense expense) {
        expense.setDate(LocalDate.now()); // TODO may be found another good way to assign date for expense
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
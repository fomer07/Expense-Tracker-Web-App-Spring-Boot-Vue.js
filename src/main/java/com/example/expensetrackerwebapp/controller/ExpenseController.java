package com.example.expensetrackerwebapp.controller;

import com.example.expensetrackerwebapp.dto.ExpenseDTO;
import com.example.expensetrackerwebapp.model.Expense;
import com.example.expensetrackerwebapp.model.User;
import com.example.expensetrackerwebapp.security.JwtUtil;
import com.example.expensetrackerwebapp.service.ExpenseService;
import com.example.expensetrackerwebapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public ExpenseController(ExpenseService expenseService,
                             UserService userService,
                             JwtUtil jwtUtil) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    //TODO returns user's password !?!?!?!?
    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestHeader("Authorization") String token,
                                              @RequestBody ExpenseDTO expenseDTO) {
        String email = jwtUtil.extractEmail(token.substring(7));
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense();
        expense.setTitle(expenseDTO.getTitle());
        expense.setAmount(expenseDTO.getAmount());
        expense.setDate(expenseDTO.getDate());
        expense.setUser(user);

        return ResponseEntity.ok(expenseService.addExpense(expense));
    }

    //TODO returns user's password !?!?!?!?
    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7));
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(expenseService.getExpensesByUser(user));
    }

    //TODO returns user's password !?!?!?!?
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id,
                                                 @RequestBody ExpenseDTO expenseDTO,
                                                 @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7));
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = expenseService.getExpenseById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().equals(user)) {
            return ResponseEntity.status(403).build();
        }

        expense.setTitle(expenseDTO.getTitle());
        expense.setAmount(expenseDTO.getAmount());
        expense.setDate(expenseDTO.getDate());

        return ResponseEntity.ok(expenseService.updateExpense(expense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id,
                                                @RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7));
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = expenseService.getExpenseById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().equals(user)) {
            return ResponseEntity.status(403).body("You are not authorized to delete this expense");
        }

        expenseService.deleteExpense(id);
        return ResponseEntity.ok("Expense deleted successfully.");
    }
}

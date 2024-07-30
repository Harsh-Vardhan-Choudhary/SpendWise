package com.harsh.SpendWise.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsh.SpendWise.model.Expense;
import com.harsh.SpendWise.repository.ExpenseRepository;
import com.harsh.SpendWise.service.ExpenseService;


@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    //All expenses made by current user (this will only include expenses added by current user)
    @GetMapping("/expenses/user/{userId}")
    List<Expense> getExpensesMadeByUserID(@PathVariable Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    //Total expenses of the user (expenses self payed + shared in other expenses payed by other users)
    @GetMapping("/expenses/user/{userId}/total")
    public ResponseEntity<List<Expense>> getAllExpensesForUser(@PathVariable Long userId) {
        List<Expense> expenses = expenseService.getAllExpensesForUser(userId);
        return ResponseEntity.ok(expenses);
    }

    @PostMapping("/expenses")
    ResponseEntity<Expense> createExpense(@Validated @RequestBody Expense expense) throws URISyntaxException {
        Expense result = expenseRepository.save(expense);
        return ResponseEntity.created(new URI("/api/expenses/" + result.getId())).body(result);
    }

    @PutMapping("/expenses/{id}")
    ResponseEntity<Expense> updateExpense(@PathVariable Long id, @Validated @RequestBody Expense expense) {
        if (!expenseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        expense.setId(id);
        Expense updatedExpense = expenseRepository.save(expense);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/expenses/{id}")
    ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        expenseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //amount lent to other user and amount borrowed from other user by current user
    @GetMapping("/expenses/user/{userId}/balance")
    public ResponseEntity<Map<String, Map<Long, Double>>> getLentAndBorrowedAmounts(@PathVariable Long userId) {
        Map<String, Map<Long, Double>> result = expenseService.calculateLentAndBorrowed(userId);
        return ResponseEntity.ok(result);
    }
}

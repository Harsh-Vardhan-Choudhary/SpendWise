package com.harsh.SpendWise.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsh.SpendWise.model.Expense;
import com.harsh.SpendWise.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Map<String, Map<Long, Double>> calculateLentAndBorrowed(Long userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        
        Map<Long, Double> lentAmounts = new HashMap<>();
        Map<Long, Double> borrowedAmounts = new HashMap<>();
        
        for (Expense expense : expenses) {
            Long expenseUserId = expense.getUser() != null ? expense.getUser().getId() : null;
            
            if (expenseUserId != null && expenseUserId.equals(userId)) {

                // Current user created the expense, they lent money to others
                for (Map.Entry<Long, Double> entry : expense.getUserAmounts().entrySet()) {
                    Long otherUserId = entry.getKey();
                    Double amount = entry.getValue();
                    
                    if (!otherUserId.equals(userId)) {
                        lentAmounts.put(otherUserId, lentAmounts.getOrDefault(otherUserId, 0.0) + amount);
                    }
                }

            } 
            
            else {

                // Current user did not create the expense, they borrowed money from the creator
                for (Map.Entry<Long, Double> entry : expense.getUserAmounts().entrySet()) {
                    Long otherUserId = entry.getKey();
                    Double amount = entry.getValue();
                    
                    if (otherUserId.equals(userId) && expenseUserId != null) {
                        borrowedAmounts.put(expenseUserId, borrowedAmounts.getOrDefault(expenseUserId, 0.0) + amount);
                    }
                }
            }
        }

        // Handle shared expenses where the current user is part of the shared expense but not the creator
        List<Expense> sharedExpenses = expenseRepository.findAll();
        
        for (Expense expense : sharedExpenses) {
            Long expenseUserId = expense.getUser() != null ? expense.getUser().getId() : null;
            
            if (expenseUserId != null && !expenseUserId.equals(userId) && expense.getUserAmounts().containsKey(userId)) {
                Double amount = expense.getUserAmounts().get(userId);
                borrowedAmounts.put(expenseUserId, borrowedAmounts.getOrDefault(expenseUserId, 0.0) + amount);
            }
        }

        Map<String, Map<Long, Double>> result = new HashMap<>();
        result.put("lent", lentAmounts);
        result.put("borrowed", borrowedAmounts);

        return result;
    }

    public List<Expense> getAllExpensesForUser(Long userId) {
        // Retrieve all expenses
        List<Expense> allExpenses = expenseRepository.findAll();

        // Filter expenses where the user is part of the shared expense
        List<Expense> sharedExpenses = allExpenses.stream()
            .filter(expense -> expense.isShared() && expense.getUserAmounts().containsKey(userId))
            .collect(Collectors.toList());

        // Filter expenses created by the user and are not shared
        List<Expense> ownNonSharedExpenses = allExpenses.stream()
            .filter(expense -> !expense.isShared() && expense.getUser() != null && expense.getUser().getId().equals(userId))
            .collect(Collectors.toList());

        // Combine both lists
        sharedExpenses.addAll(ownNonSharedExpenses);

        return sharedExpenses;
    }
}

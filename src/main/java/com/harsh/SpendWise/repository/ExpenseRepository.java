package com.harsh.SpendWise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsh.SpendWise.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{
    
    List<Expense> findByUserId(Long userId);
}

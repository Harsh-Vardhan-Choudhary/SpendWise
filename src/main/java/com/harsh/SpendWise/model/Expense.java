package com.harsh.SpendWise.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "expense")
public class Expense {

    @Id
    private Long id;

    private String description;

    private Instant expenseDate;

    private double amount;

    private boolean isShared;

    @ElementCollection
    @CollectionTable(name = "expense_user", joinColumns = @JoinColumn(name = "expense_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "amount")
    private Map<Long, Double> userAmounts = new HashMap<>();
    
    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;
}

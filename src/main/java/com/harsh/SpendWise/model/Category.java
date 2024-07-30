package com.harsh.SpendWise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@NoArgsConstructor
@Entity
@Data       
@Table(name = "category")
public class Category {

    @Id
    private Long id;

    //travel, grocery
    @NonNull
    private String name;
    
}

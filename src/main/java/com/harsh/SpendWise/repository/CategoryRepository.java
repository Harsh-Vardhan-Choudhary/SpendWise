package com.harsh.SpendWise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsh.SpendWise.model.Category;

//crud repository / jpa repository : basically an interface to talk to database
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
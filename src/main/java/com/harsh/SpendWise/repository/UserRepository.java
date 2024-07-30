package com.harsh.SpendWise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsh.SpendWise.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}

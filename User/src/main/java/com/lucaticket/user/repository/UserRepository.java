package com.lucaticket.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucaticket.user.model.User;

public interface UserRepository extends JpaRepository<User, String>{
}
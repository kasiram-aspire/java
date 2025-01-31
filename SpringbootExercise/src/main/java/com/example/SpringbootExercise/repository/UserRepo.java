package com.example.SpringbootExercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringbootExercise.models.User;

@Repository
public interface UserRepo  extends JpaRepository<User, Long>{

}

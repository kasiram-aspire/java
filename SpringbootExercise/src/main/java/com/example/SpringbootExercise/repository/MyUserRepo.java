package com.example.SpringbootExercise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.SpringbootExercise.models.MyUser;

@Repository
public interface MyUserRepo extends JpaRepository<MyUser,Integer> {
	MyUser findByUsername(String username);
}

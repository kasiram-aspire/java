package com.example.SpringbootExercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringbootExercise.models.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

}

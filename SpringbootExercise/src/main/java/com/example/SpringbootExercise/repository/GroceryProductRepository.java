package com.example.SpringbootExercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringbootExercise.models.GroceryProduct;

@Repository
public interface GroceryProductRepository extends JpaRepository<GroceryProduct, Long> {

}
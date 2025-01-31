package com.example.SpringbootExercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringbootExercise.models.GrocessryProduct;

@Repository
public interface GrocessryProductRepo extends JpaRepository< GrocessryProduct, Long>{

}

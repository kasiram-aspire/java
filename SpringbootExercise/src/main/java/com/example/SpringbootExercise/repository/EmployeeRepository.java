package com.example.SpringbootExercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringbootExercise.models.Details;
import com.example.SpringbootExercise.models.Employee;
@Repository
public interface EmployeeRepository extends  JpaRepository<Employee,Integer>  {

}

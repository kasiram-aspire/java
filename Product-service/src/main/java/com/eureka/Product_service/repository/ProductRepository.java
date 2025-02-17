package com.eureka.Product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eureka.Product_service.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

}

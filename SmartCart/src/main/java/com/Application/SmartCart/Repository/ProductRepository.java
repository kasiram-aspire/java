package com.Application.SmartCart.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Application.SmartCart.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


	Set<Product> findByProductName(String productName);
	
}

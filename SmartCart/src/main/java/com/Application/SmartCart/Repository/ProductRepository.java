package com.Application.SmartCart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Application.SmartCart.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

}

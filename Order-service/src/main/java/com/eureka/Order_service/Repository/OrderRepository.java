package com.eureka.Order_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eureka.Order_service.Model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{
      
}

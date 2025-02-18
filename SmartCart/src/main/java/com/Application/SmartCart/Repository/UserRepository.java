package com.Application.SmartCart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Application.SmartCart.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	       
}

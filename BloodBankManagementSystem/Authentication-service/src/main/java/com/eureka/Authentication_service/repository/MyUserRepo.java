package com.eureka.Authentication_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eureka.Authentication_service.Model.MyUser;


@Repository
public interface MyUserRepo extends JpaRepository<MyUser,Integer> {
	MyUser findByUsername(String username);
}

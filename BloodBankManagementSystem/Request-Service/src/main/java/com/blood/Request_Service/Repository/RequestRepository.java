package com.blood.Request_Service.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blood.Request_Service.Model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {

	List<Request> findByDatelimit(LocalDate DATE);

}

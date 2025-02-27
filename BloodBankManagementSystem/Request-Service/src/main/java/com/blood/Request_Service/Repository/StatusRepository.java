package com.blood.Request_Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blood.Request_Service.Model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status,Long> {

}

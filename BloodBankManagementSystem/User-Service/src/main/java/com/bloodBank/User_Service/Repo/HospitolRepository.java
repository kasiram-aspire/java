package com.bloodBank.User_Service.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloodBank.User_Service.Model.Hospitol;

@Repository
public interface HospitolRepository extends JpaRepository<Hospitol,Long> {
	 Hospitol findByHospitolName(String donorName);
}

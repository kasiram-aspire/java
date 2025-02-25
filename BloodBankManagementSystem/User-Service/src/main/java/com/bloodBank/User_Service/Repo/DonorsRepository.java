package com.bloodBank.User_Service.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bloodBank.User_Service.Model.Donors;

@Repository
public interface DonorsRepository extends JpaRepository<Donors,Long> {

	 Donors findByDonorName(String donorName);
	       
}

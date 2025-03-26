package com.bloodBank.User_Service.ServiceImplementation;

import java.util.List;

import com.bloodBank.User_Service.Model.Donors;

public interface DonorServiceImplementation {
	public Donors addDonors(Donors donor);
	public List<Donors> getDonors();
	public Donors getById(Long id);
	public Donors update(Donors donor);
	public List<Donors> getDonorByBloodGroupName(String bloodgroup);
	public List<Donors> getDonorByBloodGroupNameAndAge(String bloodgroup, Integer age);
	public Donors getDonorByname(String name);	
}

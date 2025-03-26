package com.bloodBank.User_Service.ServiceImplementation;

import java.util.List;

import com.bloodBank.User_Service.Model.Hospitol;

public interface HospitolServiceImplementation  {
	public Hospitol addHospitol(Hospitol hospitol);
	public List<Hospitol> getHospitolDetails();
	public Hospitol getById(Long id);
	public  Hospitol updateHospitol(Hospitol hospitol);
	public Hospitol getHospitolByName(String hospitolname);

}

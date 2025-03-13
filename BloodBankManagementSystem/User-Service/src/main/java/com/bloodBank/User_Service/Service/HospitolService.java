package com.bloodBank.User_Service.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodBank.User_Service.Exceptions.DataAlreadyPresent;
import com.bloodBank.User_Service.Exceptions.IDNotFoundException;
import com.bloodBank.User_Service.Model.Donors;
import com.bloodBank.User_Service.Model.Hospitol;
import com.bloodBank.User_Service.Repo.HospitolRepository;

@Service
public class HospitolService {
     @Autowired
     HospitolRepository hospitolrepo;
     
	public Hospitol addHospitol(Hospitol hospitol) {
		
		 Hospitol hospitolName=hospitolrepo.findByHospitolName(hospitol.getHospitolName());
			if(hospitolName==null)
			{
				hospitolrepo.save(hospitol);
			}
			else
			{
				throw new DataAlreadyPresent("The donor:"+hospitol.getHospitolName()+"is already registered in db");
			}
			return hospitol;
	}

	public List<Hospitol> getHospitolDetails() {
		
		return hospitolrepo.findAll();
	}

	public Hospitol getById(Long id) {
		Hospitol hospitol=hospitolrepo.findById(id).orElse(null);
		if(hospitol==null)
		{
		    throw new IDNotFoundException("The id:"+id+"not found in db");
		}
		else
		{
		  return hospitolrepo.findById(id).get();
		}
	}

	public  Hospitol updateHospitol(Hospitol hospitol) {
		 
		 Hospitol hospitolName=hospitolrepo.findById(hospitol.getId()).orElse(null);
			if(hospitolName==null)
			{
			    throw new IDNotFoundException("The id:"+hospitol.getId()+"not found in db");
			}
			hospitolName.setHospitolName(hospitol.getHospitolName());
			hospitolName.setEmailId(hospitol.getEmailId());
			hospitolName.setHospitolAddress(hospitol.getHospitolAddress());
			hospitolName.setPhoneno(hospitol.getPhoneno());
			return(hospitolrepo.save(hospitolName));
	
	}

	public Hospitol getHospitolByName(String hospitolname) {
		 Hospitol hospitolName=hospitolrepo.findByHospitolName(hospitolname);
			if(hospitolName==null)
			{
				throw new DataAlreadyPresent("The "+hospitolname+"is Not present in data base");
			}
			else
			{
				return hospitolName; 
			}    
	}
	
}

package com.blood.Request_Service.ServiceImplementation;

import java.time.LocalDate;
import java.util.List;

import com.blood.Request_Service.Model.Request;
import com.blood.Request_Service.Model.Status;

public interface RequestImplementation {
	public Request addRequestFromHospitol(Request request);
	public List<Request> getAllHospitolRequest();
	public List<Request> getRequestByDate(LocalDate DATE);
	public Status setTheRequest(String name,String status,String hospitolname);
	public List<Status> getRequest();
	public String requestNotificationForBlood(String bloodGroup);
	
}

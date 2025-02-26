package com.bloodBank.User_Service.Exceptions;

public class DonorNotFoundException extends RuntimeException {
	public DonorNotFoundException(String message)
	{
		super(message);
	}

}

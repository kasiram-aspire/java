package com.bloodBank.User_Service.Exceptions;
public class DataAlreadyPresent extends RuntimeException {
    public DataAlreadyPresent(String message) {
        super(message);
    }
}

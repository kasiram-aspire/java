package com.bloodBank.NotificationService.ServiceImplementation;

import jakarta.mail.MessagingException;

public interface NotificationImplementation {
	public void sendEmail(String to, String subject, String message)throws MessagingException;
}

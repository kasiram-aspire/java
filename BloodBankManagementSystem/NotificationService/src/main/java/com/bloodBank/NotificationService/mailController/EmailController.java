package com.bloodBank.NotificationService.mailController;
import org.springframework.web.bind.annotation.*;

import com.bloodBank.NotificationService.notificationservice.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String message) throws MessagingException {
        emailService.sendEmail(to, subject, message);
        return "Email sent successfully to " + to;
    }
}
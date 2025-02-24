package com.Application.SmartCart.Controller;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.*;

import com.Application.SmartCart.Service.EmailService;

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


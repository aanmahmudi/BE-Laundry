package com.laundry.BE_Laundry.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	
	private final JavaMailSender mailSender;
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	public void sendOTPEmail(String to, String otp) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject("Your OTP Code");
			message.setText("Your OTP code is: " + otp + ".It will expire in 5 minutes.");
			
			mailSender.send(message);
			logger.info("OTP email sent Successfully to {}", to);
		} catch (Exception e) {
			logger.error("Failed to send OTP email to {}: {}", to, e.getMessage());
		}
	}
}

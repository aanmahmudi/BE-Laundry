package com.laundry.BE_Laundry.Service;

import java.time.LocalDateTime;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.laundry.BE_Laundry.Entity.Customer;
import com.laundry.BE_Laundry.Repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OTPService {
	
	private final CustomerRepository customerRepository;
	private final EmailService emailservice;
	private static final Logger logger = LoggerFactory.getLogger(OTPService.class);
	
	public void generateAndSendOTP(String email) {
		Customer customer = customerRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("User not found"));
		
		String otp = generateOTP();
		customer.setOtpCode(otp);
		customer.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
		customerRepository.save(customer);
		
		logger.info("OTP for {} is: {}", email,otp);
		
		emailservice.sendOTPEmail(email, otp);
	}
	
	public boolean verifyOTP(String email, String otp) {
		Customer customer = customerRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("User not found"));
		if (customer.getOtpCode()== null || !customer.getOtpCode().equals(otp)) {
			throw new RuntimeException("Invalid OTP");
		}
		
		if (customer.getOtpExpiry().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Otp expired");
		}
		
		customer.setOtpCode(null);
		customer.setOtpExpiry(null);
		customer.setVerified(true);
		customerRepository.save(customer);
		return true;
	}
	private String generateOTP() {
		return String.format("%06d", new Random().nextInt(999999));
	}

}

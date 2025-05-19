package com.laundry.BE_Laundry.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.laundry.BE_Laundry.Entity.Customer;
import com.laundry.BE_Laundry.Repository.CustomerRepository;
import com.laundry.BE_Laundry.Utill.GenerateOTP;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OTPService {
	private final CustomerRepository customerRepository;
	private final EmailService emailService;
	
	public void generate (String email) {
		Customer c = loadActiveUser(email);
		String otp = GenerateOTP.generateOTP();
		
		c.setOtpCode(otp);
		c.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
		customerRepository.save(c);
		emailService.sendOTPEmail(email, otp);
	}
	
	public void verify (String email, String code) {
		Customer c = loadActiveUser(email);
		
		if (!code.equals(c.getOtpCode()))
			throw new RuntimeException("Otp Salah");
		if (c.getOtpExpiry().isBefore(LocalDateTime.now()))
			throw new RuntimeException("Otp Kadaluarsa");
		
		c.setVerified(true);
		c.setOtpCode(null);
		c.setOtpExpiry(null);
		customerRepository.save(c);
	}
	
	public void resend(String email) {
		generate(email);
	}
	
	private Customer loadActiveUser(String email) {
		Customer c = customerRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("User Not Found"));
		if (c.isVerified())
			throw new RuntimeException("User already verified");
		return c;
	}
	

}

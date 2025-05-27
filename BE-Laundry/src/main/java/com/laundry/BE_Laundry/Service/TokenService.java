package com.laundry.BE_Laundry.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.laundry.BE_Laundry.Entity.Customer;
import com.laundry.BE_Laundry.Repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
	private final CustomerRepository customerRepository;
	private final EmailService emailService;
	
	public void generate (String email) {
		Customer c = customerRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException ("User not found"));
		if(c.isVerified())
			throw new RuntimeException("User already verified");
		
		String token = UUID.randomUUID().toString();
		c.setVerificationToken(token);
		c.setTokenExpiry(LocalDateTime.now().plusHours(24));
		customerRepository.save(c);
		
		emailService.sendVerificationLink(email, token);
		
	}
	
	public void verify (String email, String token) {
		Customer c = customerRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException ("User Not Found"));
		
		if (!token.equals(c.getVerificationToken()))
			throw new RuntimeException("Token salah");
		if(c.getTokenExpiry().isBefore(LocalDateTime.now()))
			throw new RuntimeException("Token kadaluarsa");
		
		c.setVerified(true);
		c.setVerificationToken(null);
		c.setTokenExpiry(null);
		customerRepository.save(c);
	}
	
	public void resend(String email) {
		generate(email);
	}

}

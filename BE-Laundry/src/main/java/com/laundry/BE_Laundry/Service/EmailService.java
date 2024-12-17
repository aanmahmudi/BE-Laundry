package com.laundry.BE_Laundry.Service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	public void sendVerificationEmail(String to, String verificationToken) {
		System.out.println("Email verification link (simulated): http://localhost:5053/api/customers/verify?token=" + verificationToken);
	}

}

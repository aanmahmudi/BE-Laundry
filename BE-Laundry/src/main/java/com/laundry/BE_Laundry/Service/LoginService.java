package com.laundry.BE_Laundry.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.laundry.BE_Laundry.DTO.CustomerLoginDTO;
import com.laundry.BE_Laundry.Entity.Customer;
import com.laundry.BE_Laundry.Repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService {
	
	private final CustomerRepository customerRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	public String loginWithAPI(CustomerLoginDTO loginDTO) {
		Customer customer = customerRepository.findByEmail(loginDTO.getEmail())
				.orElseThrow(()-> new RuntimeException("Customer not found"));
		
		if (passwordEncoder.matches(loginDTO.getPassword(), customer.getPassword())) {
			return "dummy-jwt-token";
		}
		return null;
		
	}
	
	public boolean loginWithForm(String username, String password) {
		Customer customer = customerRepository.findByUsername(username)
				.orElseThrow(()-> new RuntimeException("Customer not found"));
		return passwordEncoder.matches(password, customer.getPassword());
		
	}

}

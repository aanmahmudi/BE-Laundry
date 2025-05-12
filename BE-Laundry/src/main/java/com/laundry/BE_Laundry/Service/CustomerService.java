package com.laundry.BE_Laundry.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.laundry.BE_Laundry.DTO.CustomerLoginDTO;
import com.laundry.BE_Laundry.DTO.OTPVerificationDTO;
import com.laundry.BE_Laundry.DTO.RegisterRequestDTO;
import com.laundry.BE_Laundry.DTO.UpdatePasswordRequestDTO;
import com.laundry.BE_Laundry.DTO.VerifyTokenDTO;
import com.laundry.BE_Laundry.Entity.Customer;
import com.laundry.BE_Laundry.Repository.CustomerRepository;
import com.laundry.BE_Laundry.Utill.GenerateOTP;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	public void registerCustomer(RegisterRequestDTO registerDTO) {
		if (customerRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Email already exist");
		}

		// Map dari DTO ke entity
		Customer customer = mapToCustomer(registerDTO);
		
		//Generated OTP
		String otp = GenerateOTP.generateOTP();
		customer.setOtpCode(otp);
		customer.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
		
		//Generated Token
		String token = UUID.randomUUID().toString();
		customer.setVerificationToken(token);
		customer.setTokenExpiry(LocalDateTime.now().plusHours(24));
		
		// Simpan ke database
		customerRepository.save(customer);
		
		//Send Otp & Token
		emailService.sendOTPEmail(customer.getEmail(), otp);
		emailService.sendVerificationLink(customer.getEmail(), token);

	}

	private Customer mapToCustomer(RegisterRequestDTO registerDTO) {
		Customer customer = new Customer();
		customer.setCreatedAt(registerDTO.getCreatedAt());
		customer.setUsername(registerDTO.getUsername());
		customer.setEmail(registerDTO.getEmail());
		customer.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		customer.setAddress(registerDTO.getAddress());
		customer.setPlaceOfBirth(registerDTO.getPlaceOfBirth());
		customer.setDateOfBirth(registerDTO.getDateOfBirth());
		customer.setPhoneNumber(registerDTO.getPhoneNumber());
		customer.setPhotoUrl(registerDTO.getPhotoUrl());
		customer.setDocumentUrl(registerDTO.getDocumentUrl());
		customer.setRole(Customer.RoleType.valueOf(registerDTO.getRole().toUpperCase()));
		customer.setVerificationToken(UUID.randomUUID().toString());

		// Set expiry 2menit
//		customer.setTokenExpiry(LocalDateTime.now().plusHours(24));
//		customer.setTokenExpiry(LocalDateTime.now().minusMinutes(2));
//		customer.setVerified(false);

		return customer;

	}

	public boolean login(CustomerLoginDTO customerLoginDTO) {
		Customer customer = customerRepository.findByEmail(customerLoginDTO.getEmail())
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		System.out.println("Verifying passowrd for user: " + customer.getEmail());

		if (!passwordEncoder.matches(customerLoginDTO.getPassword(), customer.getPassword())) {
			System.out.println("Password mismatch for user: " + customer.getEmail());
			throw new RuntimeException("Invalid email or password");
		}

		if (!customer.isVerified()) {
			System.out.println("Account is not verified for user: " + customer.getEmail());
			throw new RuntimeException("Account not verified. Please Verify using OTP");
		}

		return true;
	}

	public void updatePassword(UpdatePasswordRequestDTO updatePasswordDTO) {
		Customer customer = customerRepository.findByEmail(updatePasswordDTO.getEmail())
				.orElseThrow(() -> new RuntimeException("User Not Found"));

		if (!passwordEncoder.matches(updatePasswordDTO.getOldPassword(), customer.getPassword())) {
			throw new RuntimeException("Old password is incorect");
		}

		customer.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
		customerRepository.save(customer);
	}
	
	public void generateAndSendVerificationToken(String email) {
		Customer customer = customerRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException ("User Not Found"));
		
		if (customer.isVerified()) {
			throw new RuntimeException("User already verified");
		}
	
	}

	public void verifyToken(VerifyTokenDTO verifyTokenDTO) {
		Customer customer = customerRepository.findByEmail(verifyTokenDTO.getEmail())
				.orElseThrow(() -> new RuntimeException("User Not Foundd"));

		if (!customer.getVerificationToken().equals(verifyTokenDTO.getToken())) {
			throw new RuntimeException("Invalid Token");
		}

		if (customer.getTokenExpiry().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Token has expired");
		}

		customer.setVerified(true);
		customer.setVerificationToken(null);
		customer.setTokenExpiry(null);
		
		customerRepository.save(customer);
	}
	public void resendVerificationToken(String email) {
        generateAndSendVerificationToken(email);
    }

	public void generateAndSendOTP(OTPVerificationDTO otpVerify) {
		    
		Customer customer = customerRepository.findByEmail(otpVerify.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		logger.info("OTP for {} is: {}", otpVerify);
	}
	
	public boolean verifyOTP(OTPVerificationDTO otpVerify) {
		Customer customer = customerRepository.findByEmail(otpVerify.getEmail())
				.orElseThrow(()-> new RuntimeException("User Not Found"));
		if (customer.getOtpCode()== null || !customer.getOtpCode().equals(otpVerify.getOtp())){
			throw new RuntimeException("Invalid OTP code");
		}

		if (customer.getOtpExpiry().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("OTP code expired");
		}

		customer.setVerified(true);
		customer.setOtpCode(null);
		customer.setOtpExpiry(null);

		customerRepository.save(customer);
		return true;
	}
	
	public void resendOtp(OTPVerificationDTO otpVerify) {
		generateAndSendOTP(otpVerify);
	}

	public void logout(String email) {
		Customer customer = customerRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User Not Foundd"));

		System.out.println("logged out: " + customer.getEmail());
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));

	}

	public Customer updateCustomer(Long id, Customer updatedCustomer) {
		Customer existingCustomer = customerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		existingCustomer.setUsername(updatedCustomer.getUsername());
		existingCustomer.setAddress(updatedCustomer.getAddress());
		existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
		existingCustomer.setPlaceOfBirth(updatedCustomer.getPlaceOfBirth());
		existingCustomer.setDateOfBirth(updatedCustomer.getDateOfBirth());
		existingCustomer.setDocumentUrl(updatedCustomer.getDocumentUrl());
		existingCustomer.setPhotoUrl(updatedCustomer.getPhotoUrl());
		return customerRepository.save(existingCustomer);

	}

	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}

}

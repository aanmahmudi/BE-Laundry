package com.laundry.BE_Laundry.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.laundry.BE_Laundry.DTO.CustomerLoginDTO;
import com.laundry.BE_Laundry.DTO.RegisterRequestDTO;
import com.laundry.BE_Laundry.DTO.UpdatePasswordRequestDTO;
import com.laundry.BE_Laundry.DTO.VerifyTokenDTO;
import com.laundry.BE_Laundry.Entity.Customer;
import com.laundry.BE_Laundry.Repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;

	public void registerCustomer(RegisterRequestDTO registerDTO) {
		if (customerRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Email already exist");
		}

		// Map dari DTO ke entity
		Customer customer = mapToCustomer(registerDTO);

		// Simpan ke database
		customerRepository.save(customer);
	}

	private Customer mapToCustomer(RegisterRequestDTO registerDTO) {
		Customer customer = new Customer();
		customer.setUsername(registerDTO.getName());
		customer.setEmail(registerDTO.getEmail());
		customer.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		customer.setAddress(registerDTO.getAddress());
		customer.setPhoneNumber(registerDTO.getPhoneNumber());
		customer.setRole(Customer.RoleType.valueOf(registerDTO.getRole().toUpperCase()));
		customer.setVerificationToken(UUID.randomUUID().toString());

		// Set expiry 2menit
		// customer.setTokenExpiry(LocalDateTime.now().plusHours(24));
		customer.setTokenExpiry(LocalDateTime.now().minusMinutes(2));
		customer.setVerified(false);

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
			throw new RuntimeException("Account not verified");
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

	public void verifyCustomer(VerifyTokenDTO verifyTokenDTO) {
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
		return customerRepository.save(existingCustomer);

	}

	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}

}

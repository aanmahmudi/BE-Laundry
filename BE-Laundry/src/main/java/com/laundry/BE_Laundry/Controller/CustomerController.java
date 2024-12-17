package com.laundry.BE_Laundry.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.laundry.BE_Laundry.DTO.CustomerLoginDTO;
import com.laundry.BE_Laundry.DTO.RegisterRequestDTO;
import com.laundry.BE_Laundry.DTO.UpdatePasswordRequestDTO;
import com.laundry.BE_Laundry.DTO.VerifyTokenDTO;
import com.laundry.BE_Laundry.Entity.Customer;
import com.laundry.BE_Laundry.Service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@RequestBody @Valid RegisterRequestDTO registerDTO) {
		try {
			customerService.registerCustomer(registerDTO);
			return ResponseEntity.ok("Registration successfull. Verify your account using the token");
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody CustomerLoginDTO customerLoginDTO) {
		customerService.login(customerLoginDTO);
		return ResponseEntity.ok("Login Successfuly");
		
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestParam String email) {
		customerService.logout(email);
		return ResponseEntity.ok("Logout successfuly");
	}

	@PutMapping("/update-password")
	public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequestDTO updatePasswordDTO) {
		customerService.updatePassword(updatePasswordDTO);
		return ResponseEntity.ok("Update password successfuly");

	}
	
	@PostMapping("/verify")
	public ResponseEntity<String> verifyCustomer(@RequestBody @Valid VerifyTokenDTO verifyTokenDTO){
		customerService.verifyCustomer(verifyTokenDTO);
		return ResponseEntity.ok("Account verified successfuly");
		
	}

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return ResponseEntity.ok(customerService.getAllCustomers());

	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		return ResponseEntity.ok(customerService.getCustomerById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
		return ResponseEntity.ok(customerService.updateCustomer(id, customer));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		return ResponseEntity.noContent().build();
	}

}

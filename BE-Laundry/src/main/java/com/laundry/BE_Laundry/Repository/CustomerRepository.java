package com.laundry.BE_Laundry.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laundry.BE_Laundry.Entity.Customer;

public interface CustomerRepository extends JpaRepository <Customer, Long>{
	
	Optional<Customer> findById(Long id);
	Optional<Customer> findByEmail(String email);
	Optional<Customer> findByVerificationToken(String token);
	Optional<Customer> findByVerificationOtp(String otp);
	Optional<Customer> findByUsername(String username);
	Optional<Customer> findByPlaceOfBirth(String placeOfBirth);
}

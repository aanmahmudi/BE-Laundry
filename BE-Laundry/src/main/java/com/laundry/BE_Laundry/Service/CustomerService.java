package com.laundry.BE_Laundry.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.laundry.BE_Laundry.Entity.Customer;
import com.laundry.BE_Laundry.Repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;

	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id).orElseThrow(()-> new RuntimeException("Customer not found"));
		
	}

	public Customer updateCustomer(Long id, Customer updatedCustomer) {
		Customer existingCustomer = customerRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Customer not found"));
		existingCustomer.setName(updatedCustomer.getName());
		existingCustomer.setAddress(updatedCustomer.getAddress());
		existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
		return customerRepository.save(existingCustomer);

	}

	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}

}

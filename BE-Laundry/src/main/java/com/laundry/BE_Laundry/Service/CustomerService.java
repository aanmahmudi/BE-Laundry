package com.laundry.BE_Laundry.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laundry.BE_Laundry.Entity.Customer;
import com.laundry.BE_Laundry.Repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public List<Customer> getAllCustomer(){
		return customerRepository.findAll();		
	}
	
	public Customer updateCustomer(Long id, Customer customerDetails){
		Customer customer = customerRepository.findById(id).orElseThrow();
		customer.setName(customerDetails.getName());
		customer.setAddress(customerDetails.getAddress());
		customer.setPhoneNumber(customerDetails.getPhoneNumber());
		return customerRepository.save(customer);
		
	}
	
	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}

}

package com.laundry.BE_Laundry.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.laundry.BE_Laundry.DTO.TransactionRequestDTO;
import com.laundry.BE_Laundry.DTO.TransactionResponseDTO;
import com.laundry.BE_Laundry.Entity.Customer;
import com.laundry.BE_Laundry.Entity.Product;
import com.laundry.BE_Laundry.Entity.Transaction;
import com.laundry.BE_Laundry.Repository.CustomerRepository;
import com.laundry.BE_Laundry.Repository.ProductRepository;
import com.laundry.BE_Laundry.Repository.TransactionRepository;

@Service
public class TransactionService {
	
	private final TransactionRepository transactionRepository;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	
	public TransactionService(TransactionRepository transactionRepository,
								CustomerRepository customerRepository,
								ProductRepository productRepository) {
		this.transactionRepository = transactionRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
	}
	
	public TransactionResponseDTO createTransaction(TransactionRequestDTO requestDTO) {
		Customer customer = customerRepository.findById(requestDTO.getCustomerId())
				 .orElseThrow(()-> new RuntimeException("Customer Not Found"));
		
		Product product = productRepository.findById(requestDTO.getCustomerId())
				 .orElseThrow(()-> new RuntimeException("Product Not Found"));
		
		Transaction transaction = new Transaction();
		transaction.setCustomer(customer);
		transaction.setProduct(product);
		transaction.setQuantity(requestDTO.getQuantity());
		transaction.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(requestDTO.getQuantity())));
		
		Transaction saveTransaction = transactionRepository.save(transaction);
		return mapToResponseDTO(saveTransaction);	
	}
	
	public List<TransactionResponseDTO> getAllTransactions(){
		return transactionRepository.findAll().stream()
				.map(this::mapToResponseDTO)
				.collect(Collectors.toList());
	}
	
	public TransactionResponseDTO getTransactionById(Long id) {
		Transaction transaction = transactionRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Transaction Not Found"));
		return mapToResponseDTO(transaction);
		
	}
	
	private TransactionResponseDTO mapToResponseDTO(Transaction transaction) {
		// TODO Auto-generated method stub
		TransactionResponseDTO responseDTO = new TransactionResponseDTO();
		responseDTO.setId(transaction.getId());
		responseDTO.setCustomerName(transaction.getCustomer().getName());
		responseDTO.setProductName(transaction.getProduct().getName());
		responseDTO.setQuantity(transaction.getQuantity());
		responseDTO.setTotalPrice(transaction.getTotalPrice());
		responseDTO.setTransactionDate(transaction.getTransactionDate());
		return responseDTO;
	}
	

}

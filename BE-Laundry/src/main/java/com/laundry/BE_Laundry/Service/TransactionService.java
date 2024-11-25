package com.laundry.BE_Laundry.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laundry.BE_Laundry.DTO.PaymentRequestDTO;
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
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProductRepository productRepository;

	public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
		Optional<Customer> optionalCustomer = customerRepository.findById(transactionRequestDTO.getCustomerId());
		if (optionalCustomer.isEmpty()) {
			throw new IllegalArgumentException("Customer not found.");
		}
		Customer customer = optionalCustomer.get();

		Optional<Product> optionalProduct = productRepository.findById(transactionRequestDTO.getProductId());
		if (optionalProduct.isEmpty()) {
			throw new IllegalArgumentException("Product not found.");
		}
		Product product = optionalProduct.get();

		BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(transactionRequestDTO.getQuantity()));

		Transaction transaction = new Transaction();
		transaction.setCustomer(customer);
		transaction.setProduct(product);
		transaction.setQuantity(transactionRequestDTO.getQuantity());
		transaction.setTotalPrice(totalPrice);
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setPaymentStatus("Pending");

		transactionRepository.save(transaction);

		TransactionResponseDTO responseDTO = new TransactionResponseDTO();
		responseDTO.setId(transaction.getId());
		responseDTO.setCustomerName(customer.getName());
		responseDTO.setProductName(product.getName());
		responseDTO.setQuantity(transaction.getQuantity());
		responseDTO.setTotalPrice(transaction.getTotalPrice());
		responseDTO.setTransactionDate(transaction.getTransactionDate());
		responseDTO.setPaymentStatus(transaction.getPaymentStatus());
		responseDTO.setPaymentAmount(transaction.getPaymentAmount());
		return responseDTO;

	}

	public String makePayment(PaymentRequestDTO paymentRequestDTO) {
		Optional<Transaction> optionalTransaction = transactionRepository.findById(paymentRequestDTO.getTrasactionId());
		if (optionalTransaction.isEmpty()) {
			return "Transaction not found.";
		}

		Transaction transaction = optionalTransaction.get();

		if ("PAID".equalsIgnoreCase(transaction.getPaymentStatus())) {
			return "Transaction is already paid.";
		}

		if (paymentRequestDTO.getPaymentAmount().compareTo(transaction.getTotalPrice()) < 0) {
			return "Insufficient payment amount.";
		}

		transaction.setPaymentStatus("PAID");
		transaction.setPaymentAmount(paymentRequestDTO.getPaymentAmount());
		transactionRepository.save(transaction);
		return "Payment successful.";

	}

}

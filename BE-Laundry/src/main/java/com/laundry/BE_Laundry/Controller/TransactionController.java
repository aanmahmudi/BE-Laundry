package com.laundry.BE_Laundry.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundry.BE_Laundry.DTO.TransactionRequestDTO;
import com.laundry.BE_Laundry.DTO.TransactionResponseDTO;
import com.laundry.BE_Laundry.Service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
	
	private final TransactionService transactionService;
	
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@PostMapping
	public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO requestDTO) {
		TransactionResponseDTO response = transactionService.createTransaction(requestDTO);
		return ResponseEntity.ok(response);		
	}
	
	@GetMapping
	public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions(){
		List<TransactionResponseDTO> transactions = transactionService.getAllTransactions();
		return ResponseEntity.ok(transactions);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
		TransactionResponseDTO response = transactionService.getTransactionById(id);
		return ResponseEntity.ok(response);
		
	}

}

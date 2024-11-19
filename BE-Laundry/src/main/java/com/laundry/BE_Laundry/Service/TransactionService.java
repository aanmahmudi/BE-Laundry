package com.laundry.BE_Laundry.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laundry.BE_Laundry.Entity.Transaction;
import com.laundry.BE_Laundry.Repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public Transaction createTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);		
	}
	
	public List<Transaction> getAllTransactions(){
		return transactionRepository.findAll();
	}
	
	public Transaction getTransactionById(Long id) {
		return transactionRepository.findById(id).orElseThrow();
	}
	

}

package com.laundry.BE_Laundry.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class TransactionDTO {
	
	private Long id;
	
	private Long customer_id;
	private Long product_id;
	private int quantity;
	private double totalPrice;
	private Date transactionDate;

}

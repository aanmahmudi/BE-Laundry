package com.laundry.BE_Laundry.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionResponseDTO {
	private Long id;
	private String customerName;
	private String productName;
	private int quantity;
	private BigDecimal totalPrice;
	private LocalDateTime transactionDate;

}

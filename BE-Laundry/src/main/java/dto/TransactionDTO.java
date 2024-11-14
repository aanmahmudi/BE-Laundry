package dto;

import java.util.Date;

import lombok.Data;

@Data
public class TransactionDTO {
	private Long id;
	private Long customerId;
	private Long productId;
	private int quantity;
	private double totalPrice;
	private Date transactionDate;

}

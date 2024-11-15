package com.laundry.BE_Laundry.DTO;

import lombok.Data;

@Data
public class ProductDTO {
	
	private Long id;
	
	private String name;
	private double price;
	private String description;

}

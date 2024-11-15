package com.laundry.BE_Laundry.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer {
	
	private Long id;
	
	private String name;
	private String address;
	private String phoneNumber;

}

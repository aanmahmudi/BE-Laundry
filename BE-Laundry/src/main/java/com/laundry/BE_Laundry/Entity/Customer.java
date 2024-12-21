package com.laundry.BE_Laundry.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = true)
	private String address;
	
	@Column(nullable = true)
	private String phoneNumber;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String verificationToken;
	private boolean isVerified = false;
	private LocalDateTime tokenExpiry;
	
	@Enumerated(EnumType.STRING)
	public RoleType role;
	
	public enum RoleType {
		USER,
		ADMIN
	}
	
	public void generateVerificationToken() {
		this.verificationToken = UUID.randomUUID().toString();
		this.tokenExpiry = LocalDateTime.now().plusHours(24);
	}

}

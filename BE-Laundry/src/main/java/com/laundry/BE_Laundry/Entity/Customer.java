package com.laundry.BE_Laundry.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	@Column(name = "created_at", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String placeOfBirth;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(nullable = true)
	private String address;
	
	@Column(nullable = true)
	private String phoneNumber;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(name = "photo_url")
	private String photoUrl;
	
	@Column(name = "document_pdf")
	private String documentUrl;
	
	
	private String verificationToken;
	private boolean isVerified = false;
	private LocalDateTime tokenExpiry;
	
	@Column(name = "otp_code")
	private String otpCode;
	
	@Column(name = "otp_expiry")
	private LocalDateTime otpExpiry;
	
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

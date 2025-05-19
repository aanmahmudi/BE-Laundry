package com.laundry.BE_Laundry.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundry.BE_Laundry.DTO.OTPVerificationDTO;
import com.laundry.BE_Laundry.Service.OTPService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OTPController {
	
	private final OTPService otpService;
	
	private static final Logger logger = LoggerFactory.getLogger(OTPController.class);
	
	@PostMapping("/send")
	public ResponseEntity<String> send(@RequestBody @Valid OTPVerificationDTO otpSend){
		String email = otpSend.getEmail();
		try {
			otpService.generate(email);
			logger.info("OTP sent Successfully to {}", email);
			return ResponseEntity.ok("OTP Sent");
		}catch (IllegalArgumentException ex) {
			logger.warn("Failed to send OTP to {} - {}", email, ex.getMessage());
			return ResponseEntity.badRequest().body("Failed to send OTP: " + ex.getMessage());
		}catch (Exception ex) {
			logger.error("Unexpected error while sending OTP to {}: {}", email, ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured while sending OTP");
		}
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verify(@RequestBody @Valid OTPVerificationDTO otpVerify) {
		try {
			otpService.verify(otpVerify.getEmail(), otpVerify.getOtp());
			logger.info("OTP verified for {}", otpVerify.getEmail());
			return ResponseEntity.ok("Verification via OTP Successful.");
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed:" + ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured:" + ex.getMessage());
		}
	}

}

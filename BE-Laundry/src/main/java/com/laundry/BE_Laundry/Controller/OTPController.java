package com.laundry.BE_Laundry.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundry.BE_Laundry.DTO.OTPRequestDTO;
import com.laundry.BE_Laundry.DTO.OTPVerificationDTO;
import com.laundry.BE_Laundry.Service.OTPService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OTPController {
	
	private final OTPService otpService;
	private static final Logger logger = LoggerFactory.getLogger(OTPController.class);
	
	@PostMapping("/send")
	public ResponseEntity<?> sendOTP(@Validated @RequestBody OTPRequestDTO request){
		otpService.generateAndSendOTP(request.getEmail());
		logger.info("OTP sent to {}", request.getEmail());
		return ResponseEntity.ok("OTP sent Successfully.");
		
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verifyOTP(@Validated @RequestBody OTPVerificationDTO verify){
		otpService.verifyOTP(verify.getEmail(), verify.getOtp());
		logger.info("OTP verified for {}", verify.getEmail());
		return ResponseEntity.ok("OTP verified successfully.");
		
	}

}

package com.laundry.BE_Laundry.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundry.BE_Laundry.DTO.VerifyTokenDTO;
import com.laundry.BE_Laundry.Service.VerifyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/verify")
@RequiredArgsConstructor
public class VerifyController {
	
	private final VerifyService verifyService;
	private static final Logger logger = LoggerFactory.getLogger(VerifyController.class);
	
	@PostMapping("/verify")
	public ResponseEntity<?> verify(@RequestBody VerifyTokenDTO verifyDTO){
		try {
			verifyService.verify(verifyDTO.getEmail(), verifyDTO.getToken());
			logger.info("Token verified for {}", verifyDTO.getEmail());
			return ResponseEntity.ok("Account verified");
		}catch (IllegalArgumentException ex) {
			logger.warn("Token verification failed for {}: {}", verifyDTO.getEmail(), ex.getMessage());
			return ResponseEntity.badRequest().body("Verification failed:" + ex.getMessage());
		}catch (Exception ex) {
			logger.error("Error Verify token for {}: {}", verifyDTO.getEmail(), ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error occured during verification");
		}
		
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> resend(@RequestBody VerifyTokenDTO verifyDTO){
		try {
			verifyService.resend(verifyDTO.getEmail());
			logger.info("Verification token resent to {}", verifyDTO.getEmail());
			return ResponseEntity.ok("Token Resent");
		}catch (IllegalArgumentException ex) {
			logger.warn("Resend failed for {}: {}", verifyDTO.getEmail(), ex.getMessage());
			return ResponseEntity.badRequest().body("Resend failed:" + ex.getMessage());
		}catch (Exception ex) {
			logger.error("Error Resend token for {}: {}", verifyDTO.getEmail(), ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error occured while resending verification");
		}
		
	}

}

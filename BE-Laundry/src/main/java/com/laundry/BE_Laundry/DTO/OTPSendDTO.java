package com.laundry.BE_Laundry.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OTPSendDTO {
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

}

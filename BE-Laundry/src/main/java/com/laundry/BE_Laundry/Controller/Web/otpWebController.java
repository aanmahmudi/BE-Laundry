package com.laundry.BE_Laundry.Controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class otpWebController {
	@GetMapping("/otp")
	public String showOtpPage(@RequestParam("email") String email, Model model) {
		model.addAttribute("email", email);
		return "otp-verification";

	}

}

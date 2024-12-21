package com.laundry.BE_Laundry.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.laundry.BE_Laundry.Service.CustomerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {
	
	private final CustomerService customerService;
	
	@GetMapping("/laundry/login")
	public String showLoginPage() {
		return "Login";
		
	}
	
	@PostMapping("/laundry/login")
	public String login(@RequestParam String username, @RequestParam String password) {
		try {
			if (customerService.loginWithForm(username, password)) {
				return "redirect:/laundry/dashboard";
			}else {
				return "login";
			}
		} catch (RuntimeException e) {
			return "login";
		}		
	}

}

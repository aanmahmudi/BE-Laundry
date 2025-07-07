package com.laundry.BE_Laundry.Controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.laundry.BE_Laundry.DTO.RegisterRequestDTO;
import com.laundry.BE_Laundry.Model.Customer.RoleType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class registerWebController {
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		model.addAttribute("registerDTO", new RegisterRequestDTO());
		model.addAttribute("roles", RoleType.values());
		// Misal roles kamu bisa "CUSTOMER", "ADMIN", dll
//		List<String> roles = List.of("CUSTOMER", "ADMIN", "PM");
//		model.addAttribute("roles", roles);

		return "application"; // ini render application.html
	}

}

package com.laundry.BE_Laundry.Controller.Web;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.laundry.BE_Laundry.DTO.RegisterRequestDTO;
import com.laundry.BE_Laundry.Model.Customer;
import com.laundry.BE_Laundry.Model.Customer.RoleType;
import com.laundry.BE_Laundry.Service.CustomerService;
import com.laundry.BE_Laundry.Service.OTPService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class registerWebController {
	
	@GetMapping("/register-web")
	public String showRegisterPage(Model model) {
		model.addAttribute("registerDTO", new RegisterRequestDTO());
		model.addAttribute("roles", RoleType.values());
		// Misal roles kamu bisa "CUSTOMER", "ADMIN", dll
//		List<String> roles = List.of("CUSTOMER", "ADMIN", "PM");
//		model.addAttribute("roles", roles);

		return "application"; // ini render application.html
	}


}

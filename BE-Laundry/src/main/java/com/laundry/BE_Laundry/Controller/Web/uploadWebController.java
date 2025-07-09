package com.laundry.BE_Laundry.Controller.Web;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.laundry.BE_Laundry.Model.Customer;
import com.laundry.BE_Laundry.Service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class uploadWebController {

	@GetMapping("/upload")
	public String showUploadPage(@RequestParam("id") Long id, Model model) {
		log.info("Accessing /upload with customerId={}", id);
	    if (id == null || id <= 0) {
	        throw new IllegalArgumentException("Customer ID tidak valid!");
	    }
	    		
	    model.addAttribute("customerId", id);
	    return "upload";
	}

}

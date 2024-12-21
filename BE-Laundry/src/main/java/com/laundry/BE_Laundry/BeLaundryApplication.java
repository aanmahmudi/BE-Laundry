package com.laundry.BE_Laundry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.laundry.BE_Laundry")
public class BeLaundryApplication extends SpringBootServletInitializer{

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BeLaundryApplication.class);
		
	}

	public static void main(String[] args) {
		SpringApplication.run(BeLaundryApplication.class, args);
	}

}

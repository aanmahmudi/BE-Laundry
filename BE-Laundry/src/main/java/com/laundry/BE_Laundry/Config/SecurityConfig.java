package com.laundry.BE_Laundry.Config;

import java.util.List;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.Filter;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf().disable()
			.cors().disable()
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(
						"/api/customers/register",
						"/api/customers/verify", 
						"/api/customers/login",
						"/api/customers/logout",
						"/api/customers/update-password",
						"/api/customers",
						"/api/customers/{id}",
						"/api/products",
						"/api/products/{id}",
						"/api/transactions",
						"/api/transactions/{id}",
						"/api/transactions/payment",
						"/api/transactions/paid",
						"/api/transactions/{id}/payment",
						"/error")
				.permitAll()
						
				.anyRequest().authenticated()
			)
			.addFilterAfter(new CustomLoggingFilter(), UsernamePasswordAuthenticationFilter.class)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				
//				.formLogin()
//					.loginPage("/api/customers/login")
//					.defaultSuccessUrl("/api/customers/login/dashboard", true)
//					.permitAll()
//			.and()
//				.logout()
////					.logoutUrl("/api/customers/logout")
////				.logoutSuccessUrl("/api/customers/login")
//					.logoutUrl("/api/customers/logout")
//					.logoutSuccessUrl("/api/customers/login?logout")
//					.invalidateHttpSession(true)
//					.clearAuthentication(true)
//					.deleteCookies("JSESSIONID");
		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("*"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("Content-Type", "Authorization"));
		config.addExposedHeader("Authorization");
		
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
}

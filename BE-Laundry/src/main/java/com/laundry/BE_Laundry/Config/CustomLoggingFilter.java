package com.laundry.BE_Laundry.Config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomLoggingFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(CustomLoggingFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {


		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse httpResponse = (HttpServletResponse) response;

		logger.info("Incoming request: Method={} URI={}", req.getMethod(), req.getRequestURI());
//		logger.info("Request Method:{}", httpRequest.getMethod());
		
		chain.doFilter(request, response);
		
	}

}

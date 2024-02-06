package com.RESTService.Exceptions;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {


	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

	     response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	     response.setContentType("application/json");
	     response.addHeader("access_denied_reason", "Not_Found");
	     System.out.println("requsted URI :" + request.getRequestURI());
	     System.out.println("Status : "+request.getRemoteUser().toString()+" NOT-AUTHORIZED");
	}

}

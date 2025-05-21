package com.jdc.online.balances.security;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String message = switch(exception) {
		case UsernameNotFoundException e -> e.getMessage();
		case BadCredentialsException e -> "Please check password.";
		default -> "Authentication Error";
		};
		
		response.sendRedirect("/signin?error=%s".formatted(message));
	}

}

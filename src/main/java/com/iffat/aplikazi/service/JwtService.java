package com.iffat.aplikazi.service;

import com.iffat.aplikazi.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	String generateToken(String username);
	Boolean isTokenExpired(String token);
	Boolean validateToken(String token, UserDetails userDetails);
	User getUserFromToken(String token);
	String extractToken(HttpServletRequest request);
}

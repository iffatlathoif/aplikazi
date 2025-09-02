package com.iffat.aplikazi.controller;

import com.iffat.aplikazi.dto.LoginRequest;
import com.iffat.aplikazi.dto.LoginResponse;
import com.iffat.aplikazi.dto.RegisterMemberRequest;
import com.iffat.aplikazi.dto.UserResponse;
import com.iffat.aplikazi.model.User;
import com.iffat.aplikazi.model.UserPrincipal;
import com.iffat.aplikazi.service.AuthService;
import com.iffat.aplikazi.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;
	private final JwtService jwtService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		LoginResponse loginResponse = authService.login(request);
		return ResponseEntity.status(HttpStatus.OK)
				.body(loginResponse);
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterMemberRequest request) {
		authService.register(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body("User registered successfully.");
	}

	@GetMapping("/check-status")
	public ResponseEntity<?> checkStatus(Authentication authentication, HttpServletRequest request) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		User user = ((UserPrincipal) authentication.getPrincipal()).getUser();
		String authHeader = request.getHeader("Authorization");
		String token = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		}
		LoginResponse response = LoginResponse.builder()
				.user(UserResponse.builder()
						.id(user.getId())
						.username(user.getUsername())
						.email(user.getEmail())
						.enabled(user.isEnabled())
						.lastLogin(user.getLastLogin())
						.roles(authentication.getAuthorities().stream()
								.map(GrantedAuthority::getAuthority) // ambil "ROLE_ADMIN"
								.toList())
						.build())
				.token(token)
				.build();

		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}
}

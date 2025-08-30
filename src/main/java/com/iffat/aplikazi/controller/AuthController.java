package com.iffat.aplikazi.controller;

import com.iffat.aplikazi.dto.LoginRequest;
import com.iffat.aplikazi.dto.LoginResponse;
import com.iffat.aplikazi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		LoginResponse loginResponse = authService.login(request);
		return ResponseEntity.status(HttpStatus.OK)
				.body(loginResponse);
	}
}

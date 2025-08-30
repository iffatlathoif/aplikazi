package com.iffat.aplikazi.service.impl;

import com.iffat.aplikazi.dto.LoginRequest;
import com.iffat.aplikazi.dto.LoginResponse;
import com.iffat.aplikazi.repository.UserRepository;
import com.iffat.aplikazi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;

	@Override
	public LoginResponse login(LoginRequest request) {
		// TODO :
		return LoginResponse.builder().build();
	}
}

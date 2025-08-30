package com.iffat.aplikazi.service.impl;

import com.iffat.aplikazi.dto.LoginRequest;
import com.iffat.aplikazi.dto.LoginResponse;
import com.iffat.aplikazi.model.Role;
import com.iffat.aplikazi.model.User;
import com.iffat.aplikazi.model.UserPrincipal;
import com.iffat.aplikazi.service.AuthService;
import com.iffat.aplikazi.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	@Override
	public LoginResponse login(LoginRequest request) {
		// TODO :
		Authentication authentication = authenticationManager.authenticate(new
				UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(),
				request.getPassword()));
		User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

		String token = jwtService.generateToken(user.getUsername());
		return LoginResponse.builder()
				.role(authentication.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority) // ambil "ROLE_ADMIN"
						.collect(Collectors.joining(",")))
				.username(user.getUsername())
				.token(token)
				.build();
	}
}

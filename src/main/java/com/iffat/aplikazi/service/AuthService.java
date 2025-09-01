package com.iffat.aplikazi.service;

import com.iffat.aplikazi.dto.LoginRequest;
import com.iffat.aplikazi.dto.LoginResponse;
import com.iffat.aplikazi.dto.RegisterMemberRequest;

public interface AuthService {
	LoginResponse login(LoginRequest request);
	void register(RegisterMemberRequest request);
}

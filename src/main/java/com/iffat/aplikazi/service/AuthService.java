package com.iffat.aplikazi.service;

import com.iffat.aplikazi.dto.LoginRequest;
import com.iffat.aplikazi.dto.LoginResponse;

public interface AuthService {
	LoginResponse login(LoginRequest request);
}

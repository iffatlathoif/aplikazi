package com.iffat.aplikazi.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
	private UserResponse user;
	private String token;
}

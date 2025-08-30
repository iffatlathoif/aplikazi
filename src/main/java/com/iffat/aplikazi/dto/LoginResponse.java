package com.iffat.aplikazi.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
	private String username;
	private String role;
	private String token;
}

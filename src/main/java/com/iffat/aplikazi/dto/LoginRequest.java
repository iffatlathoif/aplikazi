package com.iffat.aplikazi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
	private String usernameOrEmail;
	private String password;
}

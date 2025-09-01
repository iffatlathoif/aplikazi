package com.iffat.aplikazi.dto;

import com.iffat.aplikazi.enumeration.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {
	private String username;
	private String email;
	private String password;
	private String confirmPassword;
	private List<Role> roles;
}

package com.iffat.aplikazi.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
	private String id;
	private String username;
	private String email;
	private LocalDateTime lastLogin;
	private boolean enabled;
	private List<String> roles;
}

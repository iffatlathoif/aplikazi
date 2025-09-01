package com.iffat.aplikazi.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterMemberRequest {
	private RegisterUserRequest user;
	private String firstName;
	private String lastName;
	private String address;
}

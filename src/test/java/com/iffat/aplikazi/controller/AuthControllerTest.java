package com.iffat.aplikazi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iffat.aplikazi.config.TestConfig;
import com.iffat.aplikazi.dto.LoginRequest;
import com.iffat.aplikazi.dto.LoginResponse;
import com.iffat.aplikazi.dto.RegisterMemberRequest;
import com.iffat.aplikazi.dto.RegisterUserRequest;
import com.iffat.aplikazi.enumeration.Role;
import com.iffat.aplikazi.service.AuthService;
import com.iffat.aplikazi.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(TestConfig.class)
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockitoBean
	private AuthService authService;
	@MockitoBean
	private JwtService jwtService;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN", "STAFF"})
	void givenRegisterStaffRequest_whenStaffRegisterAsAdminOrStaff_thenReturnSuccess() throws Exception {
		RegisterMemberRequest request = RegisterMemberRequest.builder()
				.firstName("John")
				.lastName("Doe")
				.address("Bandung")
				.user(RegisterUserRequest.builder()
						.username("johnDoe")
						.email("johndoe@mail.com")
						.password("password123")
						.roles(List.of(Role.MEMBER))
						.build())
				.build();
		doNothing().when(authService).register(any(RegisterMemberRequest.class));

		// when
		ResultActions response = mockMvc.perform(post("/api/v1/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)));

		// then
		response.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().string("User registered successfully."));

		verify(authService, times(1)).register(any(RegisterMemberRequest.class));
	}

	@Test
	@WithMockUser(username = "admin", roles = {"MEMBER"})
	void givenRegisterStaffRequest_whenStaffRegisterAsMember_thenReturnFailed() throws Exception {
		RegisterMemberRequest request = RegisterMemberRequest.builder()
				.firstName("John")
				.lastName("Doe")
				.address("Bandung")
				.user(RegisterUserRequest.builder()
						.username("johnDoe")
						.email("johndoe@mail.com")
						.password("password123")
						.roles(List.of(Role.MEMBER))
						.build())
				.build();
		doNothing().when(authService).register(any(RegisterMemberRequest.class));

		// when
		ResultActions response = mockMvc.perform(post("/api/v1/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)));

		// then
		response.andDo(print())
				.andExpect(status().isForbidden());

		verify(authService, times(0)).register(any(RegisterMemberRequest.class));
	}

	@Test
	void givenLoginRequest_whenAdminLogin_thenSuccess() throws Exception {
		LoginRequest request = LoginRequest.builder()
				.usernameOrEmail("admin")
				.password("password123")
				.build();
		LoginResponse mockLoginResponse = LoginResponse.builder()
				.username("admin")
				.token("mock-token")
				.role("ROLE_ADMIN")
				.build();

		// mock service
		when(authService.login(any(LoginRequest.class))).thenReturn(mockLoginResponse);

		// when
		ResultActions response = mockMvc.perform(post("/api/v1/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)));

		// then
		response.andDo(print())
				.andExpect(status().isOk());

		verify(authService, times(1)).login(any(LoginRequest.class));
	}
}
package com.iffat.aplikazi.config;

import com.iffat.aplikazi.filter.JwtTokenValidationFilter;
import com.iffat.aplikazi.handler.CustomAccessDeniedHandler;
import com.iffat.aplikazi.handler.CustomBasicAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.iffat.aplikazi.constants.Constants.ADMIN_STAFF_PERMISSION;
import static com.iffat.aplikazi.constants.Constants.PUBLIC_URL;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsService userDetailsService;
	private final JwtTokenValidationFilter jwtTokenValidationFilter;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(request -> request
						.requestMatchers(PUBLIC_URL).permitAll()
						.requestMatchers(ADMIN_STAFF_PERMISSION).hasAnyRole("ADMIN","STAFF")
						.anyRequest().authenticated())
				.exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler)
						.authenticationEntryPoint(customBasicAuthenticationEntryPoint))
				.addFilterBefore(jwtTokenValidationFilter, BasicAuthenticationFilter.class);
		return httpSecurity.build();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

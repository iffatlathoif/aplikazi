package com.iffat.aplikazi.service.impl;

import com.iffat.aplikazi.dto.LoginRequest;
import com.iffat.aplikazi.dto.LoginResponse;
import com.iffat.aplikazi.dto.RegisterMemberRequest;
import com.iffat.aplikazi.dto.RegisterUserRequest;
import com.iffat.aplikazi.enumeration.StatusMember;
import com.iffat.aplikazi.exception.ResourceNotFoundException;
import com.iffat.aplikazi.model.Member;
import com.iffat.aplikazi.model.Role;
import com.iffat.aplikazi.model.User;
import com.iffat.aplikazi.model.UserPrincipal;
import com.iffat.aplikazi.repository.MemberRepository;
import com.iffat.aplikazi.repository.RoleRepository;
import com.iffat.aplikazi.repository.UserRepository;
import com.iffat.aplikazi.service.AuthService;
import com.iffat.aplikazi.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public LoginResponse login(LoginRequest request) {
		// TODO :
		Authentication authentication = authenticationManager.authenticate(new
				UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(),
				request.getPassword()));
		User user = ((UserPrincipal) authentication.getPrincipal()).getUser();

		String token = jwtService.generateToken(user.getUsername());
		return LoginResponse.builder()
				.role(authentication.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority) // ambil "ROLE_ADMIN"
						.collect(Collectors.joining(",")))
				.username(user.getUsername())
				.token(token)
				.build();
	}

	@Transactional
	@Override
	public void register(RegisterMemberRequest request) {
		RegisterUserRequest userRequest = request.getUser();
		List<String> roles = userRequest.getRoles().stream()
				.map(Enum::name)
				.filter(name -> !name.equals("ADMIN"))
				.collect(Collectors.toList());

		Set<Role> roleDB = roleRepository.findAllByName(roles);

		if (roleDB.isEmpty()) {
			throw new ResourceNotFoundException("Role not found");
		}

		User user = User.builder()
				.username(userRequest.getUsername())
				.email(userRequest.getEmail())
				.enabled(false)
				.password(passwordEncoder.encode(userRequest.getPassword()))
				.accountNonLocked(true)
				.accountNonExpired(true)
				.build();
		user.setRoles(roleDB);
		user.setCreatedAt(LocalDateTime.now());
		user.setCreatedBy("DBA");

		if (!roles.contains("MEMBER")) {
			userRepository.save(user);
		} else {
			Member member = Member.builder()
					.firstName(request.getFirstName())
					.lastName(request.getLastName())
					.expiredAt(LocalDateTime.now().plusYears(1))
					.address(request.getAddress())
					.joinAt(LocalDateTime.now())
					.user(user)
					.status(StatusMember.INACTIVE)
					.build();
			member.setCreatedAt(LocalDateTime.now());
			member.setCreatedBy("DBA");
			memberRepository.save(member);
		}
	}
}

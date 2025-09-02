package com.iffat.aplikazi.filter;

import com.iffat.aplikazi.model.Role;
import com.iffat.aplikazi.model.User;
import com.iffat.aplikazi.model.UserPrincipal;
import com.iffat.aplikazi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.iffat.aplikazi.constants.Constants.PUBLIC_URL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtTokenValidationFilter extends OncePerRequestFilter {
	private final AntPathMatcher pathMatcher = new AntPathMatcher();
	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader(AUTHORIZATION);
		if (authHeader != null) {
			User user = jwtService.getUserFromToken(authHeader);
			Set<GrantedAuthority> authorities = user.getRoles().stream().map(Role::getName)
					.map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
					.collect(Collectors.toSet());
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(new UserPrincipal(user),
					null, authorities);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			SecurityContextHolder.clearContext();
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		return Arrays.asList(PUBLIC_URL).stream().anyMatch(publicPath ->
				pathMatcher.match(publicPath, path));
	}
}

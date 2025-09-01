package com.iffat.aplikazi.service.impl;

import com.iffat.aplikazi.model.User;
import com.iffat.aplikazi.exception.ApiException;
import com.iffat.aplikazi.exception.ResourceNotFoundException;
import com.iffat.aplikazi.repository.UserRepository;
import com.iffat.aplikazi.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

	private final UserRepository userRepository;

	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;

	@Override
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return buildToken(claims, username);
	}

	private String buildToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	@Override
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	@Override
	public User getUserFromToken(String token) {
		String jwtToken = token.substring("Bearer ".length());
		String username = extractUsername(jwtToken);
		return userRepository.findByUsername(username).orElseThrow(
				() -> new ResourceNotFoundException("User not found with username " + username)
		);
	}

	@Override
	public String extractToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

	private Claims extractAllClaims(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(getSignKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException e) {
			// Token expired
			throw new ApiException("Token expired");
		} catch (MalformedJwtException e) {
			throw new ApiException("Invalid token format");
		} catch (Exception e) {
			throw new ApiException("Token parsing error");
		}
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
}

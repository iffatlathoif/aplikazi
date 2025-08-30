package com.iffat.aplikazi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {
	@Column(unique = true, nullable = false)
	private String username;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(name = "last_login")
	private LocalDateTime lastLogin;
	private boolean enabled;
	@Column(name = "account_non_expired")
	private boolean accountNonExpired;
	@Column(name = "account_non_locked")
	private boolean accountNonLocked;
	@Column(name = "login_attempts")
	private int loginAttempts;
	@ManyToMany
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
}

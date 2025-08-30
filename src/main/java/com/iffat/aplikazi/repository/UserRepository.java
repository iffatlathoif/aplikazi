package com.iffat.aplikazi.repository;

import com.iffat.aplikazi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByUsername(String username);
	@Query("select u from User u join fetch u.roles where u.username = ?1 or u.email = ?2")
	Optional<User> findByUsernameOrEmail(String username, String email);
}

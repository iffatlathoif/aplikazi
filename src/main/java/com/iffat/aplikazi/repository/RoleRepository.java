package com.iffat.aplikazi.repository;

import com.iffat.aplikazi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
	@Query("select r from Role r where r.name in (?1)")
	Set<Role> findAllByName(List<String> names);
}

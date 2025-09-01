package com.iffat.aplikazi.repository;

import com.iffat.aplikazi.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
}

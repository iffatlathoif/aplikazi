package com.iffat.aplikazi.entity;

import com.iffat.aplikazi.enumeration.StatusMember;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "members")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity{
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	private String address;
	@Enumerated(EnumType.STRING)
	private StatusMember status;
	@Column(name = "join_at")
	private LocalDateTime joinAt;
	@Column(name = "expired_at")
	private LocalDateTime expiredAt;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
}

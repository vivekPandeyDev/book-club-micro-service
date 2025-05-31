package org.loop.troop.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString(exclude = { "role" })
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID authorityId;

	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

}
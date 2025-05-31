package org.loop.troop.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.loop.troop.domain.BaseEntity;

import java.util.*;

@Entity
@Table(name = "club_user")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID userId;
	@Column(updatable = false, nullable = false)
	private UUID keycloakId;
	private String username;
	private String email;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	private String profileUrl;

	private String bio;

	private boolean privateAccount = false;

	public boolean checkForPrivateAccount() {
		return privateAccount;
	}

}

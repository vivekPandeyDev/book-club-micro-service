package org.loop.troop.domain.dto;

import lombok.Data;
import org.keycloak.representations.idm.UserRepresentation;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Data
public class UserDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 92414210438655957L;

	private UUID userId;

	private String firstName;

	private String username;

	private String email;

	private List<String> roleAuthorities = new ArrayList<>();

	private String profileUrl;

	private String lastName;

	private boolean privateAccount;

	private LocalDate createdAt;

	private boolean enable;

	private boolean emailVerified;

	private String createdBy;

	private String lastModifiedBy;

	private Long creationTimestamp;

}

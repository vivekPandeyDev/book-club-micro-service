package org.loop.troop.domain.keycloak;

import org.keycloak.representations.idm.UserRepresentation;
import org.loop.troop.domain.dto.UserDto;
import org.loop.troop.domain.user.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

public class UserUtility {

	private UserUtility() {
	}

	public static UserDto toUserDto(UserRepresentation userRep, User user) {
		UserDto userDto = new UserDto();
		userDto.setUserId(UUID.fromString(userRep.getId()));
		userDto.setFirstName(userRep.getFirstName());
		userDto.setLastName(userRep.getLastName());
		userDto.setUsername(userRep.getUsername());
		userDto.setEmail(userRep.getEmail());
		userDto.setEnable(userRep.isEnabled());
		userDto.setEmailVerified(userRep.isEmailVerified());

		if (userRep.getCreatedTimestamp() != null) {
			LocalDate localDate = Instant.ofEpochMilli(userRep.getCreatedTimestamp())
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
			userDto.setCreatedAt(localDate);
		}

		List<String> roleAuthorityList = user.getRoles()
			.stream()
			.flatMap(role -> role.getAuthorities().stream().map(auth -> role.getName() + ":" + auth.getName()))
			.toList();

		userDto.setRoleAuthorities(roleAuthorityList);

		return userDto;
	}

}

package org.loop.troop.domain.keycloak;

import org.loop.troop.domain.dto.RegisterDto;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.loop.troop.domain.dto.UserDto;

public interface KeyCloakService {

	UserDto createUser(RegisterDto registerDto);

	void deleteUser(String id);

	UserRepresentation getKeycloakUser(String username);

	RoleRepresentation getKeycloakDefaultRoles();

	boolean isUserExist(String username);

}

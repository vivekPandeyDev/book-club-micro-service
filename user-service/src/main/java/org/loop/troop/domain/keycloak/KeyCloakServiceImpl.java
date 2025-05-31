package org.loop.troop.domain.keycloak;

import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.client.jaxrs.internal.FinalizedClientResponse;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.loop.troop.exception.ServiceException;
import org.loop.troop.domain.dto.RegisterDto;
import org.loop.troop.domain.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KeyCloakServiceImpl implements KeyCloakService {

	private static final Logger log = LoggerFactory.getLogger(KeyCloakServiceImpl.class);

	private final RealmResource realmResource;

	private final UsersResource usersResource;

	@Value("${auth.keycloak.config.default-role}")
	private String defaultRoleName;

	public UserDto createUser(RegisterDto registerDto) {
		try (var response = usersResource.create(registerDto.toUserRepresentation())) {
			var isUserCreated = Objects.equals(201, response.getStatus());
			if (!isUserCreated) {
				throw new ServiceException("user cannot be created, reason -> : " + response.getStatusInfo());
			}
			else {
				// Get user ID
				var users = usersResource.search(registerDto.getUsername());
				if (users.isEmpty()) {
					throw new ServiceException("user cannot be find by given username: " + registerDto.getUsername());
				}

				return UserUtility.toUserDto(users.get(0), null);
			}

		}
	}

	public void deleteUser(String id) {
		try (var response = usersResource.delete(id)) {
			log.info("user delete status: {}", response.getStatus());
		}
	}

	@Override
	public UserRepresentation getKeycloakUser(String username) {
		var users = usersResource.search(username);
		if (users.isEmpty()) {
			throw new ServiceException("user cannot be find by given username: " + username);
		}
		return users.get(0);
	}

	@Override
	public RoleRepresentation getKeycloakDefaultRoles() {
		return realmResource.roles().get(defaultRoleName).toRepresentation();
	}

	@Override
	public boolean isUserExist(String username) {
		var users = usersResource.search(username);
		return !users.isEmpty();
	}

}

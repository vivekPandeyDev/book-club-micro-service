package org.loop.troop.domain.user;

import org.loop.troop.domain.keycloak.KeyCloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegisterEvent {

	private final KeyCloakService keyCloakService;

	private final UserRepository userRepository;

	@KafkaListener(topics = "oauth-user-register-event", groupId = "oauth-user-group-1")
	public void consumeUserEvent(String username) {
		log.info("user id: {}", username);
		var userRep = keyCloakService.getKeycloakUser(username);
		var role = keyCloakService.getKeycloakDefaultRoles();
		userRepository.save(new User());
	}

}

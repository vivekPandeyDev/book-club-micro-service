package org.loop.troop.web.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyCloakConfiguration {

	@Value("${auth.keycloak.config.realm}")
	private String realm;

	@Value("${auth.keycloak.config.base-url}")
	private String baseUrl;

	@Value("${auth.keycloak.config.admin-client-id}")
	private String adminClientId;

	@Value("${auth.keycloak.config.admin-client-secret}")
	private String adminClientSecret;

	@Bean
	@Qualifier("admin-client")
	Keycloak keycloak() {
		return KeycloakBuilder.builder()
			.realm(realm)
			.serverUrl(baseUrl)
			.clientId(adminClientId)
			.clientSecret(adminClientSecret)
			.grantType(OAuth2Constants.CLIENT_CREDENTIALS)
			.build();
	}

	@Bean
	RealmResource realmResource(@Qualifier("admin-client") Keycloak keycloak) {
		return keycloak.realm(realm);
	}

	@Bean
	UsersResource usersResource(RealmResource realmResource) {
		return realmResource.users();
	}

}

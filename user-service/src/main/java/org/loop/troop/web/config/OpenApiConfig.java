package org.loop.troop.web.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class OpenApiConfig {

	@Value("${jwt.open-id-uri}")
	private String openIdUri;

	@Value("${jwt.open-id-security-name}")
	private String openIdSecurityName;

	@Bean
	public OpenAPI productServiceApi() {
		return new OpenAPI()
			.info(new Info().title("User Service Api")
				.description("Rest api to handle key-cloak user")
				.version("1.0.0")
				.license(new License().name("Loop Troop Org")))
			.components(securityComponent())
			.security(securityRequirements());
	}

	private List<SecurityRequirement> securityRequirements() {
		return Collections.singletonList(new SecurityRequirement().addList(openIdSecurityName));
	}

	private Components securityComponent() {
		return new Components().addSecuritySchemes(openIdSecurityName,
				new SecurityScheme().type(SecurityScheme.Type.OPENIDCONNECT)
					.in(SecurityScheme.In.HEADER)
					.scheme("bearer")
					.openIdConnectUrl(openIdUri));
	}

}
